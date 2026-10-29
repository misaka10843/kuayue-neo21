package com.misaka10843.kuayue_neo21.content.coupling;

import com.misaka10843.kuayue_neo21.content.VehicleDoorInteraction;
import com.misaka10843.kuayue_neo21.content.infrastructure.PlatformDoorBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.end_face.TrainEndfaceBlock;
import com.simibubi.create.AllItems;
import com.simibubi.create.Create;
import com.simibubi.create.api.behaviour.interaction.MovingInteractionBehaviour;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.trains.entity.CarriageContraption;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import com.simibubi.create.content.trains.entity.Train;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

public final class CouplerInteraction extends MovingInteractionBehaviour {
    public enum Hint { CANNOT_DIVIDE, CAN_DIVIDE, CAN_SELECT, UNSAFE, FAILED }

    public static Hint hint(Player player, CarriageContraptionEntity entity, BlockPos pos) {
        if (!(entity.getContraption() instanceof CarriageContraption contraption) || entity.getCarriage() == null) return Hint.CANNOT_DIVIDE;
        var info = contraption.getBlocks().get(pos);
        if (info == null || !isCoupler(info.state())) return Hint.CANNOT_DIVIDE;
        var train = entity.getCarriage().train;
        if (!available(player, train)) return Hint.UNSAFE;
        int boundary = boundary(entity, pos);
        if (boundary < 0 || boundary >= train.carriages.size() - 1) return Hint.CAN_SELECT;
        return AutomaticCoupling.coupler(train.carriages.get(boundary), false) != null
                && AutomaticCoupling.coupler(train.carriages.get(boundary + 1), true) != null ? Hint.CAN_DIVIDE : Hint.FAILED;
    }

    public static int boundary(CarriageContraptionEntity entity, BlockPos pos) {
        var direction = ((CarriageContraption) entity.getContraption()).getAssemblyDirection();
        boolean leading = pos.get(direction.getAxis()) * direction.getAxisDirection().getStep() < 0;
        return leading ? entity.carriageIndex - 1 : entity.carriageIndex;
    }
    private record Selection(UUID train, boolean leading, Vec3 position, long expires) {}
    private static final Map<UUID, Selection> SELECTED = new HashMap<>();
    private final VehicleDoorInteraction doors = new VehicleDoorInteraction();

    public static boolean isCoupler(BlockState state) {
        return state.getBlock() instanceof CouplerPart || state.getBlock() instanceof TrainEndfaceBlock && !(state.getBlock() instanceof PlatformDoorBlock)
                || BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString().equals("kuayue:22_coupler")
                || state.is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK,
                    com.misaka10843.kuayue_neo21.content.PortRegistration.resource("simple_conductor")));
    }

    @Override
    public boolean handlePlayerInteraction(Player player, InteractionHand hand, BlockPos pos, AbstractContraptionEntity entity) {
        if (!AllItems.WRENCH.isIn(player.getItemInHand(hand))) {
            var info = entity.getContraption().getBlocks().get(pos);
            return info != null && VehicleDoorInteraction.canInteract(info.state()) && doors.handlePlayerInteraction(player, hand, pos, entity);
        }
        if (!(entity instanceof CarriageContraptionEntity carriageEntity)
                || !(entity.getContraption() instanceof CarriageContraption contraption)) return false;
        if (player.level().isClientSide) return true;
        Train train = carriageEntity.getCarriage().train;
        var hint = hint(player, carriageEntity, pos);
        if (hint == Hint.CANNOT_DIVIDE || hint == Hint.FAILED) return message(player, "failed");
        if (hint == Hint.UNSAFE) return message(player, "unsafe");
        var direction = contraption.getAssemblyDirection();
        int coordinate = pos.get(direction.getAxis()) * direction.getAxisDirection().getStep();
        boolean leading = coordinate < 0;
        int boundary = boundary(carriageEntity, pos);
        if (boundary >= 0 && boundary < train.carriages.size() - 1) {
            UUID newId = UUID.randomUUID();
            Train separated = TrainTopology.split(Create.RAILWAYS, train, newId, boundary);
            if (separated == null) return message(player, "failed");
            train.navigation.cancelNavigation();
            train.collectInitiallyOccupiedSignalBlocks();
            separated.collectInitiallyOccupiedSignalBlocks();
            Create.RAILWAYS.markTracksDirty();
            PacketDistributor.sendToAllPlayers(new TrainTopologyPayload(train.id, newId, boundary, 0, true));
            SELECTED.remove(player.getUUID());
            CouplingEffects.play((net.minecraft.server.level.ServerLevel) entity.level(), entity.toGlobalVector(Vec3.atCenterOf(pos), 1));
            return message(player, "split");
        }
        Vec3 position = entity.toGlobalVector(Vec3.atCenterOf(pos), 1);
        Selection selected = SELECTED.remove(player.getUUID());
        if (selected == null || selected.expires < player.level().getGameTime() || selected.train.equals(train.id)) {
            SELECTED.put(player.getUUID(), new Selection(train.id, leading, position, player.level().getGameTime() + 600));
            return message(player, "selected");
        }
        Train other = Create.RAILWAYS.trains.get(selected.train);
        if (!available(player, other) || other.graph != train.graph || position.distanceToSqr(selected.position) > 4)
            return message(player, "too_far");
        if (selected.leading == leading) return message(player, "orientation");
        Train front = leading ? other : train;
        Train rear = leading ? train : other;
        Vec3 frontDirection = AutomaticCoupling.forward(front.carriages.getLast());
        Vec3 rearDirection = AutomaticCoupling.forward(rear.carriages.getFirst());
        if (frontDirection == null || rearDirection == null || frontDirection.dot(rearDirection) < 0.95)
            return message(player, "orientation");
        if (rear.runtime.getSchedule() != null) return message(player, "schedule");
        Vec3 frontEnd = front.carriages.getLast().trailingBogey().getAnchorPosition();
        Vec3 rearEnd = rear.carriages.getFirst().leadingBogey().getAnchorPosition();
        if (frontEnd == null || rearEnd == null) return message(player, "failed");
        int spacing = Math.max(1, (int) Math.round(frontEnd.distanceTo(rearEnd)));
        if (!TrainTopology.merge(Create.RAILWAYS, front, rear, spacing)) return message(player, "failed");
        rear.leaveStation();
        front.navigation.cancelNavigation();
        front.collectInitiallyOccupiedSignalBlocks();
        Create.RAILWAYS.markTracksDirty();
        PacketDistributor.sendToAllPlayers(new TrainTopologyPayload(front.id, rear.id, 0, spacing, false));
        CouplingEffects.play((net.minecraft.server.level.ServerLevel) entity.level(), position);
        return message(player, "merged");
    }

    private static boolean available(Player player, Train train) {
        return train != null && !train.derailed && train.graph != null && Math.abs(train.speed) < 0.001
                && (train.owner == null || train.owner.equals(player.getUUID()) || player.hasPermissions(2))
                && (train.runtime.getSchedule() == null || train.runtime.paused)
                && train.carriages.stream().noneMatch(carriage -> carriage.presentInMultipleDimensions());
    }

    private static boolean message(Player player, String key) {
        player.displayClientMessage(Component.translatable("message.kuayue.coupling." + key), true);
        return true;
    }
}

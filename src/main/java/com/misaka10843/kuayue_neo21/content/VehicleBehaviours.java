package com.misaka10843.kuayue_neo21.content;

import com.misaka10843.kuayue_neo21.legacy.block.panels.base.CompanyTrainPanel;
import com.misaka10843.kuayue_neo21.legacy.block.panels.carport.DF11GChimneyBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.carport.DF5ChimneyBlock;
import com.misaka10843.kuayue_neo21.legacy.block.seat.YZSeatBlock;
import com.simibubi.create.api.behaviour.interaction.MovingInteractionBehaviour;
import com.simibubi.create.api.behaviour.movement.MovementBehaviour;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

public final class VehicleBehaviours {
    public static void register() {
        for (var entry : PortRegistration.ENTRIES) {
            var block = entry.get();
            if (block instanceof CompanyTrainPanel) MovingInteractionBehaviour.REGISTRY.register(block, new ExtensionInteraction());
            else if (block instanceof YZSeatBlock) MovingInteractionBehaviour.REGISTRY.register(block, new SeatInteraction());
            else if (VehicleDoorInteraction.canInteract(block.defaultBlockState())) MovingInteractionBehaviour.REGISTRY.register(block, new VehicleDoorInteraction());
            if (block instanceof DF11GChimneyBlock) MovementBehaviour.REGISTRY.register(block, new ChimneyMovement());
            else if (block instanceof IBE<?>) MovementBehaviour.REGISTRY.register(block, new MovementBehaviour() {
                @Override public boolean mustTickWhileDisabled() { return true; }
            });
        }
    }

    private static final class ExtensionInteraction extends MovingInteractionBehaviour {
        @Override
        public boolean handlePlayerInteraction(Player player, InteractionHand hand, BlockPos pos, AbstractContraptionEntity entity) {
            var info = entity.getContraption().getBlocks().get(pos);
            if (info == null || info.nbt() == null) return false;
            var tag = info.nbt();
            BlockPos parent = pos.offset(tag.getInt("px"), tag.getInt("py"), tag.getInt("pz"));
            if (parent.equals(pos)) return false;
            var target = entity.getContraption().getBlocks().get(parent);
            if (target == null || target.state().getBlock() instanceof CompanyTrainPanel) return false;
            return entity.handlePlayerInteraction(player, parent, Direction.DOWN, hand);
        }
    }

    private static final class SeatInteraction extends MovingInteractionBehaviour {
        @Override
        public boolean handlePlayerInteraction(Player player, InteractionHand hand, BlockPos pos, AbstractContraptionEntity entity) {
            if (player.isShiftKeyDown()) return false;
            if (entity.level().isClientSide) return true;
            return MountedSeats.mount(entity, player, pos, true);
        }
    }

    private static final class ChimneyMovement implements MovementBehaviour {
        @Override
        public void onSpeedChanged(MovementContext context, Vec3 oldMotion, Vec3 motion) {
            context.data.putDouble("acceleration", Math.max(0, motion.length() - oldMotion.length()));
        }

        @Override
        public void tick(MovementContext context) {
            if (!context.world.isClientSide || context.position == null || context.disabled
                    || !context.state.getValue(DF11GChimneyBlock.LIT)) return;
            boolean stopped = context.motion.lengthSqr() < 0.000001;
            double density = stopped ? 0.1 : Math.min(1, 0.5 + context.data.getDouble("acceleration") * 10);
            if (context.world.random.nextFloat() > density) return;
            Vec3 offset = context.state.getBlock() instanceof DF5ChimneyBlock ? new Vec3(0.6, 0.95, 0.6) : new Vec3(0, 0.7, 0);
            offset = context.rotation.apply(offset);
            Vec3 position = context.position.add(offset);
            context.world.addParticle(ParticleTypes.LARGE_SMOKE, position.x, position.y, position.z, 0, 0.2, 0);
        }
    }
}

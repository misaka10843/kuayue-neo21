package com.misaka10843.kuayue_neo21.content.coupling;

import com.misaka10843.kuayue_neo21.KuaYueneo21;
import com.simibubi.create.Create;
import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.CarriageContraption;
import com.simibubi.create.content.trains.entity.Train;
import java.util.ArrayList;
import java.util.Objects;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = KuaYueneo21.MODID)
public final class AutomaticCoupling {
    @SubscribeEvent
    public static void tick(ServerTickEvent.Post event) {
        var trains = new ArrayList<>(Create.RAILWAYS.trains.values());
        trains.removeIf(train -> !ready(train));
        for (Train front : trains) {
            for (Train rear : trains) {
                if (front == rear || front.graph != rear.graph || !Objects.equals(front.owner, rear.owner)
                        || rear.runtime.getSchedule() != null) continue;
                double closingSpeed = rear.speed - front.speed;
                if (closingSpeed < 0.001 || closingSpeed > 0.25) continue;
                Carriage last = front.carriages.getLast();
                Carriage first = rear.carriages.getFirst();
                var firstEntity = first.anyAvailableEntity();
                var lastEntity = last.anyAvailableEntity();
                if (firstEntity == null || lastEntity == null || firstEntity.level() != lastEntity.level()) continue;
                Vec3 rearPoint = coupler(first, true);
                Vec3 frontPoint = coupler(last, false);
                if (rearPoint == null || frontPoint == null || rearPoint.distanceToSqr(frontPoint) > 1) continue;
                Vec3 firstForward = forward(first);
                Vec3 lastForward = forward(last);
                if (firstForward == null || lastForward == null || firstForward.dot(lastForward) < 0.95) continue;
                Vec3 rearAnchor = first.leadingBogey().getAnchorPosition();
                Vec3 frontAnchor = last.trailingBogey().getAnchorPosition();
                if (rearAnchor == null || frontAnchor == null) continue;
                int spacing = Math.max(1, (int) Math.round(rearAnchor.distanceTo(frontAnchor)));
                if (!TrainTopology.merge(Create.RAILWAYS, front, rear, spacing)) continue;
                rear.leaveStation();
                front.navigation.cancelNavigation();
                front.collectInitiallyOccupiedSignalBlocks();
                Create.RAILWAYS.markTracksDirty();
                PacketDistributor.sendToAllPlayers(new TrainTopologyPayload(front.id, rear.id, 0, spacing, false));
                CouplingEffects.play((net.minecraft.server.level.ServerLevel) firstEntity.level(), frontPoint.add(rearPoint).scale(0.5));
                return;
            }
        }
    }

    private static boolean ready(Train train) {
        return Create.RAILWAYS.trains.get(train.id) == train && !train.derailed && train.graph != null
                && !train.carriages.isEmpty() && train.carriages.stream().noneMatch(Carriage::presentInMultipleDimensions);
    }

    static Vec3 forward(Carriage carriage) {
        var entity = carriage.anyAvailableEntity();
        if (entity == null || !(entity.getContraption() instanceof CarriageContraption contraption)) return null;
        Vec3 origin = entity.toGlobalVector(Vec3.ZERO, 1);
        return entity.toGlobalVector(Vec3.atLowerCornerOf(contraption.getAssemblyDirection().getNormal()).scale(-1), 1).subtract(origin).normalize();
    }

    static Vec3 coupler(Carriage carriage, boolean leading) {
        var entity = carriage.anyAvailableEntity();
        if (entity == null || !(entity.getContraption() instanceof CarriageContraption contraption)) return null;
        var direction = contraption.getAssemblyDirection();
        return contraption.getBlocks().values().stream().filter(info -> CouplerInteraction.isCoupler(info.state()))
                .filter(info -> (info.pos().get(direction.getAxis()) * direction.getAxisDirection().getStep() < 0) == leading)
                .map(info -> {
                    double extension = info.state().getBlock() instanceof CouplerPart part ? part.couplerOffset() : 1;
                    Vec3 point = entity.toGlobalVector(Vec3.atCenterOf(info.pos())
                            .add(Vec3.atLowerCornerOf(direction.getNormal()).scale(leading ? -extension : extension)), 1);
                    var anchor = (leading ? carriage.leadingBogey() : carriage.trailingBogey()).getAnchorPosition();
                    return anchor == null ? point : new Vec3(point.x, anchor.y, point.z);
                })
                .findFirst().orElse(null);
    }
}

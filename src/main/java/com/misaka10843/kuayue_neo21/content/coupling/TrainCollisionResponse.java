package com.misaka10843.kuayue_neo21.content.coupling;

import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.graph.TrackGraph;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

public final class TrainCollisionResponse {
    private record Contact(UUID first, UUID second) {
        static Contact of(Train first, Train second) {
            return first.id.compareTo(second.id) < 0 ? new Contact(first.id, second.id) : new Contact(second.id, first.id);
        }
    }
    private record Geometry(Vec3 center, Vec3 forward) {}
    private static final Map<ServerLevel, Map<Contact, Long>> CONTACTS = new WeakHashMap<>();

    private TrainCollisionResponse() {}

    public static boolean handle(ServerLevel level, Train first, Carriage movingCarriage, Train second, Vec3 position) {
        if (first == second || first.derailed || second.derailed || first.graph == null || first.graph != second.graph
                || !Double.isFinite(first.speed) || !Double.isFinite(second.speed)
                || !inDimension(first, level) || !inDimension(second, level)
                || (!TrainMass.hasPortedParts(first) && !TrainMass.hasPortedParts(second))) return false;
        Geometry firstGeometry = geometry(movingCarriage, first.graph);
        Geometry secondGeometry = nearestGeometry(second, position);
        if (firstGeometry == null || secondGeometry == null) return false;
        double alignment = firstGeometry.forward.dot(secondGeometry.forward);
        if (Math.abs(alignment) < 0.9) return false;
        double separation = secondGeometry.center.subtract(firstGeometry.center).dot(firstGeometry.forward);
        var contacts = CONTACTS.computeIfAbsent(level, ignored -> new HashMap<>());
        long now = level.getGameTime();
        contacts.entrySet().removeIf(entry -> entry.getValue() < now);
        Contact pair = Contact.of(first, second);
        if (contacts.containsKey(pair)) return true;

        double orientation = Math.signum(alignment);
        double secondSpeed = second.speed * orientation;
        double relativeSpeed = first.speed - secondSpeed;
        double closingSpeed = relativeSpeed * Math.signum(separation);
        if (closingSpeed <= 0.000001) {
            // The native detector can report contact after the current movement crossed the other center.
            if (separation * (separation + relativeSpeed) > 0) return true;
            closingSpeed = Math.abs(relativeSpeed);
        }
        if (closingSpeed <= 0.000001) return true;
        var result = ImpactPhysics.resolve(TrainMass.of(first), TrainMass.of(second), first.speed, secondSpeed);
        stopNavigation(first, result.firstSpeed());
        stopNavigation(second, result.secondSpeed() * orientation);
        contacts.put(pair, now + 3);
        if (closingSpeed > 0.2)
            level.explode(null, position.x, position.y, position.z, (float) Math.min(3 * closingSpeed, 5), Level.ExplosionInteraction.NONE);
        CouplingEffects.play(level, position);
        PacketDistributor.sendToAllPlayers(new TrainImpactPayload(first.id, second.id, first.speed, second.speed));
        return true;
    }

    private static void stopNavigation(Train train, double speed) {
        train.navigation.cancelNavigation();
        train.runtime.paused = true;
        train.targetSpeed = 0;
        train.speedBeforeStall = null;
        train.speed = speed;
        train.leaveStation();
        for (var carriage : train.carriages) carriage.forEachPresentEntity(entity -> entity.getIndirectPassengers().forEach(passenger -> {
            if (passenger instanceof Player player && !entity.getControllingPlayer().filter(player.getUUID()::equals).isPresent())
                AllAdvancements.TRAIN_CRASH.awardTo(player);
        }));
        if (train.backwardsDriver != null) AllAdvancements.TRAIN_CRASH_BACKWARDS.awardTo(train.backwardsDriver);
    }

    private static Geometry nearestGeometry(Train train, Vec3 position) {
        Geometry nearest = null;
        double distance = Double.MAX_VALUE;
        for (var carriage : train.carriages) {
            Geometry candidate = geometry(carriage, train.graph);
            if (candidate != null && candidate.center.distanceToSqr(position) < distance) {
                nearest = candidate;
                distance = candidate.center.distanceToSqr(position);
            }
        }
        return nearest;
    }

    private static boolean inDimension(Train train, ServerLevel level) {
        return !train.carriages.isEmpty() && train.carriages.stream().allMatch(carriage -> {
            var front = carriage.getLeadingPoint();
            var rear = carriage.getTrailingPoint();
            return front.node1 != null && rear.node1 != null && front.edge != null && rear.edge != null
                    && !front.edge.isInterDimensional() && !rear.edge.isInterDimensional()
                    && front.node1.getLocation().dimension.equals(level.dimension())
                    && rear.node1.getLocation().dimension.equals(level.dimension());
        });
    }

    private static Geometry geometry(Carriage carriage, TrackGraph graph) {
        var leading = carriage.getLeadingPoint();
        var trailing = carriage.getTrailingPoint();
        if (leading.node1 == null || trailing.node1 == null || leading.edge == null || trailing.edge == null) return null;
        Vec3 first = leading.getPosition(graph);
        Vec3 second = trailing.getPosition(graph);
        Vec3 forward = first.subtract(second);
        if (forward.lengthSqr() < 0.000001) return null;
        return new Geometry(first.add(second).scale(0.5), forward.normalize());
    }
}

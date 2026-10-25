package com.misaka10843.kuayue_neo21.data;

import com.misaka10843.kuayue_neo21.content.coupling.ImpactPhysics;
import java.util.List;
import java.util.Map;

final class CollisionIntegrationAudit {
    static Map<String, Integer> run(List<String> failures) {
        int cases = 0;
        for (double firstMass : new double[]{1, 4, 400}) for (double secondMass : new double[]{1, 9, 200})
            for (double firstSpeed : new double[]{-1, -0.1, 0, 0.25, 2}) for (double secondSpeed : new double[]{-1.5, 0, 0.1}) {
                var result = ImpactPhysics.resolve(firstMass, secondMass, firstSpeed, secondSpeed);
                double before = firstMass * firstSpeed + secondMass * secondSpeed;
                double after = firstMass * result.firstSpeed() + secondMass * result.secondSpeed();
                if (Math.abs(before - after) > 1e-8) failures.add("Collision lost momentum");
                double energyBefore = firstMass * firstSpeed * firstSpeed + secondMass * secondSpeed * secondSpeed;
                double energyAfter = firstMass * result.firstSpeed() * result.firstSpeed() + secondMass * result.secondSpeed() * result.secondSpeed();
                if (energyAfter > energyBefore + 1e-8) failures.add("Collision created kinetic energy");
                double relative = Math.abs(firstSpeed - secondSpeed);
                if (Math.abs(Math.abs(result.firstSpeed() - result.secondSpeed()) - Math.min(relative, 0.4)) > 1e-8)
                    failures.add("Collision restitution differs from source response");
                cases++;
            }
        var equal = ImpactPhysics.resolve(1, 1, 0.1, 0);
        if (Math.abs(equal.firstSpeed()) > 1e-8 || Math.abs(equal.secondSpeed() - 0.1) > 1e-8) failures.add("Equal-mass collision did not transfer speed");
        var heavy = ImpactPhysics.resolve(9, 1, 0.1, 0);
        if (Math.abs(heavy.firstSpeed() - 0.08) > 1e-8 || Math.abs(heavy.secondSpeed() - 0.18) > 1e-8)
            failures.add("Unequal-mass collision assigned results to wrong trains");
        try { ImpactPhysics.resolve(0, 1, 1, 0); failures.add("Collision accepted zero mass"); }
        catch (IllegalArgumentException expected) { }
        var packet = new com.misaka10843.kuayue_neo21.content.coupling.TrainImpactPayload(java.util.UUID.randomUUID(),
                java.util.UUID.randomUUID(), -0.1, 0.18);
        var buffer = new net.minecraft.network.RegistryFriendlyByteBuf(io.netty.buffer.Unpooled.buffer(), net.minecraft.core.RegistryAccess.EMPTY);
        try {
            com.misaka10843.kuayue_neo21.content.coupling.TrainImpactPayload.CODEC.encode(buffer, packet);
            if (!com.misaka10843.kuayue_neo21.content.coupling.TrainImpactPayload.CODEC.decode(buffer).equals(packet))
                failures.add("Collision velocity packet lost IDs or signed speeds");
        } finally { buffer.release(); }
        return Map.of("momentumAndEnergyCases", cases, "knownSpeedTransfers", 2, "velocityPacketRoundTrips", 1);
    }
}

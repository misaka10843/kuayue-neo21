package com.misaka10843.kuayue_neo21.data;

import com.misaka10843.kuayue_neo21.content.PortRegistration;
import com.misaka10843.kuayue_neo21.content.coupling.TrainTopology;
import com.simibubi.create.content.trains.GlobalRailwayManager;
import com.simibubi.create.content.trains.bogey.AbstractBogeyBlock;
import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.CarriageBogey;
import com.simibubi.create.content.trains.entity.Train;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;

final class CouplingIntegrationAudit {
    static Map<String, Integer> run(List<String> failures) {
        var manager = new GlobalRailwayManager();
        var carriages = new ArrayList<Carriage>();
        var block = (AbstractBogeyBlock<?>) BuiltInRegistries.BLOCK.get(PortRegistration.resource("pk209p_bogey"));
        for (int i = 0; i < 4; i++) {
            var data = new CompoundTag();
            data.putInt("CouplingAudit", i);
            carriages.add(new Carriage(new CarriageBogey(block, false, data), null, 0));
        }
        var train = new Train(UUID.randomUUID(), UUID.randomUUID(), null, new ArrayList<>(carriages),
                new ArrayList<>(List.of(9, 11, 13)), false, 0);
        manager.addTrain(train);
        var separated = TrainTopology.split(manager, train, UUID.randomUUID(), 1);
        if (separated == null || !train.carriageSpacing.equals(List.of(9)) || !separated.carriageSpacing.equals(List.of(13)))
            failures.add("Train split lost spacing or carriage groups");
        else {
            if (train.carriages.get(0) != carriages.get(0) || separated.carriages.get(0) != carriages.get(2)
                    || separated.carriages.get(0).train != separated) failures.add("Train split lost carriage identity");
            if (!TrainTopology.merge(manager, train, separated, 11) || !train.carriages.equals(carriages)
                    || !train.carriageSpacing.equals(List.of(9, 11, 13)) || manager.trains.size() != 1)
                failures.add("Train merge failed topology round trip");
        }
        for (int i = 0; i < carriages.size(); i++) {
            if (carriages.get(i).train != train || carriages.get(i).leadingBogey().bogeyData.getInt("CouplingAudit") != i)
                failures.add("Coupling lost native bogey data or train reference");
        }
        if (TrainTopology.split(manager, train, UUID.randomUUID(), 3) != null || TrainTopology.merge(manager, train, train, 2))
            failures.add("Invalid topology mutation was accepted");
        return Map.of("preservedCarriages", carriages.size(), "splitMergeRoundTrips", 1, "rejectedInvalidOperations", 2);
    }
}

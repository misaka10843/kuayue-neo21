package com.misaka10843.kuayue_neo21.content.coupling;

import com.misaka10843.kuayue_neo21.mixin.TrainTopologyAccessor;
import com.simibubi.create.content.trains.GlobalRailwayManager;
import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.Train;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class TrainTopology {
    private TrainTopology() {}

    public static Train split(GlobalRailwayManager manager, Train train, UUID newId, int boundary) {
        if (boundary < 0 || boundary >= train.carriages.size() - 1 || manager.trains.containsKey(newId)) return null;
        List<Carriage> front = new ArrayList<>(train.carriages.subList(0, boundary + 1));
        List<Carriage> rear = new ArrayList<>(train.carriages.subList(boundary + 1, train.carriages.size()));
        List<Integer> frontSpacing = new ArrayList<>(train.carriageSpacing.subList(0, boundary));
        List<Integer> rearSpacing = new ArrayList<>(train.carriageSpacing.subList(boundary + 1, train.carriageSpacing.size()));
        train.carriages = front;
        train.carriageSpacing = frontSpacing;
        train.doubleEnded = doubleEnded(front);
        ((TrainTopologyAccessor) train).kuayue$setStress(new double[frontSpacing.size()]);
        Train separated = new Train(newId, train.owner, train.graph, rear, rearSpacing, doubleEnded(rear),
                train.name.copy(), train.icon, train.mapColorIndex);
        separated.speed = train.speed;
        separated.targetSpeed = 0;
        separated.throttle = train.throttle;
        manager.addTrain(separated);
        bind(train);
        bind(separated);
        return separated;
    }

    public static boolean merge(GlobalRailwayManager manager, Train front, Train rear, int spacing) {
        if (front == rear || spacing < 1 || spacing > 64) return false;
        int frontCount = front.carriages.size();
        int rearCount = rear.carriages.size();
        List<Carriage> carriages = new ArrayList<>(front.carriages);
        carriages.addAll(rear.carriages);
        List<Integer> distances = new ArrayList<>(front.carriageSpacing);
        distances.add(spacing);
        distances.addAll(rear.carriageSpacing);
        front.speed = (front.speed * frontCount + rear.speed * rearCount) / (frontCount + rearCount);
        front.carriages = carriages;
        front.carriageSpacing = distances;
        front.doubleEnded = doubleEnded(carriages);
        ((TrainTopologyAccessor) front).kuayue$setStress(new double[distances.size()]);
        manager.removeTrain(rear.id);
        bind(front);
        return true;
    }

    private static boolean doubleEnded(List<Carriage> carriages) {
        return carriages.stream().anyMatch(carriage -> carriage.presentConductors.getSecond());
    }

    private static void bind(Train train) {
        for (var carriage : train.carriages) {
            carriage.setTrain(train);
            for (var dimension : carriage.getPresentDimensions()) {
                var entity = carriage.getDimensional(dimension).entity.get();
                if (entity != null) {
                    // setCarriage swaps in the server cargo manager, which is uninitialized on clients.
                    entity.trainId = train.id;
                    entity.carriageIndex = train.carriages.indexOf(carriage);
                }
            }
        }
    }
}

package com.misaka10843.kuayue_neo21.client;

import com.misaka10843.kuayue_neo21.content.coupling.TrainTopology;
import com.misaka10843.kuayue_neo21.content.coupling.TrainTopologyPayload;
import com.simibubi.create.CreateClient;

public final class ClientTrainCoupling {
    public static void apply(TrainTopologyPayload payload) {
        var manager = CreateClient.RAILWAYS;
        var front = manager.trains.get(payload.front());
        if (front == null) return;
        if (payload.split()) TrainTopology.split(manager, front, payload.rear(), payload.boundary());
        else {
            var rear = manager.trains.get(payload.rear());
            if (rear != null) TrainTopology.merge(manager, front, rear, payload.spacing());
        }
    }
}

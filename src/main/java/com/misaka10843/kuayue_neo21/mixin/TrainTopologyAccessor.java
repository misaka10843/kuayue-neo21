package com.misaka10843.kuayue_neo21.mixin;

import com.simibubi.create.content.trains.entity.Train;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Train.class)
public interface TrainTopologyAccessor {
    @Accessor("stress") void kuayue$setStress(double[] stress);
}

package com.misaka10843.kuayue_neo21.mixin;

import com.simibubi.create.content.trains.entity.Carriage;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Carriage.class)
public interface CarriageDataAccessor {
    @Accessor("serialisedEntity") CompoundTag kuayue$savedEntity();
}

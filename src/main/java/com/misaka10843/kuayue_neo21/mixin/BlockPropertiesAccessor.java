package com.misaka10843.kuayue_neo21.mixin;

import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockBehaviour.Properties.class)
public interface BlockPropertiesAccessor {
    @Accessor("canOcclude") void kuayue$setCanOcclude(boolean value);
}

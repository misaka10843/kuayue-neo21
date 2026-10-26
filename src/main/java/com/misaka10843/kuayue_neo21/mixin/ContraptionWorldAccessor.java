package com.misaka10843.kuayue_neo21.mixin;

import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.ContraptionWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ContraptionWorld.class)
public interface ContraptionWorldAccessor {
    @Accessor("contraption") Contraption kuayue$getContraption();
}

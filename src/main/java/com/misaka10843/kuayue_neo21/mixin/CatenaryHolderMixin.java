package com.misaka10843.kuayue_neo21.mixin;

import com.misaka10843.kuayue_neo21.content.infrastructure.CatenaryCompatibility;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "com.george_vi.electroenergetics.content.railway_electrification.catenary.CatenaryHolderBlockEntity", remap = false)
public abstract class CatenaryHolderMixin {
    @Inject(method = "getPoleWidth", at = @At("HEAD"), cancellable = true)
    private void kuayue$recognizeStructure(BlockPos pos, CallbackInfoReturnable<Integer> callback) {
        BlockEntity entity = (BlockEntity) (Object) this;
        if (entity.getLevel() == null) return;
        int width = CatenaryCompatibility.poleWidth(entity.getLevel(), pos);
        if (width > 0) callback.setReturnValue(width);
    }
}

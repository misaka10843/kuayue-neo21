package com.misaka10843.kuayue_neo21.mixin;

import com.misaka10843.kuayue_neo21.content.infrastructure.CatenaryCompatibility;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "com.george_vi.electroenergetics.content.pole.PoleMountBlock", remap = false)
public abstract class PoleMountMixin {
    @Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
    private void kuayue$mountOnStructure(BlockState state, LevelReader level, BlockPos pos,
                                        CallbackInfoReturnable<Boolean> callback) {
        BlockPos support = pos.relative(state.getValue(BlockStateProperties.HORIZONTAL_FACING));
        if (CatenaryCompatibility.poleWidth(level, support) > 0) callback.setReturnValue(true);
    }
}

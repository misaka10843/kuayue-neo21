package com.misaka10843.kuayue_neo21.mixin;

import com.misaka10843.kuayue_neo21.content.VehicleDoorAnimations;
import com.simibubi.create.content.contraptions.render.ClientContraption;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientContraption.class)
public abstract class VehicleDoorAnimationMixin {
    @Shadow @Final public List<BlockEntity> renderedBlockEntityView;
    @Unique private Map<BlockPos, VehicleDoorAnimations.Progress> kuayue$doorProgress = Map.of();

    @Inject(method = "resetRenderLevel", at = @At("HEAD"))
    private void kuayue$saveDoorProgress(CallbackInfo callback) {
        kuayue$doorProgress = VehicleDoorAnimations.capture(renderedBlockEntityView);
    }

    @Inject(method = "resetRenderLevel", at = @At("RETURN"))
    private void kuayue$restoreDoorProgress(CallbackInfo callback) {
        VehicleDoorAnimations.restore(renderedBlockEntityView, kuayue$doorProgress);
        kuayue$doorProgress = Map.of();
    }
}

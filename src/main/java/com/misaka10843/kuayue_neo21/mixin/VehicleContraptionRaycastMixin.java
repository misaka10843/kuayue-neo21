package com.misaka10843.kuayue_neo21.mixin;

import com.misaka10843.kuayue_neo21.content.VehicleContraptionRaycast;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.ContraptionHandlerClient;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ContraptionHandlerClient.class)
public abstract class VehicleContraptionRaycastMixin {
    @Inject(method = "rayTraceContraption", at = @At("HEAD"), cancellable = true)
    private static void kuayue$traceVehicleParts(Vec3 origin, Vec3 target, AbstractContraptionEntity entity, CallbackInfoReturnable<BlockHitResult> callback) {
        var contraption = entity.getContraption();
        if (contraption == null || !VehicleContraptionRaycast.containsVehicleParts(contraption)) return;
        callback.setReturnValue(VehicleContraptionRaycast.trace(entity.toLocalVector(origin, 1), entity.toLocalVector(target, 1), contraption));
    }
}

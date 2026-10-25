package com.misaka10843.kuayue_neo21.mixin;

import com.misaka10843.kuayue_neo21.content.infrastructure.PantographMounting;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.render.ContraptionMatrices;
import com.simibubi.create.foundation.virtualWorld.VirtualRenderWorld;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "com.george_vi.electroenergetics.content.railway_electrification.pantograph.PantographMovementBehaviour", remap = false)
public abstract class PantographMovementMixin {
    @ModifyExpressionValue(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;add(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;", ordinal = 0))
    private Vec3 kuayue$moveSearch(Vec3 original, MovementContext context) {
        return original.add(context.rotation.apply(new Vec3(0, context.blockEntityData.getDouble(PantographMounting.OFFSET_TAG), 0)));
    }
    @Inject(method = "getConnectorPos", at = @At("RETURN"), cancellable = true)
    private void kuayue$moveContact(float extension, MovementContext context, CallbackInfoReturnable<Vec3> callback) {
        double offset = context.blockEntityData.getDouble(PantographMounting.OFFSET_TAG);
        callback.setReturnValue(callback.getReturnValue().add(context.rotation.apply(new Vec3(0, offset, 0))));
    }

    @Inject(method = "renderInContraption", at = @At("HEAD"))
    private void kuayue$lowerBase(MovementContext context, VirtualRenderWorld world, ContraptionMatrices matrices,
                                  MultiBufferSource buffer, CallbackInfo callback) {
        matrices.getModel().pushPose();
        matrices.getModel().translate(0, context.blockEntityData.getDouble(PantographMounting.OFFSET_TAG), 0);
    }

    @Inject(method = "renderInContraption", at = @At("RETURN"))
    private void kuayue$restorePose(MovementContext context, VirtualRenderWorld world, ContraptionMatrices matrices,
                                    MultiBufferSource buffer, CallbackInfo callback) {
        matrices.getModel().popPose();
    }
}

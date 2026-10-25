package com.misaka10843.kuayue_neo21.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.misaka10843.kuayue_neo21.content.infrastructure.PantographMounting;
import com.simibubi.create.content.trains.entity.Carriage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "com.george_vi.electroenergetics.simulation.infrastructure.CatenaryModule", remap = false)
public abstract class CatenaryCircuitMixin {
    @Redirect(method = "buildCircuit", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;atLowerCornerOf(Lnet/minecraft/core/Vec3i;)Lnet/minecraft/world/phys/Vec3;"))
    private Vec3 kuayue$moveElectricalContact(Vec3i pos, @Local Carriage carriage) {
        return Vec3.atLowerCornerOf(pos).add(0, PantographMounting.movingOffset(carriage, new BlockPos(pos)), 0);
    }
}

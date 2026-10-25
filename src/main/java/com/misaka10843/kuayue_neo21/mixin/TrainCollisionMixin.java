package com.misaka10843.kuayue_neo21.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.misaka10843.kuayue_neo21.content.coupling.TrainCollisionResponse;
import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.Train;
import net.createmod.catnip.data.Pair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Train.class)
public abstract class TrainCollisionMixin {
    @ModifyExpressionValue(method = "collideWithOtherTrains", at = @At(value = "INVOKE",
            target = "Lcom/simibubi/create/content/trains/entity/Train;findCollidingTrain(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/resources/ResourceKey;)Lnet/createmod/catnip/data/Pair;"))
    private Pair<Train, Vec3> kuayue$exchangeMomentum(Pair<Train, Vec3> collision, Level level, Carriage carriage) {
        if (collision != null && level instanceof ServerLevel server
                && TrainCollisionResponse.handle(server, (Train) (Object) this, carriage, collision.getFirst(), collision.getSecond())) return null;
        return collision;
    }
}

package com.misaka10843.kuayue_neo21.mixin;

import com.misaka10843.kuayue_neo21.content.MountedSeats;
import com.misaka10843.kuayue_neo21.legacy.block.seat.YZSeatBlock;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.actors.seat.SeatBlock;
import com.simibubi.create.content.contraptions.actors.seat.SeatEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Contraption.class, remap = false)
public abstract class ContraptionSeatsMixin {
    @Unique private final Map<BlockPos, List<Entity>> kuayue$passengers = new HashMap<>();
    @org.spongepowered.asm.mixin.Shadow protected abstract BlockPos toLocalPos(BlockPos pos);

    @Inject(method = "moveSeat", at = @At("RETURN"))
    private void kuayue$expandSeats(Level world, BlockPos pos, CallbackInfo callback) {
        if (!(world.getBlockState(pos).getBlock() instanceof YZSeatBlock seat)) return;
        Contraption contraption = (Contraption) (Object) this;
        BlockPos local = toLocalPos(pos);
        for (int i = 1; i < seat.getSeatSize(); i++) contraption.getSeats().add(local);
        List<Entity> passengers = world.getEntitiesOfClass(SeatEntity.class, new AABB(pos).inflate(1.5)).stream()
                .filter(entity -> entity.blockPosition().equals(pos) || entity instanceof com.misaka10843.kuayue_neo21.legacy.block.seat.M1SeatEntity meter && pos.equals(meter.anchor()))
                .flatMap(entity -> entity.getPassengers().stream()).toList();
        kuayue$passengers.put(local, passengers);
    }

    @Inject(method = "onEntityInitialize", at = @At("RETURN"))
    private void kuayue$mountPassengers(Level world, AbstractContraptionEntity entity, CallbackInfo callback) {
        if (!world.isClientSide) kuayue$passengers.forEach((root, passengers) -> passengers.forEach(passenger -> MountedSeats.mount(entity, passenger, root, false)));
        kuayue$passengers.clear();
    }

    @Redirect(method = "addPassengersToWorld", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/contraptions/actors/seat/SeatBlock;isSeatOccupied(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Z"))
    private boolean kuayue$checkCapacity(Level world, BlockPos pos) {
        return !(world.getBlockState(pos).getBlock() instanceof YZSeatBlock) && SeatBlock.isSeatOccupied(world, pos);
    }

    @Redirect(method = "addPassengersToWorld", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/contraptions/actors/seat/SeatBlock;sitDown(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;)V"))
    private void kuayue$restorePassenger(Level world, BlockPos pos, Entity passenger) {
        if (world.getBlockState(pos).getBlock() instanceof YZSeatBlock seat) seat.sitAvailable(world, world.getBlockState(pos), pos, passenger);
        else SeatBlock.sitDown(world, pos, passenger);
    }
}

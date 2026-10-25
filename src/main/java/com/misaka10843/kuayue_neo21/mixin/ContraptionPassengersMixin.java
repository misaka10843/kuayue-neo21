package com.misaka10843.kuayue_neo21.mixin;

import com.misaka10843.kuayue_neo21.content.MountedSeats;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.actors.seat.SeatEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = AbstractContraptionEntity.class, remap = false)
public abstract class ContraptionPassengersMixin {
    @Inject(method = "handlePlayerInteraction", at = @At("HEAD"), cancellable = true)
    private void kuayue$interactSeat(net.minecraft.world.entity.player.Player player, net.minecraft.core.BlockPos root,
                                    net.minecraft.core.Direction side, net.minecraft.world.InteractionHand hand,
                                    CallbackInfoReturnable<Boolean> callback) {
        var entity = (AbstractContraptionEntity) (Object) this;
        var contraption = entity.getContraption();
        if (contraption == null || MountedSeats.seat(contraption, root) == null) return;
        if (com.simibubi.create.AllItems.WRENCH.isIn(player.getItemInHand(hand)) || player.isShiftKeyDown()) {
            callback.setReturnValue(false);
            return;
        }
        callback.setReturnValue(entity.level().isClientSide || MountedSeats.mount(entity,
                com.simibubi.create.content.contraptions.actors.seat.SeatBlock.getLeashed(entity.level(), player).or(player), root, true));
    }

    @Inject(method = "addSittingPassenger", at = @At("HEAD"), cancellable = true)
    private void kuayue$mountSlot(Entity passenger, int seatIndex, CallbackInfo callback) {
        var entity = (AbstractContraptionEntity) (Object) this;
        var contraption = entity.getContraption();
        if (contraption == null || seatIndex < 0 || seatIndex >= contraption.getSeats().size()) return;
        var root = contraption.getSeats().get(seatIndex);
        if (MountedSeats.seat(contraption, root) == null) return;
        MountedSeats.mount(entity, passenger, root, false);
        callback.cancel();
    }

    @Inject(method = "getPassengerPosition", at = @At("HEAD"), cancellable = true)
    private void kuayue$positionSlot(Entity passenger, float partialTick, CallbackInfoReturnable<Vec3> callback) {
        var entity = (AbstractContraptionEntity) (Object) this;
        var contraption = entity.getContraption();
        if (contraption == null) return;
        Integer index = contraption.getSeatMapping().get(passenger.getUUID());
        if (index == null || index < 0 || index >= contraption.getSeats().size()) return;
        var root = contraption.getSeats().get(index);
        var seat = MountedSeats.seat(contraption, root);
        if (seat == null) return;
        int slot = MountedSeats.slot(contraption, index);
        if (slot < 0 || slot >= seat.getSeatSize()) return;
        var offset = seat.getOffset(contraption.getBlocks().get(root).state(), slot);
        double height = passenger.getBoundingBox().getYsize();
        Vec3 local = Vec3.atLowerCornerOf(root).add(0.5 + offset.x,
                -passenger.getVehicleAttachmentPoint(entity).y + height + 0.125 - SeatEntity.getCustomEntitySeatOffset(passenger) + offset.y,
                0.5 + offset.z);
        callback.setReturnValue(entity.toGlobalVector(local, partialTick).add(0, 0.5 - height, 0));
    }
}

package com.misaka10843.kuayue_neo21.content;

import com.misaka10843.kuayue_neo21.legacy.block.seat.YZSeatBlock;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.sync.ContraptionSeatMappingPacket;
import java.util.ArrayList;
import net.createmod.catnip.platform.CatnipServices;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;

public final class MountedSeats {
    public static YZSeatBlock seat(Contraption contraption, BlockPos pos) {
        var info = contraption.getBlocks().get(pos);
        return info != null && info.state().getBlock() instanceof YZSeatBlock seat ? seat : null;
    }

    public static int slot(Contraption contraption, int seatIndex) {
        BlockPos root = contraption.getSeats().get(seatIndex);
        int slot = -1;
        for (int i = 0; i <= seatIndex; i++) if (root.equals(contraption.getSeats().get(i))) slot++;
        return slot;
    }

    public static boolean mount(AbstractContraptionEntity entity, Entity passenger, BlockPos root, boolean cycle) {
        Contraption contraption = entity.getContraption();
        YZSeatBlock seat = seat(contraption, root);
        if (seat == null) return false;
        if (entity.level().isClientSide) return true;
        var mapping = contraption.getSeatMapping();
        Integer previous = mapping.get(passenger.getUUID());
        if (previous != null && !cycle) return true;
        var candidates = new ArrayList<Integer>();
        for (int i = 0; i < contraption.getSeats().size(); i++) if (root.equals(contraption.getSeats().get(i))) candidates.add(i);
        if (candidates.isEmpty()) return false;
        int start = previous == null ? 0 : (candidates.indexOf(previous) + 1) % candidates.size();
        for (int step = 0; step < candidates.size(); step++) {
            int index = candidates.get((start + step) % candidates.size());
            if (mapping.containsValue(index)) continue;
            if (passenger.getVehicle() != entity && !passenger.startRiding(entity, true)) return false;
            mapping.put(passenger.getUUID(), index);
            if (passenger instanceof TamableAnimal animal) animal.setInSittingPose(true);
            CatnipServices.NETWORK.sendToClientsTrackingEntity(entity, new ContraptionSeatMappingPacket(entity.getId(), mapping));
            return true;
        }
        return true;
    }
}

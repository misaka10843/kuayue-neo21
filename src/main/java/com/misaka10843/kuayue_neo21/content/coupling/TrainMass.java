package com.misaka10843.kuayue_neo21.content.coupling;

import com.misaka10843.kuayue_neo21.mixin.CarriageDataAccessor;
import com.misaka10843.kuayue_neo21.mixin.MountedStorageAccessor;
import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.Train;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

public final class TrainMass {
    private TrainMass() {}

    private static CompoundTag savedBlocks(Carriage carriage) {
        return ((CarriageDataAccessor) carriage).kuayue$savedEntity().getCompound("Contraption").getCompound("Blocks");
    }

    public static boolean hasPortedParts(Train train) {
        for (var carriage : train.carriages) {
            var entity = carriage.anyAvailableEntity();
            if (entity != null && entity.getContraption() != null) {
                if (entity.getContraption().getBlocks().values().stream().anyMatch(info ->
                        BuiltInRegistries.BLOCK.getKey(info.state().getBlock()).getNamespace().equals("kuayue"))) return true;
            } else {
                for (var entry : savedBlocks(carriage).getList("Palette", Tag.TAG_COMPOUND))
                    if (((CompoundTag) entry).getString("Name").startsWith("kuayue:")) return true;
            }
        }
        return false;
    }

    public static double of(Train train) {
        double mass = 0;
        for (var carriage : train.carriages) {
            var entity = carriage.anyAvailableEntity();
            int blocks = entity != null && entity.getContraption() != null ? entity.getContraption().getBlocks().size()
                    : savedBlocks(carriage).getList("BlockList", Tag.TAG_COMPOUND).size();
            mass += Math.max(1, blocks);
            var cargo = carriage.storage;
            if (cargo == null || !((MountedStorageAccessor) cargo).kuayue$isInitialized()) continue;
            var items = cargo.getMountedItems();
            for (int i = 0; i < items.getSlots(); i++) mass += items.getStackInSlot(i).getCount() * 0.6;
            var fluids = cargo.getFluids();
            for (int i = 0; i < fluids.getTanks(); i++) mass += fluids.getFluidInTank(i).getAmount() * 0.4;
        }
        return Math.max(1, mass);
    }
}

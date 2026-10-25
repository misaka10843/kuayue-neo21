package com.misaka10843.kuayue_neo21.legacy.block.seat;

import com.simibubi.create.content.contraptions.actors.seat.SeatEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import com.misaka10843.kuayue_neo21.legacy.initial.AllEntities;

public class M1SeatEntity extends SeatEntity {
    private BlockPos pos;
    public M1SeatEntity(EntityType<?> p_i48580_1_, Level p_i48580_2_) {
        super(p_i48580_1_, p_i48580_2_);
        pos = null;
    }

    public M1SeatEntity(Level world, BlockPos pos) {
        super(AllEntities.M1_SEAT.get(), world);
        this.noPhysics = true;
        this.pos = pos;
    }

    @Override
    public void tick() {
        if (level().isClientSide)
            return;
        if (!getPassengers().isEmpty() && pos != null && level().getBlockState(pos).getBlock() instanceof M1SeatBlock)
            return;
        this.discard();
    }

    public BlockPos anchor() { return pos; }

    @Override
    protected void addAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (pos != null) tag.putLong("Anchor", pos.asLong());
    }

    @Override
    protected void readAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Anchor")) pos = BlockPos.of(tag.getLong("Anchor"));
    }
}

package com.misaka10843.kuayue_neo21.content.bogey;

import com.simibubi.create.content.trains.bogey.AbstractBogeyBlockEntity;
import com.simibubi.create.content.trains.bogey.BogeyStyle;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public final class VehicleBogeyEntity extends AbstractBogeyBlockEntity {
    public VehicleBogeyEntity(BlockPos pos, BlockState state) { super(VehicleBogeys.ENTITY.getType(), pos, state); }
    @Override public BogeyStyle getDefaultStyle() { return ((VehicleBogeyBlock) getBlockState().getBlock()).getDefaultStyle(); }

    @Override protected net.minecraft.world.phys.AABB createRenderBoundingBox() {
        return new net.minecraft.world.phys.AABB(getBlockPos()).inflate(6, 3, 6);
    }
}

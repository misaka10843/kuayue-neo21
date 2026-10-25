package com.misaka10843.kuayue_neo21.content.infrastructure;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.CompanyTrainBlockEntity;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.CompanyTrainPanel;

public final class CatenaryCompatibility {
    private CatenaryCompatibility() {}

    public static int poleWidth(BlockGetter level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (state.getBlock() instanceof CompanyTrainPanel && level.getBlockEntity(pos) instanceof CompanyTrainBlockEntity entity) {
            BlockPos root = pos.offset(entity.getParentPos());
            if (!root.equals(pos) && level.getBlockState(root).getBlock() instanceof OverheadTruss truss
                    && truss.getOccupiedPositions(level, root, level.getBlockState(root)).contains(pos)) return 8;
            return 0;
        }
        if (!(state.getBlock() instanceof SimpleOverheadLinePillarBlock
                || state.getBlock() instanceof LinkedTrussBlock
                || state.getBlock() instanceof OverheadTruss)) {
            return 0;
        }
        VoxelShape shape = state.getShape(level, pos);
        if (shape.isEmpty()) return 0;
        return Mth.clamp((int) Math.round(Math.min(shape.bounds().getXsize(), shape.bounds().getZsize()) * 16), 1, 16);
    }
}

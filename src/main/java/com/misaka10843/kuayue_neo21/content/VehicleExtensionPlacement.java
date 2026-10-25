package com.misaka10843.kuayue_neo21.content;

import com.misaka10843.kuayue_neo21.legacy.block.panels.base.CompanyTrainPanel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;

public final class VehicleExtensionPlacement {
    public static boolean canClaim(BlockGetter level, BlockPos root, BlockPos part) {
        var state = level.getBlockState(part);
        if (state.getBlock() instanceof CompanyTrainPanel) return root.equals(CompanyTrainPanel.getParentPos(level, part));
        return state.isAir() && level.getBlockEntity(part) == null;
    }
}

package com.misaka10843.kuayue_neo21.content;

import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;

public final class SchematicPlacementRules {
    public static boolean needsPlacementCallback(BlockState state) {
        return !(state.getBlock() instanceof BedBlock) || state.getValue(BedBlock.PART) == BedPart.FOOT;
    }
}

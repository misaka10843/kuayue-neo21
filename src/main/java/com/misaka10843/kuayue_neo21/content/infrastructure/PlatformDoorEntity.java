package com.misaka10843.kuayue_neo21.content.infrastructure;

import com.misaka10843.kuayue_neo21.legacy.block.panels.block_entity.CustomRenderedEndfaceEntity;
import com.misaka10843.kuayue_neo21.legacy.initial.AllBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public final class PlatformDoorEntity extends CustomRenderedEndfaceEntity {
    public PlatformDoorEntity(BlockPos pos, BlockState state) { super(AllBlocks.PLATFORM_DOOR_ENTITY.getType(), pos, state); }
}

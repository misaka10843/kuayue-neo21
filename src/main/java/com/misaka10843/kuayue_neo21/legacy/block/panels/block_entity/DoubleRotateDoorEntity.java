package com.misaka10843.kuayue_neo21.legacy.block.panels.block_entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import com.misaka10843.kuayue_neo21.legacy.initial.AllBlocks;

import static com.misaka10843.kuayue_neo21.legacy.block.panels.door.DoubleRotateDoorBlock.HINGE;

public class DoubleRotateDoorEntity extends CustomRenderedEndfaceEntity implements IContraptionMovementBlockEntity {

    private DoorHingeSide hinge;

    public DoubleRotateDoorEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
        this.hinge = pBlockState.getValue(HINGE);
    }

    public DoubleRotateDoorEntity(BlockPos pos, BlockState state) {
        this(AllBlocks.DOUBLE_ROTATE_DOOR_ENTITY.getType(), pos, state);
    }

    public DoorHingeSide getHinge() {
        return this.hinge;
    }
}

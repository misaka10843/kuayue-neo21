package com.misaka10843.kuayue_neo21.content.infrastructure;

import com.misaka10843.kuayue_neo21.legacy.block.panels.base.TrainPanelProperties.DoorType;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.TrainPanelShapes;
import com.misaka10843.kuayue_neo21.legacy.block.panels.door.TrainDoorBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.end_face.CustomRenderedEndfaceBlock;
import com.misaka10843.kuayue_neo21.legacy.initial.AllBlocks;
import com.misaka10843.kuayue_neo21.content.LegacyMaterial;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class PlatformDoorBlock extends CustomRenderedEndfaceBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public PlatformDoorBlock(Properties properties) {
        super(properties, DoorType.SLIDE, "door/platform_door/platform_door_left", "door/platform_door/platform_door_right", "door/platform_door/platform_door_side");
        registerDefaultState(defaultBlockState().setValue(POWERED, false));
    }

    @Override protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) { super.createBlockStateDefinition(builder.add(POWERED)); }
    @Override public BlockEntityType<PlatformDoorEntity> getBlockEntityType() { return AllBlocks.PLATFORM_DOOR_ENTITY.getType(); }
    @Override public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean powered = context.getLevel().hasNeighborSignal(context.getClickedPos());
        return super.getStateForPlacement(context).setValue(OPEN, powered).setValue(POWERED, powered);
    }
    @Override public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) { return TrainPanelShapes.getDoubleDoorCloseShape(state.getValue(FACING).getOpposite()); }
    @Override public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) { return TrainPanelShapes.getDoubleDoorShape(state.getValue(FACING).getOpposite(), state.getValue(OPEN)); }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos from, boolean moving) {
        if (level.isClientSide) return;
        boolean powered = level.hasNeighborSignal(pos) || level.hasNeighborSignal(pos.above());
        if (powered == state.getValue(POWERED)) return;
        BlockState next = state.setValue(POWERED, powered).setValue(OPEN, powered);
        level.setBlock(pos, next, Block.UPDATE_ALL);
        generateCompanyBlock(level, next, pos, false);
        if (state.getValue(OPEN) != powered) TrainDoorBlock.playDoorSound(level, pos, LegacyMaterial.METAL, powered);
    }
}

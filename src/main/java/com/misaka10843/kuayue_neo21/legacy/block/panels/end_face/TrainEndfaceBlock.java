package com.misaka10843.kuayue_neo21.legacy.block.panels.end_face;

import com.misaka10843.kuayue_neo21.content.VehicleBlockItem;
import com.misaka10843.kuayue_neo21.content.VehiclePartGeometry;

import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainPanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.EndFaceShapes;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.TrainPanelProperties;

import com.misaka10843.kuayue_neo21.legacy.initial.AllBlocks;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.CompanyTrainPanel;
import com.misaka10843.kuayue_neo21.legacy.block.panels.window.TrainSmallWindowBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class TrainEndfaceBlock extends TrainPanelBlock {
    public final TrainPanelProperties.DoorType DOOR_TYPE;

    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public TrainEndfaceBlock(Properties pProperties, TrainPanelProperties.DoorType doorType) {
        super(pProperties, new Vec2(0, 0), new Vec2(1, 1));
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.EAST).setValue(OPEN, false));
        this.DOOR_TYPE = doorType;
    }

    public TrainEndfaceBlock(Properties properties, Vec2 beginPos, Vec2 endPos, TrainPanelProperties.DoorType doorType) {
        super(properties, beginPos, endPos);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.EAST).setValue(OPEN, false));
        this.DOOR_TYPE = doorType;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder.add(OPEN));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        VoxelShape shape = EndFaceShapes.getEndFaceShape(pState.getValue(FACING).getOpposite(), DOOR_TYPE, pState.getValue(OPEN)).move(0, 1, 0);
        
        return shape;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return getShape(pState, pLevel, pPos, pContext);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return super.getStateForPlacement(pContext).setValue(OPEN, false);
    }

    public void generateCompanyBlock(Level level, BlockState state, BlockPos pos, boolean isMoving) {
        Direction direction = state.getValue(FACING);
        boolean open = state.hasProperty(BlockStateProperties.OPEN) ?
                state.getValue(BlockStateProperties.OPEN) : false;
        boolean leftHinge = !state.hasProperty(BlockStateProperties.DOOR_HINGE) || state.getValue(BlockStateProperties.DOOR_HINGE) == DoorHingeSide.LEFT;
        BlockUseFunction function = (l, p, parentState, myPos, myState, player, hand, hit) -> {
            if (myPos.equals(p)) return InteractionResult.SUCCESS;
            BlockState state1 = generateCompanyState(direction, leftHinge ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT, open);
            level.setBlock(myPos, state1, 10);
            CompanyTrainPanel.setParentBlock(myPos, level, state1, pos);
            return InteractionResult.SUCCESS;
        };
        walkAllValidPos(level, pos, state, null, null, null, function);
    }

    public BlockState generateCompanyState(Direction direction, DoorHingeSide hingeSide, boolean open) {
        return AllBlocks.COMPANY_TRAIN_PANEL.instance().defaultBlockState()
                .setValue(CompanyTrainPanel.FACING, direction.getOpposite())
                .setValue(BlockStateProperties.DOOR_HINGE, hingeSide)
                .setValue(OPEN, open);
    }

    @Override
    public void walkAllValidPos(Level level, BlockPos root, BlockState state, Player player, InteractionHand hand, BlockHitResult hit, BlockUseFunction function) {
        VoxelShape closed = getShape(state.setValue(OPEN, false), level, root, CollisionContext.empty());
        for (BlockPos part : VehiclePartGeometry.footprint(root, state.getValue(FACING), closed)) {
            function.apply(level, root, state, part, level.getBlockState(part), player, hand, hit);
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (DOOR_TYPE == TrainPanelProperties.DoorType.NO_DOOR || player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof net.minecraft.world.item.BlockItem) {
            return InteractionResult.PASS;
        }
        return TrainSmallWindowBlock.windowUse(state, level, pos, player, InteractionHand.MAIN_HAND, hitResult);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (DOOR_TYPE == TrainPanelProperties.DoorType.NO_DOOR || stack.getItem() instanceof net.minecraft.world.item.BlockItem) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        TrainSmallWindowBlock.windowUse(state, level, pos, player, hand, hitResult);
        return level.isClientSide ? ItemInteractionResult.SUCCESS : ItemInteractionResult.CONSUME;
    }
}

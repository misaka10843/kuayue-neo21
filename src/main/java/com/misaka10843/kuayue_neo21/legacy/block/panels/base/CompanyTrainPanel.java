package com.misaka10843.kuayue_neo21.legacy.block.panels.base;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.misaka10843.kuayue_neo21.content.VehiclePartGeometry;
import com.misaka10843.kuayue_neo21.legacy.block.panels.end_face.TrainEndfaceBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainPanelBlock;
import com.misaka10843.kuayue_neo21.legacy.utils.DirectionUtil;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.TrainPanelProperties;

public class CompanyTrainPanel extends Block implements EntityBlock, IWrenchable {

    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<DoorHingeSide> HINGE = BlockStateProperties.DOOR_HINGE;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final EnumProperty<TrainPanelProperties.ShapeType> SHAPE_TYPE =
            EnumProperty.create("shape_type", TrainPanelProperties.ShapeType.class);

    public CompanyTrainPanel(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FACING, Direction.EAST)
                        .setValue(HINGE, DoorHingeSide.LEFT)
                        .setValue(OPEN, false)
                        .setValue(SHAPE_TYPE, TrainPanelProperties.ShapeType.NORMAL)
        );
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        BlockPos root = getParentPos(pLevel, pPos);
        if (root != null && pLevel.getBlockState(root).getBlock() instanceof TrainEndfaceBlock end) {
            return VehiclePartGeometry.localShape(end.getShape(pLevel.getBlockState(root), pLevel, root, pContext), root, pPos);
        }
        TrainPanelProperties.ShapeType shapeType = pState.getValue(SHAPE_TYPE);
        switch (shapeType) {
            case BIG_TRUSS:
                return TrainPanelShapes.getOverheadBigTrussShape(pState.getValue(FACING));
            case SMALL_TRUSS:
                return TrainPanelShapes.getOverheadSmallTrussShape(pState.getValue(FACING));
            case PILLAR_TRUSS:
                return TrainPanelShapes.getOverheadPillarTrussShape(pState.getValue(FACING));
            default:
                return TrainPanelShapes.getShape(pState.getValue(FACING).getOpposite());
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        BlockPos root = getParentPos(pLevel, pPos);
        if (root != null && pLevel.getBlockState(root).getBlock() instanceof TrainEndfaceBlock end) {
            return VehiclePartGeometry.localShape(end.getCollisionShape(pLevel.getBlockState(root), pLevel, root, pContext), root, pPos);
        }
        return getShape(pState, pLevel, pPos, pContext);
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        super.neighborChanged(pState, pLevel, pPos, pBlock, pFromPos, pIsMoving);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pIsMoving || pState.is(pNewState.getBlock())) {
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
            return;
        }
        BlockPos pos = getParentPos(pLevel, pPos);
        if (pos == null) {
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
            return;
        }
        BlockState state = pLevel.getBlockState(pos);
        Block block = state.getBlock();
        if (block instanceof TrainPanelBlock trainPanelBlock && !pState.getBlock().equals(pNewState.getBlock()))
            trainPanelBlock.specialRemove(state, pLevel, pos, pNewState, pIsMoving);
        else
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        pLevel.removeBlockEntity(pPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, HINGE, OPEN, SHAPE_TYPE);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return super.getStateForPlacement(pContext)
                .setValue(FACING, pContext.getHorizontalDirection().getOpposite())
                .setValue(HINGE, DoorHingeSide.LEFT)
                .setValue(OPEN, false)
                .setValue(SHAPE_TYPE, TrainPanelProperties.ShapeType.NORMAL);
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (pIsMoving) {
            BlockEntity be = pLevel.getBlockEntity(pPos);
            if (be instanceof CompanyTrainBlockEntity entity) {
                entity.updateDirection(pState.getValue(FACING));
            }
        }
    }

    @Override
    public BlockState rotate(BlockState state, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @Override
    public InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHit) {
        InteractionHand pHand = InteractionHand.MAIN_HAND;
        BlockPos pos = getParentPos(pLevel, pPos);
        if (pos == null) return InteractionResult.PASS;
        BlockState state = pLevel.getBlockState(pos);
        if (state.getBlock() instanceof CompanyTrainPanel) return InteractionResult.PASS;
        return state.useWithoutItem(pLevel, pPlayer, new BlockHitResult(pHit.getLocation(), pHit.getDirection(), pos, pHit.isInside()));
    }

    public static BlockPos getParentPos(BlockGetter level, BlockPos myPos) {
        BlockEntity entity = level.getBlockEntity(myPos);
        if (!(entity instanceof CompanyTrainBlockEntity companyTrainBlockEntity)) return null;
        return companyTrainBlockEntity.getParentPos().offset(myPos);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlockPos parentPos = getParentPos(level, pos);
        if (parentPos == null) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        BlockState parentState = level.getBlockState(parentPos);
        if (parentState.getBlock() instanceof CompanyTrainPanel) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        return parentState.useItemOn(stack, level, player, hand,
                new BlockHitResult(hit.getLocation(), hit.getDirection(), parentPos, hit.isInside()));
    }

    public static void setParentBlock(BlockPos myPos, BlockGetter level, BlockState state, BlockPos pos) {
        BlockEntity entity = level.getBlockEntity(myPos);
        if (!(entity instanceof CompanyTrainBlockEntity companyTrainBlockEntity)) return;
        companyTrainBlockEntity.setParentPos(pos.subtract(myPos), state.getValue(FACING));
    }

    public static BlockState getParentBlock(BlockGetter level, BlockPos myPos) {
        BlockPos pos = getParentPos(level, myPos);
        if (pos == null) return Blocks.AIR.defaultBlockState();
        return level.getBlockState(pos);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CompanyTrainBlockEntity(pPos, pState);
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        Level level = context.getLevel();
        BlockEntity entity = level.getBlockEntity(context.getClickedPos());
        if (!(entity instanceof CompanyTrainBlockEntity)) return InteractionResult.PASS;
        BlockPos parentPos = context.getClickedPos().offset(((CompanyTrainBlockEntity) entity).getParentPos());
        BlockState state1 = level.getBlockState(parentPos);
        if (!(state1.getBlock() instanceof TrainPanelBlock)) return InteractionResult.PASS;
        Vec3 hitLoc = context.getClickLocation().add(DirectionUtil.toVec3(((CompanyTrainBlockEntity) entity).getParentPos()));
        BlockHitResult result = new BlockHitResult(hitLoc, context.getClickedFace(), parentPos, context.isInside());
        UseOnContext context1 = new UseOnContext(context.getPlayer(), context.getHand(), result);
        return ((IWrenchable)state1.getBlock()).onWrenched(state1, context1);
    }

    @Override
    public InteractionResult onSneakWrenched(BlockState state, UseOnContext context) {
        Level level = context.getLevel();
        BlockEntity entity = level.getBlockEntity(context.getClickedPos());
        if (!(entity instanceof CompanyTrainBlockEntity)) return InteractionResult.PASS;
        BlockPos parentPos = context.getClickedPos().offset(((CompanyTrainBlockEntity) entity).getParentPos());
        BlockState state1 = level.getBlockState(parentPos);
        if (!(state1.getBlock() instanceof TrainPanelBlock)) return InteractionResult.PASS;
        Vec3 hitLoc = context.getClickLocation().add(DirectionUtil.toVec3(((CompanyTrainBlockEntity) entity).getParentPos()));
        BlockHitResult result = new BlockHitResult(hitLoc, context.getClickedFace(), parentPos, context.isInside());
        UseOnContext context1 = new UseOnContext(context.getPlayer(), context.getHand(), result);
        return ((IWrenchable)state1.getBlock()).onSneakWrenched(state1, context1);
    }
}

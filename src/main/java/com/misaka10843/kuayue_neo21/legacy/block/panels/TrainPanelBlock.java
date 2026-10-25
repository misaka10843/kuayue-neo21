package com.misaka10843.kuayue_neo21.legacy.block.panels;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.misaka10843.kuayue_neo21.content.VehicleExtensionPlacement;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import org.jetbrains.annotations.Nullable;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.CompanyTrainPanel;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.TrainPanelShapes;
import com.misaka10843.kuayue_neo21.legacy.initial.AllBlocks;
import com.misaka10843.kuayue_neo21.legacy.utils.DirectionUtil;


public class TrainPanelBlock extends Block implements IWrenchable, EntityBlock {
    public final Vec2 beginPos, endPos;
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public TrainPanelBlock(Properties pProperties, Vec2 beginPos, Vec2 endPos) {
        super(pProperties);
        this.registerDefaultState(
                this.getStateDefinition().any()
                        .setValue(FACING, Direction.EAST)
        );
        this.beginPos = beginPos;
        this.endPos = endPos;
    }

    public TrainPanelBlock(Properties properties) {
        this(properties, Vec2.ZERO, Vec2.ONE);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return TrainPanelShapes.getShape(pState.getValue(FACING).getOpposite());
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return TrainPanelShapes.getShape(pState.getValue(FACING).getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(FACING);
    }

    @Nullable


    @Override
    public BlockState rotate(BlockState state, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return super.getStateForPlacement(pContext)
                .setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }


    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
        if (pLevel instanceof ServerLevel server && !pIsMoving && !pState.is(pOldState.getBlock())) server.scheduleTick(pPos, this, 1);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        generateCompanyBlock(level, state, pos, false);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (placer != null && level instanceof ServerLevel) generateCompanyBlock(level, state, pos, false);
    }

    public boolean hasHinge() {
        if (!defaultBlockState().hasProperty(BlockStateProperties.DOOR_HINGE)) return false;
        int width = (int) endPos.x - (int) beginPos.x;
        if (width % 2 == 0) return true;
        return !(width % 2 == 1 && - beginPos.x == (width / 2));
    }

    public void generateCompanyBlock(Level level, BlockState state, BlockPos pos, boolean isMoving) {
        Direction direction = state.getValue(FACING);
        boolean open = state.hasProperty(BlockStateProperties.OPEN) ?
                state.getValue(BlockStateProperties.OPEN) : false;
        boolean leftHinge = !state.hasProperty(BlockStateProperties.DOOR_HINGE) || state.getValue(BlockStateProperties.DOOR_HINGE) == DoorHingeSide.LEFT;
        BlockUseFunction function = (l, p, parentState, myPos, myState, player, hand, hit) -> {
            if (p.equals(myPos)) return InteractionResult.SUCCESS;
            if (!VehicleExtensionPlacement.canClaim(l, p, myPos)) return InteractionResult.PASS;
            BlockState state1 = generateCompanyState(direction, leftHinge ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT, open);
            level.setBlock(myPos, state1, 10);
            CompanyTrainPanel.setParentBlock(myPos, level, state1, pos);
            return InteractionResult.SUCCESS;
        };
        walkAllValidPos(level, pos, state, null, null, null, function);
    }

    public BlockState generateCompanyState(Direction direction, DoorHingeSide hingeSide, boolean open) {
        return AllBlocks.COMPANY_TRAIN_PANEL.instance().defaultBlockState()
                .setValue(CompanyTrainPanel.FACING, direction)
                .setValue(BlockStateProperties.DOOR_HINGE, hingeSide);
    }


    public void removeCompanyBlock(Level level, BlockState state, BlockPos pos, boolean isMoving) {
        BlockUseFunction function = (l, p, parentState, myPos, myState, player, hand, hit) -> {
            if (p.equals(myPos)) return InteractionResult.SUCCESS;
            if (!(myState.getBlock() instanceof CompanyTrainPanel)) return InteractionResult.PASS;
            if (!p.equals(CompanyTrainPanel.getParentPos(l, myPos))) return InteractionResult.PASS;
            l.removeBlockEntity(myPos);
            l.removeBlock(myPos, isMoving);
            return InteractionResult.SUCCESS;
        };
        walkAllValidPos(level, pos, state, null, null, null, function);
    }

    public void walkAllValidPos(Level level, BlockPos blockPos, BlockState parentState,
                                Player player, InteractionHand hand, BlockHitResult hit, BlockUseFunction function) {
        for (BlockPos part : getOccupiedPositions(level, blockPos, parentState)) {
            function.apply(level, blockPos, parentState, part, level.getBlockState(part), player, hand, hit);
        }
    }

    public List<BlockPos> getOccupiedPositions(BlockGetter level, BlockPos blockPos, BlockState parentState) {
        List<BlockPos> positions = new ArrayList<>();
        Direction direction = parentState.getValue(FACING);
        BlockPos firstPos = blockPos;
        boolean leftHinge = !parentState.hasProperty(BlockStateProperties.DOOR_HINGE) ||
                parentState.getValue(BlockStateProperties.DOOR_HINGE) == DoorHingeSide.LEFT;
        firstPos = DirectionUtil.left(firstPos, direction, (int) (leftHinge ? - beginPos.x : beginPos.x));
        firstPos = firstPos.offset(0, (int) beginPos.y, 0);
        int length = (int) (endPos.x - beginPos.x),
                height = (int) (endPos.y - beginPos.y);

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < height; j++) {
                positions.add(firstPos);
                firstPos = firstPos.above();
            }
            firstPos = firstPos.offset(0, - height, 0);
            firstPos = DirectionUtil.right(firstPos, direction, leftHinge ? 1 : -1);
        }
        return positions;
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        return InteractionResult.PASS;
    }

    public interface BlockUseFunction {
        InteractionResult apply(Level level, BlockPos parentPos, BlockState parentState, BlockPos myPos,
                               BlockState myState, Player player, InteractionHand hand, BlockHitResult hit);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) { return null; }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState replacement, boolean moving) {
        if (state.is(replacement.getBlock())) return;
        if (!moving && !level.isClientSide) removeCompanyBlock(level, state, pos, false);
        super.onRemove(state, level, pos, replacement, moving);
    }
}

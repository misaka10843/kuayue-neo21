package com.misaka10843.kuayue_neo21.legacy.block.panels.door;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.GameEvent;
import com.misaka10843.kuayue_neo21.content.LegacyMaterial;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainPanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.CompanyTrainDoor;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.TrainPanelShapes;
import com.misaka10843.kuayue_neo21.legacy.block.panels.window.TrainOpenableWindowBlock;
import com.misaka10843.kuayue_neo21.legacy.initial.AllBlocks;
import com.misaka10843.kuayue_neo21.legacy.utils.DirectionUtil;

public class TrainDoorBlock extends TrainPanelBlock {

    public static final EnumProperty<DoorHingeSide> HINGE = BlockStateProperties.DOOR_HINGE;

    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    public TrainDoorBlock(Properties pProperties) {
        super(pProperties, new Vec2(0, 0), new Vec2(1, 2));
        this.registerDefaultState(
                this.getStateDefinition().any()
                    .setValue(FACING, Direction.EAST)
                    .setValue(HINGE, DoorHingeSide.LEFT)
                    .setValue(OPEN, false)
        );
    }

    public TrainDoorBlock(Properties pProperties, Vec2 beginPos, Vec2 endPos) {
        super(pProperties, beginPos, endPos);
        this.registerDefaultState(
                this.getStateDefinition().any()
                        .setValue(FACING, Direction.EAST)
                        .setValue(HINGE, DoorHingeSide.LEFT)
                        .setValue(OPEN, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder.add(HINGE, OPEN));
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return super.getStateForPlacement(pContext)
                .setValue(FACING, pContext.getHorizontalDirection().getOpposite())
                .setValue(HINGE, TrainOpenableWindowBlock.getHinge(pContext))
                .setValue(OPEN, false);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return TrainPanelShapes.getDoorShape(pState.getValue(FACING).getOpposite(), pState.getValue(HINGE), pState.getValue(OPEN));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return getShape(pState, pLevel, pPos, pContext);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHit) {
        InteractionHand pHand = InteractionHand.MAIN_HAND;
        return doorUse(pState, LegacyMaterial.METAL, pLevel, pPos, pPlayer, pHand, pHit);
    }

    public static InteractionResult doorUse(BlockState state, LegacyMaterial material, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) return InteractionResult.SUCCESS;
        state = state.cycle(OPEN);
        BlockPos abovePos = pos.above();
        BlockState aboveState = level.getBlockState(abovePos);
        if (aboveState.getBlock() instanceof CompanyTrainDoor && pos.equals(CompanyTrainDoor.getParentPos(level, abovePos))) {
            aboveState = aboveState.setValue(OPEN, state.getValue(OPEN));
            level.setBlock(abovePos, aboveState, 10);
            CompanyTrainDoor.setParentBlock(abovePos, level, aboveState, pos);
        }
        level.setBlock(pos, state, 10);
        playDoorSound(level, pos, material, state.getValue(OPEN));
        level.gameEvent(player, isOpen(state) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    public static int getCloseSound(LegacyMaterial material) {
        return material == LegacyMaterial.METAL ? 1011 : 1012;
    }

    public static int getOpenSound(LegacyMaterial material) {
        return material == LegacyMaterial.METAL ? 1005 : 1006;
    }

    public static void playDoorSound(Level level, BlockPos pos, LegacyMaterial material, boolean open) {
        var sound = material == LegacyMaterial.METAL
                ? (open ? net.minecraft.sounds.SoundEvents.IRON_DOOR_OPEN : net.minecraft.sounds.SoundEvents.IRON_DOOR_CLOSE)
                : (open ? net.minecraft.sounds.SoundEvents.WOODEN_DOOR_OPEN : net.minecraft.sounds.SoundEvents.WOODEN_DOOR_CLOSE);
        level.playSound(null, pos, sound, net.minecraft.sounds.SoundSource.BLOCKS, 1f, 1f);
    }

    public static boolean isOpen(BlockState state) {
        return state.getValue(OPEN);
    }

    @Override
    public BlockState generateCompanyState(Direction direction, DoorHingeSide hingeSide, boolean open) {
        return AllBlocks.COMPANY_TRAIN_DOOR.instance().defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, direction)
                .setValue(BlockStateProperties.DOOR_HINGE, hingeSide)
                .setValue(BlockStateProperties.OPEN, open);
    }

    protected static DoorHingeSide getHinge(BlockPlaceContext pContext, Block block) {
        BlockGetter blockGetter = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        Direction direction = pContext.getHorizontalDirection().getOpposite();
        BlockState leftState = blockGetter.getBlockState(DirectionUtil.left(pos, direction, 1));
        if (leftState.is(block) && leftState.getValue(FACING) == direction && leftState.getValue(HINGE) == DoorHingeSide.LEFT)
            return DoorHingeSide.RIGHT;
        return DoorHingeSide.LEFT;
    }

    public static class Sliding extends TrainDoorBlock {

        public Sliding(Properties pProperties) {
            super(pProperties);
        }

        @Override
        public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
            return TrainPanelShapes.getSlidingDoorShape(pState.getValue(FACING), pState.getValue(HINGE), pState.getValue(OPEN));
        }
    }
}

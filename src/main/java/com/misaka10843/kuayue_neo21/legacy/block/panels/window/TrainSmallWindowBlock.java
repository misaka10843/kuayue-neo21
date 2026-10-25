package com.misaka10843.kuayue_neo21.legacy.block.panels.window;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import com.misaka10843.kuayue_neo21.content.LegacyMaterial;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.Nullable;
import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainPanelBlock;
import net.minecraft.world.item.BlockItem;

import static com.misaka10843.kuayue_neo21.legacy.block.panels.door.TrainDoorBlock.*;

public class TrainSmallWindowBlock extends TrainPanelBlock {
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public TrainSmallWindowBlock(Properties pProperties, Vec2 beginPos, Vec2 endPos) {
        super(pProperties, beginPos, endPos);
        registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.EAST).setValue(OPEN, false));
    }

    public TrainSmallWindowBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.EAST).setValue(OPEN, false));
    }

    public TrainSmallWindowBlock(Properties properties, int height) {
        super(properties, new Vec2(0, 0), new Vec2(1, height));
        registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.EAST).setValue(OPEN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder.add(OPEN));
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return super.getStateForPlacement(pContext).setValue(OPEN, false);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHit) {
        InteractionHand pHand = InteractionHand.MAIN_HAND;
        return windowUse(pState, LegacyMaterial.METAL, pLevel, pPos, pPlayer, pHand, pHit);
    }

    public static InteractionResult windowUse(BlockState state, LegacyMaterial material, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (player.getItemInHand(hand).getItem() instanceof BlockItem) return InteractionResult.PASS;
        if (level.isClientSide) return InteractionResult.SUCCESS;
        boolean open = !state.getValue(OPEN);
        com.misaka10843.kuayue_neo21.content.VehicleDoorPairs.setOpen(level, pos, state, open);
        playDoorSound(level, pos, material, open);
        level.gameEvent(player, open ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (stack.getItem() instanceof BlockItem) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        windowUse(state, level, pos, player, hand, hit);
        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    public static InteractionResult windowUse(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        return windowUse(state, LegacyMaterial.METAL, level, pos, player, hand, hit);
    }
}

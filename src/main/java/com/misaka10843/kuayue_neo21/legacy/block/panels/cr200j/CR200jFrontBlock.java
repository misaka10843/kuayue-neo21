package com.misaka10843.kuayue_neo21.legacy.block.panels.cr200j;

import com.misaka10843.kuayue_neo21.legacy.block.panels.FullShapeDirectionalBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;
import com.misaka10843.kuayue_neo21.legacy.block.panels.block_entity.CR200jFrontBlockEntity;
import com.misaka10843.kuayue_neo21.legacy.initial.panel.CR200JPanel;

import java.util.List;

public class CR200jFrontBlock extends FullShapeDirectionalBlock implements IBE<CR200jFrontBlockEntity> {
    public CR200jFrontBlock(Properties properties) {
        super(properties);
    }

    @Override
    public net.minecraft.world.level.block.entity.BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return getBlockEntityType().create(pos, state);
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, net.minecraft.world.level.storage.loot.LootParams.Builder pBuilder) {
        return List.of(this.asItem().getDefaultInstance());
    }

    @Override
    public Class<CR200jFrontBlockEntity> getBlockEntityClass() {
        return CR200jFrontBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends CR200jFrontBlockEntity> getBlockEntityType() {
        return (BlockEntityType<? extends CR200jFrontBlockEntity>)
                com.misaka10843.kuayue_neo21.legacy.initial.AllBlocks.CR200J_FRONT_ENTITY.getType();
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHit) {
        InteractionHand pHand = InteractionHand.MAIN_HAND;
        if(pLevel.getBlockEntity(pPos) instanceof CR200jFrontBlockEntity entity)
            entity.onNormalUse(pLevel, pPlayer, pHand, pPos, pState, new CompoundTag());
        return super.useWithoutItem(pState, pLevel, pPos, pPlayer, pHit);
    }
}

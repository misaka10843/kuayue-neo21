package com.misaka10843.kuayue_neo21.legacy.block.panels.block_entity;

import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import com.misaka10843.kuayue_neo21.legacy.initial.panel.CR200JPanel;

import javax.annotation.Nullable;
import java.util.List;

public class CR200jFrontBlockEntity extends SmartBlockEntity implements ContraptionAnimatableBlockEntity {
    public float counterFairing = 0f, counterWindShield = 0f;
    private boolean isFairingOpen = false, isWindShieldActive = true;
    public boolean negative_direction = false;

    public CR200jFrontBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(com.misaka10843.kuayue_neo21.legacy.initial.AllBlocks.CR200J_FRONT_ENTITY.getType(), pPos, pBlockState);
    }

    @Override
    protected void write(CompoundTag tag, net.minecraft.core.HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        tag.putBoolean("fairing", isFairingOpen);
        tag.putBoolean("wind_shield", isWindShieldActive);
    }

    @Override
    protected void read(CompoundTag tag, net.minecraft.core.HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        this.isFairingOpen = tag.getBoolean("fairing");
        this.isWindShieldActive = tag.getBoolean("wind_shield");
    }

    public boolean isFairingOpen() {
        return isFairingOpen;
    }

    public boolean isWindShieldActive() {
        return isWindShieldActive;
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {}

    @Override
    public void onContraptionUse(Level level, Player player, InteractionHand hand, BlockPos localPos, AbstractContraptionEntity contraptionEntity, BlockState state, CompoundTag externalData) {
        onUse(player, hand, contraptionEntity, localPos);
    }

    @Override
    public void onNormalUse(Level level, Player player, InteractionHand hand, BlockPos worldPos, BlockState state, CompoundTag externalData) {
        onUse(player, hand, null, null);
    }

    public void switchWindShield() {
        this.isWindShieldActive = !isWindShieldActive;
    }

    public void switchFairing() {
        this.isFairingOpen = !isFairingOpen;
    }

    public void onUse(Player player, InteractionHand hand,@Nullable AbstractContraptionEntity entity, @Nullable BlockPos localPos) {
        ItemStack stack = player.getItemInHand(hand);
        
        
        

    }

    @Override
    public AABB getRenderBoundingBox() {
        BlockPos pos = getBlockPos();
        return AABB.ofSize(new Vec3(pos.getX(), pos.getY(), pos.getZ()), 10, 10, 10);
    }
}

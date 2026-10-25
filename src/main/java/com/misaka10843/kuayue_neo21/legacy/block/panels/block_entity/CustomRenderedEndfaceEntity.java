package com.misaka10843.kuayue_neo21.legacy.block.panels.block_entity;
import com.misaka10843.kuayue_neo21.legacy.initial.AllBlocks;

import com.misaka10843.kuayue_neo21.legacy.block.panels.end_face.CustomRenderedEndfaceBlock;

import com.misaka10843.kuayue_neo21.legacy.block.panels.base.TrainPanelProperties;
import com.misaka10843.kuayue_neo21.legacy.block.panels.block_entity.IContraptionMovementBlockEntity;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.data.Couple;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class CustomRenderedEndfaceEntity extends SmartBlockEntity implements IContraptionMovementBlockEntity {

    public boolean open = false;
    public TrainPanelProperties.DoorType type = TrainPanelProperties.DoorType.ROTATE;
    public float counter = 0;

    public CustomRenderedEndfaceEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public CustomRenderedEndfaceEntity(BlockPos pos, BlockState state) {
        this(AllBlocks.CUSTOM_RENDERED_ENDFACE_ENTITY.getType(), pos, state);
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        tag.putBoolean("open", open);
        tag.putString("type", type.getSerializedName());
    }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        open = tag.getBoolean("open");
        type = TrainPanelProperties.DoorType.fromString(tag.getString("type"));
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isRotateDoor() {
        return type == TrainPanelProperties.DoorType.ROTATE ||
                type == TrainPanelProperties.DoorType.ROTATE_SINGLE_SIDED;
    }

    public boolean isSingleSided() {
        return type == TrainPanelProperties.DoorType.ROTATE_SINGLE_SIDED;
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {}

    @Override
    public void tick() {
        if(level == null) return;
        BlockState state = level.getBlockState(this.getBlockPos());
        if (!(state.getBlock() instanceof CustomRenderedEndfaceBlock block)) return;
        this.open = level.getBlockState(this.getBlockPos()).getValue(DoorBlock.OPEN);
        this.type = block.DOOR_TYPE;
    }

    public Couple<PartialModel> getModels() {
        if (this.level == null) return null;
        BlockState state = this.level.getBlockState(this.getBlockPos());
        if (!(state.getBlock() instanceof CustomRenderedEndfaceBlock block)) return null;
        return block.models;
    }

    public PartialModel getFrameModel() {
        if (this.level == null) return null;
        BlockState state = this.level.getBlockState(this.getBlockPos());
        if (!(state.getBlock() instanceof CustomRenderedEndfaceBlock block)) return null;
        return block.frameModel;
    }

    @Override
    public void update(StructureTemplate.StructureBlockInfo info, Player player, BlockPos pos, AbstractContraptionEntity entity) {
        this.open = !open;
    }

    @Override
    public AABB getRenderBoundingBox() {
        return AABB.ofSize(Vec3.atCenterOf(this.getBlockPos()), 5, 5, 5);
    }
}

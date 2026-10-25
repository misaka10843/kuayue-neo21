package com.misaka10843.kuayue_neo21.legacy.block.panels.end_face;
import com.misaka10843.kuayue_neo21.legacy.initial.AllBlocks;

import com.misaka10843.kuayue_neo21.legacy.block.panels.block_entity.CustomRenderedEndfaceEntity;

import com.misaka10843.kuayue_neo21.KuaYueneo21;
import com.misaka10843.kuayue_neo21.legacy.block.panels.end_face.TrainEndfaceBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.TrainPanelProperties;
import com.simibubi.create.foundation.block.IBE;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.data.Couple;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CustomRenderedEndfaceBlock extends TrainEndfaceBlock implements IBE<CustomRenderedEndfaceEntity> {

    public final Couple<PartialModel> models;
    public final PartialModel frameModel;
    public CustomRenderedEndfaceBlock(BlockBehaviour.Properties pProperties,
                                      TrainPanelProperties.DoorType doorType,
                                      PartialModel leftModel, PartialModel rightModel, PartialModel frameModel) {
        super(pProperties, doorType);
        this.models = Couple.create(leftModel, rightModel);
        this.frameModel = frameModel;
    }

    public CustomRenderedEndfaceBlock(BlockBehaviour.Properties prop,
                                      TrainPanelProperties.DoorType doorType,
                                      ResourceLocation left, ResourceLocation right, ResourceLocation frame) {
        super(prop, doorType);
        this.models = Couple.create(PartialModel.of(left), PartialModel.of(right));
        this.frameModel = PartialModel.of(frame);
    }

    public CustomRenderedEndfaceBlock(BlockBehaviour.Properties properties, TrainPanelProperties.DoorType doorType,
                                      String leftModel, String rightModel, String frameModel) {
        super(properties, doorType);
        this.models = Couple.create(
                leftModel == null ? null : PartialModel.of(ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "block/" + leftModel)),
                rightModel == null ? null : PartialModel.of(ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "block/" + rightModel)));
        this.frameModel = PartialModel.of(ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "block/" + frameModel));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pPos, BlockState pState) {
        return getBlockEntityType().create(pPos, pState);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public Class<CustomRenderedEndfaceEntity> getBlockEntityClass() {
        return CustomRenderedEndfaceEntity.class;
    }

    @Override
    public BlockEntityType<? extends CustomRenderedEndfaceEntity> getBlockEntityType() {
        return AllBlocks.CUSTOM_RENDERED_ENDFACE_ENTITY.getType();
    }
}

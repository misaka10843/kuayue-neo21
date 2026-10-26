package com.misaka10843.kuayue_neo21.legacy.block.panels.door;

import com.misaka10843.kuayue_neo21.legacy.block.panels.block_entity.CustomRenderedDoorEntity;

import com.misaka10843.kuayue_neo21.KuaYueneo21;
import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainPanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.TrainPanelShapes;
import com.misaka10843.kuayue_neo21.legacy.initial.AllBlocks;
import com.misaka10843.kuayue_neo21.legacy.block.panels.door.TrainDoorBlock;
import com.simibubi.create.foundation.block.IBE;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.data.Couple;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CustomRenderedDoorBlock extends TrainDoorBlock implements IBE<CustomRenderedDoorEntity> {
    boolean isSlideDoor;
    final Couple<PartialModel> leftDoorModels;
    final Couple<PartialModel> rightDoorModels;
    final Vec3 offset;
    final Vec3 openoffset;
    final RenderShape renderShape;

    public CustomRenderedDoorBlock(BlockBehaviour.Properties pProperties,
                                   Couple<ResourceLocation> leftDoorModels,
                                   Couple<ResourceLocation> rightDoorModels,
                                   RenderShape renderShape, boolean isSlideDoor) {
        super(pProperties);
        this.leftDoorModels = Couple.create(block(leftDoorModels.get(true).getPath()), block(leftDoorModels.get(false).getPath()));
        this.rightDoorModels = Couple.create(block(rightDoorModels.get(true).getPath()), block(rightDoorModels.get(false).getPath()));
        this.renderShape = renderShape;
        this.offset = Vec3.ZERO;
        this.openoffset = Vec3.ZERO;
        this.isSlideDoor = isSlideDoor;
    }

    public CustomRenderedDoorBlock(BlockBehaviour.Properties pProperties,
                                   CustomRenderedDoorBlock modelFrom,
                                   RenderShape renderShape, boolean isSlideDoor) {
        super(pProperties);
        this.leftDoorModels = modelFrom.leftDoorModels;
        this.rightDoorModels = modelFrom.rightDoorModels;
        this.renderShape = renderShape;
        this.offset = modelFrom.offset;
        this.openoffset = modelFrom.openoffset;
        this.isSlideDoor = isSlideDoor;
    }

    public CustomRenderedDoorBlock(BlockBehaviour.Properties properties,
                                   Couple<ResourceLocation> leftDoorModels,
                                   Couple<ResourceLocation> rightDoorModels,
                                   Vec3 offset,
                                   Vec3 openoffset,
                                   RenderShape renderShape, boolean isSlideDoor) {
        super(properties);
        this.leftDoorModels = Couple.create(block(leftDoorModels.get(true).getPath()), block(leftDoorModels.get(false).getPath()));
        this.rightDoorModels = Couple.create(block(rightDoorModels.get(true).getPath()), block(rightDoorModels.get(false).getPath()));
        this.renderShape = renderShape;
        this.offset = offset;
        this.openoffset = openoffset;
        this.isSlideDoor = isSlideDoor;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pPos, BlockState pState) {
        return getBlockEntityType().create(pPos, pState);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        var leaf = getCollisionShape(pState, pLevel, pPos, pContext);
        return isSlideDoor ? net.minecraft.world.phys.shapes.Shapes.or(leaf, closedLeaf(pState)) : leaf;
    }

    private VoxelShape closedLeaf(BlockState state) {
        return com.misaka10843.kuayue_neo21.content.SlidingDoorGeometry.closed(this, state);
    }

    @Override
    public void removeCompanyBlock(net.minecraft.world.level.Level level, BlockState state, BlockPos root, boolean moving) {
        super.removeCompanyBlock(level, state, root, moving);
        if (!isSlideDoor) return;
        for (var part : com.misaka10843.kuayue_neo21.content.VehiclePartGeometry.footprint(root, state.getValue(FACING), closedLeaf(state))) {
            if (part.equals(root) || !(level.getBlockState(part).getBlock() instanceof com.misaka10843.kuayue_neo21.legacy.block.panels.base.CompanyTrainPanel)) continue;
            if (root.equals(com.misaka10843.kuayue_neo21.legacy.block.panels.base.CompanyTrainPanel.getParentPos(level, part))) level.removeBlock(part, moving);
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (isSlideDoor) {
            var closed = closedLeaf(state);
            if (!state.getValue(OPEN)) return closed;
            var offset = com.misaka10843.kuayue_neo21.content.VehicleDoorMotion.worldSlide(state.getValue(FACING), state.getValue(HINGE) == DoorHingeSide.LEFT);
            return closed.move(offset.x, offset.y, offset.z);
        }
        var lower = super.getShape(state, level, pos, context);
        return net.minecraft.world.phys.shapes.Shapes.or(lower, lower.move(0, 1, 0));
    }

    public Couple<PartialModel> getLeftDoorModels() {
        return leftDoorModels;
    }

    public Couple<PartialModel> getRightDoorModels() {
        return rightDoorModels;
    }

    public Vec3 getOffset() {
        return offset;
    }

    public Vec3 getOpenOffset() {
        return openoffset;
    }

    private static PartialModel block(String path) {
        return PartialModel.of(ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "block/" + path));
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return true;
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return true;
    }

    @Override
    public Class<CustomRenderedDoorEntity> getBlockEntityClass() {
        return CustomRenderedDoorEntity.class;
    }

    @Override
    public BlockEntityType<CustomRenderedDoorEntity> getBlockEntityType() {
        return AllBlocks.CUSTOM_RENDERED_DOOR_ENTITY.getType();
    }

    @Override
    public BlockState generateCompanyState(Direction direction, DoorHingeSide hingeSide, boolean open) {
        if (!isSlideDoor)
            return super.generateCompanyState(direction, hingeSide, open);
        return AllBlocks.COMPANY_SLIDING_DOOR.instance().defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, direction)
                .setValue(BlockStateProperties.DOOR_HINGE, hingeSide)
                .setValue(BlockStateProperties.OPEN, open);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return renderShape;
    }

    public boolean isSlideDoor() {
        return isSlideDoor;
    }
}

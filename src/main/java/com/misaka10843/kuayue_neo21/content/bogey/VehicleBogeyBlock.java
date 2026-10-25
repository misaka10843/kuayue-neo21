package com.misaka10843.kuayue_neo21.content.bogey;

import com.simibubi.create.content.trains.bogey.AbstractBogeyBlock;
import com.simibubi.create.content.trains.bogey.BogeySizes;
import com.simibubi.create.content.trains.bogey.BogeyStyle;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.TrainPanelShapes;
import com.simibubi.create.content.schematics.requirement.ItemRequirement;
import com.simibubi.create.content.trains.track.ITrackBlock;
import com.simibubi.create.content.trains.track.TrackMaterial;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class VehicleBogeyBlock extends AbstractBogeyBlock<VehicleBogeyEntity> {
    private final Supplier<BogeyStyle> style;
    private final TrackMaterial.TrackType trackType;
    private final boolean singleSided;
    private final boolean halfHeightTop;

    public VehicleBogeyBlock(Properties properties, BogeySizes.BogeySize size, Supplier<BogeyStyle> style,
                             TrackMaterial.TrackType trackType, boolean singleSided, boolean halfHeightTop) {
        super(properties, size);
        this.style = style;
        this.trackType = trackType;
        this.singleSided = singleSided;
        this.halfHeightTop = halfHeightTop;
    }

    @Override public TrackMaterial.TrackType getTrackType(BogeyStyle style) { return trackType; }
    @Override public double getWheelPointSpacing() { return singleSided ? 5 : 2.5; }
    @Override public double getWheelRadius() { return 0.915; }
    @Override protected Vec3 getConnectorAnchorOffset() { return new Vec3(0, 7 / 32f, 1); }
    @Override public boolean allowsSingleBogeyCarriage() { return true; }
    // Create renders assembled bogeys through the carriage, so their BEs must not render again.
    @Override public boolean captureBlockEntityForTrain() { return false; }
    @Override public BogeyStyle getDefaultStyle() { return style.get(); }
    @Override public Class<VehicleBogeyEntity> getBlockEntityClass() { return VehicleBogeyEntity.class; }
    @Override public BlockEntityType<? extends VehicleBogeyEntity> getBlockEntityType() { return VehicleBogeys.ENTITY.getType(); }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return halfHeightTop ? TrainPanelShapes.HALF_HEIGHT_TOP_AABB : super.getShape(state, level, pos, context);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getShape(state, level, pos, context);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var railPos = context.getClickedPos().below();
        var railState = context.getLevel().getBlockState(railPos);
        var placement = getPlacementOnTrack(context.getLevel(), railPos, railState);
        return placement == null ? null : placement.setValue(WATERLOGGED,
                context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
    }

    public BlockState getPlacementOnTrack(BlockGetter level, BlockPos railPos, BlockState railState) {
        if (!(railState.getBlock() instanceof ITrackBlock rail)) return null;
        var axes = rail.getTrackAxes(level, railPos, railState);
        if (axes.size() != 1 || Math.abs(axes.getFirst().y) > 0.000001
                || rail.getMaterial().trackType != trackType) return null;
        var anchor = rail.getBogeyAnchor(level, railPos, railState);
        if (!anchor.hasProperty(AXIS)) return null;
        return defaultBlockState().setValue(AXIS, anchor.getValue(AXIS));
    }

    @Override
    public ItemRequirement getRequiredItems(BlockState state, BlockEntity entity) {
        return new ItemRequirement(ItemRequirement.ItemUseType.CONSUME, new ItemStack(this));
    }
}

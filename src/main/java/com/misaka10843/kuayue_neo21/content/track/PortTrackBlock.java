package com.misaka10843.kuayue_neo21.content.track;

import com.misaka10843.kuayue_neo21.content.PortRegistration;
import com.simibubi.create.content.trains.track.TrackBlock;
import com.simibubi.create.content.trains.track.TrackMaterial;
import com.simibubi.create.content.trains.track.TrackShape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public final class PortTrackBlock extends TrackBlock {
    public PortTrackBlock(Properties properties, TrackMaterial material) { super(properties, material); }

    @Override
    public BlockState getBogeyAnchor(BlockGetter world, BlockPos pos, BlockState state) {
        String id = material.trackType == PortTrackTypes.METER ? "mkz_bogey" : "pk209p_bogey";
        return BuiltInRegistries.BLOCK.get(PortRegistration.resource(id)).defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_AXIS, state.getValue(SHAPE) == TrackShape.XO ? Axis.X : Axis.Z);
    }
}

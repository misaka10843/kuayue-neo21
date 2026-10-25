package com.misaka10843.kuayue_neo21.mixin;

import com.misaka10843.kuayue_neo21.content.track.RailwayTracks;
import com.simibubi.create.content.trains.track.BezierConnection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = BezierConnection.SegmentAngles.class, remap = false)
public abstract class TrackSegmentAnglesMixin {
    @ModifyConstant(method = "<init>", constant = @Constant(doubleValue = 0.9649999737739563), require = 2)
    private double kuayue$useModelRailSpacing(double original, BezierConnection connection) {
        return RailwayTracks.curveRailOffset(connection.getMaterial(), original);
    }
}

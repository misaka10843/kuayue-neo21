package com.misaka10843.kuayue_neo21.data;

import com.misaka10843.kuayue_neo21.content.track.RailwayTracks;
import com.simibubi.create.AllBlockEntityTypes;
import com.simibubi.create.content.trains.track.BezierConnection;
import com.simibubi.create.content.trains.track.TrackBlock;
import com.simibubi.create.content.trains.track.TrackBlockItem;
import com.simibubi.create.content.trains.track.TrackMaterial;
import com.simibubi.create.content.trains.track.TrackShape;
import net.createmod.catnip.data.Couple;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.phys.Vec3;
import java.util.List;
import java.util.Map;

final class TrackIntegrationAudit {
    static Map<String, Integer> run(List<String> failures) {
        int curves = 0;
        int bogeyPlacements = 0;
        for (var entry : RailwayTracks.BLOCKS.entrySet()) {
            var block = entry.getValue().get();
            var material = RailwayTracks.MATERIALS.get(entry.getKey());
            if (!(block.asItem() instanceof TrackBlockItem)) failures.add("Missing native track item: " + entry.getKey());
            if (TrackMaterial.deserialize(material.id.toString()) != material) failures.add("Track material decode: " + entry.getKey());
            var state = block.defaultBlockState().setValue(TrackBlock.HAS_BE, true);
            if (!AllBlockEntityTypes.TRACK.get().isValid(state)) failures.add("Missing native track entity: " + entry.getKey());
            for (var shape : new TrackShape[]{TrackShape.XO, TrackShape.ZO}) {
                var anchor = block.getBogeyAnchor(EmptyBlockGetter.INSTANCE, BlockPos.ZERO, state.setValue(TrackBlock.SHAPE, shape));
                if (!(anchor.getBlock() instanceof com.simibubi.create.content.trains.bogey.AbstractBogeyBlock<?>))
                    failures.add("Missing track bogey anchor: " + entry.getKey());
                for (var registered : com.misaka10843.kuayue_neo21.content.PortRegistration.ENTRIES) {
                    if (!(registered.get() instanceof com.misaka10843.kuayue_neo21.content.bogey.VehicleBogeyBlock bogey)) continue;
                    boolean compatible = bogey.getTrackType(bogey.getDefaultStyle()) == material.trackType;
                    var placement = bogey.getPlacementOnTrack(EmptyBlockGetter.INSTANCE, BlockPos.ZERO, state.setValue(TrackBlock.SHAPE, shape));
                    if ((placement != null) != compatible) failures.add("Bogey/track gauge mismatch: " + registered.id() + " / " + entry.getKey());
                    bogeyPlacements++;
                }
            }
            var connection = new BezierConnection(Couple.create(BlockPos.ZERO, new BlockPos(8, 0, 0)),
                    Couple.create(Vec3.ZERO, new Vec3(8, 0, 0)), Couple.create(new Vec3(1, 0, 0), new Vec3(-1, 0, 0)),
                    Couple.create(new Vec3(0, 1, 0), new Vec3(0, 1, 0)), true, false, material);
            if (new BezierConnection(connection.write(BlockPos.ZERO), BlockPos.ZERO).getMaterial() != material)
                failures.add("Curve material save round trip: " + entry.getKey());
            var segments = connection.getBakedSegments();
            var rails = segments.railTransforms[1];
            var left = rails.getFirst().pose();
            var right = rails.getSecond().pose();
            double width = Math.sqrt(Math.pow(left.m30() - right.m30(), 2) + Math.pow(left.m32() - right.m32(), 2));
            double expected = entry.getKey().equals("meter") ? 1.05 : 1.51;
            if (Math.abs(width - expected) > 0.001) failures.add("Incorrect curve gauge: " + entry.getKey() + " / " + width);
            curves++;
        }
        return Map.of("materials", RailwayTracks.MATERIALS.size(), "nativeCurveTransforms", curves, "bogeyGaugePlacementChecks", bogeyPlacements);
    }
}

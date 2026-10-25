package com.misaka10843.kuayue_neo21.data;

import com.misaka10843.kuayue_neo21.content.PortRegistration;
import com.misaka10843.kuayue_neo21.content.bogey.VehicleBogeyBlock;
import com.simibubi.create.AllBogeyStyles;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.trains.bogey.AbstractBogeyBlock;
import com.simibubi.create.content.trains.bogey.AbstractBogeyBlockEntity;
import com.simibubi.create.content.trains.track.TrackBlock;
import com.simibubi.create.content.trains.track.TrackMaterial;
import com.simibubi.create.content.trains.track.TrackShape;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;

final class BogeyIntegrationAudit {
    static Map<String, Integer> run(HolderLookup.Provider registries, List<String> failures) {
        var nativeBogey = AllBlocks.SMALL_BOGEY.get();
        var reachableStyles = new HashSet<ResourceLocation>();
        var current = AllBogeyStyles.STANDARD;
        while (reachableStyles.add(current.id)) current = nativeBogey.getNextStyle(current);
        int bogeys = 0;
        int placements = 0;
        int restored = 0;
        int assembled = 0;
        for (var entry : PortRegistration.ENTRIES) {
            if (!(entry.get() instanceof VehicleBogeyBlock block)) continue;
            bogeys++;
            var style = block.getDefaultStyle();
            boolean standard = block.getTrackType(style) == TrackMaterial.TrackType.STANDARD;
            if (standard && !reachableStyles.contains(style.id)) failures.add("Bogey style unreachable from Create wrench: " + entry.id());
            if (style.getBlockForSize(block.getSize()) != block) failures.add("Bogey style resolves wrong block: " + entry.id());
            if (block.captureBlockEntityForTrain()) failures.add("Bogey would render twice after assembly: " + entry.id());
            for (var shape : new TrackShape[]{TrackShape.XO, TrackShape.ZO}) {
                var track = AllBlocks.TRACK.getDefaultState().setValue(TrackBlock.SHAPE, shape);
                var result = block.getPlacementOnTrack(EmptyBlockGetter.INSTANCE, BlockPos.ZERO, track);
                if (standard) {
                    var expectedAxis = shape == TrackShape.XO ? Direction.Axis.X : Direction.Axis.Z;
                    if (result == null || result.getValue(AbstractBogeyBlock.AXIS) != expectedAxis) failures.add("Track placement axis mismatch: " + entry.id() + " / " + shape);
                } else if (result != null) failures.add("Meter bogey accepted standard track: " + entry.id());
                placements++;
            }
            var state = block.defaultBlockState().setValue(AbstractBogeyBlock.AXIS, Direction.Axis.Z);
            var entity = block.newBlockEntity(BlockPos.ZERO, state);
            if (!(entity instanceof AbstractBogeyBlockEntity bogeyEntity)) {
                failures.add("Missing Create bogey entity: " + entry.id());
                continue;
            }
            var data = bogeyEntity.getBogeyData();
            data.putString("PortAudit", entry.id());
            bogeyEntity.setBogeyData(data);
            var carriageBogey = new com.simibubi.create.content.trains.entity.CarriageBogey(block, false, bogeyEntity.getBogeyData().copy());
            if (carriageBogey.getStyle() != style || !carriageBogey.bogeyData.getString("PortAudit").equals(entry.id()))
                failures.add("Native assembly lost bogey style/data: " + entry.id());
            else assembled++;
            var saved = entity.saveWithFullMetadata(registries);
            var loaded = BlockEntity.loadStatic(BlockPos.ZERO, state, saved, registries);
            if (!(loaded instanceof AbstractBogeyBlockEntity loadedBogey)
                    || loadedBogey.getStyle() != style || !loadedBogey.getBogeyData().getString("PortAudit").equals(entry.id()))
                failures.add("Bogey style/data failed save round trip: " + entry.id());
            else restored++;
            if (!(block.getNextSize(bogeyEntity).getBlock() instanceof AbstractBogeyBlock<?>)) failures.add("Size cycling lost bogey type: " + entry.id());
        }
        return Map.of("bogeys", bogeys, "nativeTrackPlacementChecks", placements, "savedBogeyRoundTrips", restored,
                "nativeAssemblyDataChecks", assembled);
    }
}

package com.misaka10843.kuayue_neo21.content.track;

import com.misaka10843.kuayue_neo21.content.PortRegistration;
import com.simibubi.create.content.trains.track.TrackMaterial;
import com.simibubi.create.content.trains.track.TrackMaterialFactory;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;

public final class RailwayTracks {
    public static final Map<String, PortRegistration<PortTrackBlock>> BLOCKS = new LinkedHashMap<>();
    public static final Map<String, TrackMaterial> MATERIALS = new LinkedHashMap<>();

    public static void initialize() {
        for (String id : new String[]{"standard", "tieless", "ballastless", "meter", "guard"}) {
            String tie = id.equals("tieless") ? "empty" : "track/" + (id.equals("standard") ? "standard" : id) + "/tie";
            String rails = id.equals("guard") ? "guard" : "standard";
            var material = TrackMaterialFactory.make(PortRegistration.resource(id))
                    .lang(id).block(() -> () -> BLOCKS.get(id).get())
                    .particle(PortRegistration.resource("block/palettes/stone_types/polished/andesite_cut_polished"))
                    .sleeper(Blocks.ANDESITE_SLAB).rails(Items.IRON_NUGGET)
                    .trackType(id.equals("meter") ? PortTrackTypes.METER : TrackMaterial.TrackType.STANDARD)
                    .customBlockFactory(PortTrackBlock::new)
                    .defaultModels()
                    .customModels(() -> () -> PartialModel.of(PortRegistration.resource("block/" + tie)),
                            () -> () -> PartialModel.of(PortRegistration.resource("block/track/" + rails + "/segment_left")),
                            () -> () -> PartialModel.of(PortRegistration.resource("block/track/" + rails + "/segment_right")))
                    .build();
            MATERIALS.put(id, material);
            BLOCKS.put(id, new PortRegistration<PortTrackBlock>(id + "_track").factory(properties ->
                    new PortTrackBlock(properties.strength(0.8f).sound(SoundType.METAL).forceSolidOn(), material)).submit());
        }
    }

    public static double curveRailOffset(TrackMaterial material, double fallback) {
        if (!material.id.getNamespace().equals("kuayue") || !MATERIALS.containsValue(material)) return fallback;
        return material.trackType == PortTrackTypes.METER ? 0.525 : 0.755;
    }
}

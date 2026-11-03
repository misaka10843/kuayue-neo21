package com.misaka10843.kuayue_neo21.compat;

import com.misaka10843.kuayue_neo21.content.PortRegistration;
import com.misaka10843.kuayue_neo21.content.bogey.VehicleBogeys;
import com.misaka10843.kuayue_neo21.content.track.PortTrackTypes;
import com.railwayteam.railways.api.bogeymenu.v0.BogeyMenuManager;
import com.railwayteam.railways.api.bogeymenu.v0.entry.CategoryEntry;
import com.railwayteam.railways.content.bogey_menu.handler.BogeyMenuHandlerServer;
import com.railwayteam.railways.registry.CRBogeyStyles;
import com.simibubi.create.AllBogeyStyles;
import com.simibubi.create.content.trains.bogey.AbstractBogeyBlock;
import com.simibubi.create.content.trains.bogey.BogeyStyle;
import com.simibubi.create.content.trains.track.TrackMaterial.TrackType;
import java.util.LinkedHashMap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

public final class RailwaysBogeyMenu {
    private RailwaysBogeyMenu() {}

    public static void register() {
        var manager = BogeyMenuManager.INSTANCE;
        var categories = new LinkedHashMap<String, CategoryEntry>();
        for (var variant : VehicleBogeys.MENU_VARIANTS) {
            AllBogeyStyles.BOGEY_STYLES.put(PortRegistration.resource("menu/" + variant.blockId()), variant.style());
            var category = categories.computeIfAbsent(variant.group(), group -> manager.registerCategory(
                    Component.translatable("bogey.menu.kuayue." + group), PortRegistration.resource("bogeys/" + group)));
            manager.addToCategory(category, variant.style(), null, 16);
        }
        BogeyStyle meterDefault = VehicleBogeys.MENU_VARIANTS.stream().filter(v -> v.blockId().equals("mkz_bogey"))
                .findFirst().orElseThrow().style();
        for (var variant : VehicleBogeys.MENU_VARIANTS) {
            var size = variant.style().validSizes().iterator().next();
            var block = (AbstractBogeyBlock<?>) variant.style().getBlockForSize(size);
            if (block.getTrackType(variant.style()) == PortTrackTypes.METER)
                CRBogeyStyles.map(variant.style(), TrackType.STANDARD, AllBogeyStyles.STANDARD, false);
            else CRBogeyStyles.map(variant.style(), PortTrackTypes.METER, meterDefault, false);
        }
    }

    public static BlockState selectedAnchor(TrackType trackType) {
        var player = BogeyMenuHandlerServer.getCurrentPlayer();
        if (player == null) return null;
        var selection = BogeyMenuHandlerServer.getStyle(player);
        var style = selection.getFirst();
        if (style == AllBogeyStyles.STANDARD) return null;
        style = CRBogeyStyles.getMapped(style, trackType, true).orElse(style);
        var size = selection.getSecond();
        if (size == null || !style.validSizes().contains(size)) size = style.validSizes().iterator().next();
        var block = style.getBlockForSize(size);
        if (!(block instanceof AbstractBogeyBlock<?> bogey) || !bogey.getValidPathfindingTypes(style).contains(trackType)) return null;
        return block.defaultBlockState();
    }
}

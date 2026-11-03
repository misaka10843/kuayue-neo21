package com.misaka10843.kuayue_neo21.data;

import com.misaka10843.kuayue_neo21.content.PortRegistration;
import com.misaka10843.kuayue_neo21.content.bogey.VehicleBogeys;
import com.misaka10843.kuayue_neo21.content.bogey.VehicleBogeyBlock;
import com.misaka10843.kuayue_neo21.content.track.PortTrackTypes;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllBogeyStyles;
import com.simibubi.create.content.trains.bogey.AbstractBogeyBlock;
import com.simibubi.create.content.trains.bogey.AbstractBogeyBlockEntity;
import com.simibubi.create.content.trains.track.TrackBlock;
import com.simibubi.create.content.trains.track.TrackMaterial;
import com.simibubi.create.content.trains.track.TrackShape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("kuayue")
@PrefixGameTestTemplate(false)
public final class RailwaysMenuWorldTests {
    @GameTest(template = "schematic_room")
    public static void menuSelectsExactBogeyAndPreservesGauge(GameTestHelper helper) {
        helper.assertTrue(VehicleBogeys.MENU_VARIANTS.size() == 53, "Bogey menu coverage changed");
        if (!ModList.get().isLoaded("railways")) {
            helper.assertTrue(VehicleBogeys.MENU_VARIANTS.stream().noneMatch(v -> AllBogeyStyles.BOGEY_STYLES.containsKey(v.style().id)),
                    "Optional Railways menu styles registered without Railways");
        } else WithRailways.verify(helper);
        helper.succeed();
    }

    private static final class WithRailways {
        static void verify(GameTestHelper helper) {
            var player = java.util.UUID.randomUUID();
            var manager = com.railwayteam.railways.api.bogeymenu.v0.BogeyMenuManager.INSTANCE;
            var oldPlayer = com.railwayteam.railways.content.bogey_menu.handler.BogeyMenuHandlerServer.getCurrentPlayer();
            try {
                com.railwayteam.railways.content.bogey_menu.handler.BogeyMenuHandlerServer.setCurrentPlayer(player);
                for (var variant : VehicleBogeys.MENU_VARIANTS) {
                    var category = manager.getCategoryById(PortRegistration.resource("bogeys/" + variant.group()));
                    helper.assertTrue(category != null && category.getBogeyEntryList().stream().filter(e -> e.bogeyStyle() == variant.style()).count() == 1,
                            "Missing or duplicated menu entry: " + variant.blockId());
                    helper.assertTrue(variant.style().validSizes().size() == 1, "Menu variant can resolve to a different model");
                    var size = variant.style().validSizes().iterator().next();
                    var expected = BuiltInRegistries.BLOCK.get(PortRegistration.resource(variant.blockId()));
                    com.railwayteam.railways.content.bogey_menu.handler.BogeyMenuHandlerServer.addStyle(player,
                            net.createmod.catnip.data.Pair.of(variant.style(), size));
                    for (var track : new TrackBlock[]{AllBlocks.TRACK.get(),
                            (TrackBlock) BuiltInRegistries.BLOCK.get(PortRegistration.resource("standard_track")),
                            (TrackBlock) BuiltInRegistries.BLOCK.get(PortRegistration.resource("meter_track"))}) {
                        for (var shape : new TrackShape[]{TrackShape.XO, TrackShape.ZO}) {
                            var state = track.defaultBlockState().setValue(TrackBlock.SHAPE, shape);
                            var anchor = track.getBogeyAnchor(helper.getLevel(), helper.absolutePos(BlockPos.ZERO), state);
                            var block = (AbstractBogeyBlock<?>) anchor.getBlock();
                            var requiredType = ((VehicleBogeyBlock) expected).getTrackType(variant.style());
                            if (requiredType == track.getMaterial().trackType)
                                helper.assertTrue(block == expected, "Menu placed wrong bogey: " + variant.blockId());
                            helper.assertTrue(block.getValidPathfindingTypes(block.getDefaultStyle()).contains(track.getMaterial().trackType),
                                    "Menu placed a bogey on an incompatible gauge");
                            helper.assertTrue(anchor.getValue(AbstractBogeyBlock.AXIS) == (shape == TrackShape.XO ? Direction.Axis.X : Direction.Axis.Z),
                                    "Menu placement lost track axis");
                        }
                    }
                    var pos = new BlockPos(2, 2, 2);
                    helper.setBlock(pos, expected.defaultBlockState());
                    var entity = (AbstractBogeyBlockEntity) helper.getBlockEntity(pos);
                    entity.setBogeyStyle(variant.style());
                    var saved = entity.saveWithFullMetadata(helper.getLevel().registryAccess());
                    var restored = net.minecraft.world.level.block.entity.BlockEntity.loadStatic(helper.absolutePos(pos),
                            expected.defaultBlockState(), saved, helper.getLevel().registryAccess());
                    helper.assertTrue(restored instanceof AbstractBogeyBlockEntity bogey && bogey.getStyle() == variant.style(),
                            "Menu selection failed saved style round trip");
                    var data = new com.simibubi.create.content.trains.entity.CarriageBogey((AbstractBogeyBlock<?>) expected, false, entity.getBogeyData());
                    helper.assertTrue(data.getStyle() == variant.style(), "Menu selection lost native assembly style");
                }
            } finally {
                com.railwayteam.railways.content.bogey_menu.handler.BogeyMenuHandlerServer.setCurrentPlayer(oldPlayer);
            }
        }
    }
}

package com.misaka10843.kuayue_neo21.data;

import com.misaka10843.kuayue_neo21.content.PortRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("kuayue")
@PrefixGameTestTemplate(false)
public final class SlabLightingWorldTests {
    @GameTest(template = "schematic_room", timeoutTicks = 120)
    public static void floorsMatchVanillaSlabLightOcclusion(GameTestHelper helper) {
        BlockState reference = Blocks.SMOOTH_STONE_SLAB.defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, SlabType.TOP);
        BlockState floor = BuiltInRegistries.BLOCK.get(PortRegistration.resource("floor_25b")).defaultBlockState();
        BlockState companion = BuiltInRegistries.BLOCK.get(PortRegistration.resource("company_panel_floor")).defaultBlockState();
        BlockState[] states = {reference, floor, companion};
        for (int room = 0; room < states.length; room++) {
            int startX = 1 + room * 10;
            for (int x = 0; x <= 6; x++) for (int z = 0; z <= 6; z++) for (int y = 1; y <= 5; y++) {
                var pos = new BlockPos(startX + x, y, 2 + z);
                boolean wall = x == 0 || x == 6 || z == 0 || z == 6 || y == 1;
                helper.setBlock(pos, wall ? Blocks.STONE.defaultBlockState() : y == 3 ? states[room] : Blocks.AIR.defaultBlockState());
            }
            helper.setBlock(startX + 3, 2, 5, Blocks.SEA_LANTERN);
            BlockPos sample = helper.absolutePos(new BlockPos(startX + 3, 3, 5));
            helper.assertTrue(states[room].canOcclude() && states[room].useShapeForLightOcclusion(), "Floor bypasses shape-based occlusion");
            for (Direction direction : Direction.values()) {
                var expected = reference.getFaceOcclusionShape(helper.getLevel(), sample, direction);
                var actual = states[room].getFaceOcclusionShape(helper.getLevel(), sample, direction);
                helper.assertTrue(!Shapes.joinIsNotEmpty(expected, actual, BooleanOp.NOT_SAME), "Floor occlusion differs from upper slab: " + direction);
            }
        }
        helper.runAtTickTime(10, () -> helper.succeedWhen(() -> {
            for (int room = 0; room < states.length; room++) {
                int startX = 1 + room * 10;
                helper.assertTrue(helper.getLevel().getBrightness(LightLayer.BLOCK, helper.absolutePos(new BlockPos(startX + 3, 2, 5))) == 15,
                        "Light source has not propagated");
                for (int x = 1; x <= 5; x++) for (int z = 1; z <= 5; z++) {
                    int actual = helper.getLevel().getBrightness(LightLayer.BLOCK, helper.absolutePos(new BlockPos(startX + x, 4, 2 + z)));
                    helper.assertTrue(actual == 0, "Light leaked through opaque floor top at room " + room + ": " + actual);
                }
            }
        }));
    }
}

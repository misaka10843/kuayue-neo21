package com.misaka10843.kuayue_neo21.data;

import com.google.gson.JsonParser;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.CompanyTrainPanel;
import com.simibubi.create.foundation.utility.BlockHelper;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("kuayue")
@PrefixGameTestTemplate(false)
public final class SchematicWorldTests {
    @GameTest(template = "schematic_room", timeoutTicks = 40)
    public static void schematicForward(GameTestHelper helper) { place(helper, false); }

    @GameTest(template = "schematic_room", timeoutTicks = 40)
    public static void schematicReverse(GameTestHelper helper) { place(helper, true); }

    private static void place(GameTestHelper helper, boolean reverse) {
        try {
            Path project = Path.of(System.getProperty("kuayue.projectDir"));
            Path fixture = project.resolve(System.getProperty("kuayue.schematicFixture"));
            var data = JsonParser.parseString(Files.readString(fixture)).getAsJsonObject();
            var palette = data.getAsJsonArray("palette");
            var entries = new ArrayList<>(data.getAsJsonArray("blocks").asList());
            if (reverse) Collections.reverse(entries);
            var expected = new LinkedHashMap<BlockPos, BlockState>();
            var level = helper.getLevel();
            for (int x = 0; x < 30; x++) for (int z = 0; z < 12; z++) helper.setBlock(x, 0, z, Blocks.STONE);
            for (var element : entries) {
                var entry = element.getAsJsonObject();
                var p = entry.getAsJsonArray("pos");
                BlockPos local = new BlockPos(p.get(0).getAsInt() + 1, p.get(1).getAsInt() + 1, p.get(2).getAsInt() + 1);
                var definition = palette.get(entry.get("state").getAsInt()).getAsJsonObject();
                var id = ResourceLocation.parse(definition.get("Name").getAsString());
                BlockState state = BuiltInRegistries.BLOCK.containsKey(id)
                        ? NbtUtils.readBlockState(level.holderLookup(Registries.BLOCK), TagParser.parseTag(definition.toString()))
                        : Blocks.BARRIER.defaultBlockState();
                var nbt = entry.has("nbt") ? TagParser.parseTag(entry.get("nbt").toString()) : null;
                BlockHelper.placeSchematicBlock(level, state, helper.absolutePos(local), new ItemStack(state.getBlock()), nbt);
                if (!state.isAir() && !(state.getBlock() instanceof CompanyTrainPanel)) expected.put(local, state);
            }
            helper.runAtTickTime(8, () -> {
                for (var entry : expected.entrySet()) helper.assertBlockPresent(entry.getValue().getBlock(), entry.getKey());
                helper.assertEntityNotPresent(EntityType.ITEM);
                helper.succeed();
            });
        } catch (Exception error) {
            throw new IllegalStateException("Cannot place schematic regression fixture", error);
        }
    }
}

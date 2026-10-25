package com.misaka10843.kuayue_neo21.data;

import com.misaka10843.kuayue_neo21.content.PortRegistration;
import com.misaka10843.kuayue_neo21.content.infrastructure.InfrastructureBlocks;
import com.misaka10843.kuayue_neo21.content.infrastructure.PantographMounting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("kuayue")
@PrefixGameTestTemplate(false)
public final class ElectroEnergeticsWorldTests {
    @GameTest(template = "schematic_room", timeoutTicks = 40)
    public static void electroEnergeticsMounts(GameTestHelper helper) {
        if (!ModList.get().isLoaded("electroenergetics")) { helper.succeed(); return; }
        try {
            var level = helper.getLevel();
            BlockPos roof = new BlockPos(3, 2, 3);
            helper.setBlock(roof, BuiltInRegistries.BLOCK.get(PortRegistration.resource("carport_center_25bgzkt")));
            var pantograph = BuiltInRegistries.BLOCK.get(ResourceLocation.parse("electroenergetics:pantograph"));
            helper.setBlock(roof.above(), pantograph);
            BlockPos absolute = helper.absolutePos(roof.above());
            double offset = PantographMounting.offset(level, absolute);
            helper.assertTrue(Math.abs(offset + 13.0 / 16) < 0.00001, "Pantograph base does not meet half-height roof");
            var entity = level.getBlockEntity(absolute);
            var saved = entity.saveWithoutMetadata(level.registryAccess());
            helper.assertTrue(Math.abs(saved.getDouble(PantographMounting.OFFSET_TAG) - offset) < 0.00001,
                    "Assembly NBT lost pantograph mount offset");
            helper.setBlock(6, 2, 3, InfrastructureBlocks.OVERHEAD_PILLAR_STEEL.get());
            var mount = BuiltInRegistries.BLOCK.get(ResourceLocation.parse("electroenergetics:pole_mount"));
            helper.setBlock(new BlockPos(7, 2, 3), mount.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST));
            var holder = BuiltInRegistries.BLOCK.get(ResourceLocation.parse("electroenergetics:catenary_holder"));
            helper.setBlock(6, 2, 5, holder);
            var holderEntity = helper.getBlockEntity(new BlockPos(6, 2, 5));
            var widthMethod = holderEntity.getClass().getDeclaredMethod("getPoleWidth", BlockPos.class);
            widthMethod.setAccessible(true);
            helper.assertTrue((int) widthMethod.invoke(holderEntity, helper.absolutePos(new BlockPos(6, 2, 3))) > 0,
                    "CEE holder did not recognize Kuayue pillar");
            helper.runAtTickTime(8, () -> {
                helper.assertBlockPresent(mount, new BlockPos(7, 2, 3));
                helper.setBlock(roof, Blocks.AIR);
                helper.assertTrue(PantographMounting.offset(level, absolute) == 0, "Removed roof left stale offset");
                helper.succeed();
            });
        } catch (ReflectiveOperationException error) {
            throw new IllegalStateException("CEE mount API changed", error);
        }
    }
}

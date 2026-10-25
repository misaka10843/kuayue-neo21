package com.misaka10843.kuayue_neo21.content.infrastructure;

import com.misaka10843.kuayue_neo21.content.PortRegistration;
import com.misaka10843.kuayue_neo21.legacy.block.panels.deco.DirectionalCarpetBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.*;
import net.minecraft.world.phys.Vec3;

public final class InfrastructureBlocks {
    public static final PortRegistration<SlabBlock> CLAY_SLAB = new PortRegistration<SlabBlock>("clay_slab")
            .factory(SlabBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<StairBlock> CLAY_STAIRS = new PortRegistration<StairBlock>("clay_stairs")
            .factory(p -> new StairBlock(Blocks.CLAY.defaultBlockState(), p)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<QuartzPanelBlock2> SMOOTH_QUARTZ_PANEL_2 = new PortRegistration<QuartzPanelBlock2>("smooth_quartz_panel_2")
            .factory(QuartzPanelBlock2::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<QuartzPanelBlockHalf> SMOOTH_QUARTZ_PANEL_HALF = new PortRegistration<QuartzPanelBlockHalf>("smooth_quartz_panel_half")
            .factory(QuartzPanelBlockHalf::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<QuartzPanelBlock4> SMOOTH_QUARTZ_PANEL_4 = new PortRegistration<QuartzPanelBlock4>("smooth_quartz_panel_4")
            .factory(QuartzPanelBlock4::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<QuartzAngleBlock22> SMOOTH_QUARTZ_ANGLE_22_UP = new PortRegistration<QuartzAngleBlock22>("smooth_quartz_angle_22_up")
            .factory(QuartzAngleBlock22::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<FullWeatheringSteelBlock> WEATHERING_STEEL_BLOCK = new PortRegistration<FullWeatheringSteelBlock>("weathering_resistant_steel_block_0")
            .factory(p -> new FullWeatheringSteelBlock(IWeatheringSteel.WSWeatherState.WS, p)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<FullWeatheringSteelBlock> EXPOSED_WEATHERING_STEEL_BLOCK = new PortRegistration<FullWeatheringSteelBlock>("exposed_weathering_steel_block")
            .factory(p -> new FullWeatheringSteelBlock(IWeatheringSteel.WSWeatherState.EXPOSED_WS, p)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<FullWeatheringSteelBlock> YELLOWING_WEATHERING_STEEL_BLOCK = new PortRegistration<FullWeatheringSteelBlock>("yellowing_weathering_steel_block")
            .factory(p -> new FullWeatheringSteelBlock(IWeatheringSteel.WSWeatherState.YELLOWING_WS, p)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<FullWeatheringSteelBlock> RUSTED_WEATHERING_STEEL_BLOCK = new PortRegistration<FullWeatheringSteelBlock>("rusted_weathering_steel_block")
            .factory(p -> new FullWeatheringSteelBlock(IWeatheringSteel.WSWeatherState.RUSTED_WS, p)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<FullWeatheringSteelBlock> STABLY_RUSTED_WEATHERING_STEEL_BLOCK = new PortRegistration<FullWeatheringSteelBlock>("stably_rusted_weathering_steel_block")
            .factory(p -> new FullWeatheringSteelBlock(IWeatheringSteel.WSWeatherState.STABLY_WS, p)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<FullWeatheringSteelBlock> FULLY_RUSTED_WEATHERING_STEEL_BLOCK = new PortRegistration<FullWeatheringSteelBlock>("fully_rusted_weathering_steel_block")
            .factory(p -> new FullWeatheringSteelBlock(IWeatheringSteel.WSWeatherState.FULLY_WS, p)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<DirectionalCarpetBlock> ORIGINAL_25Z_FLOOR_CARPET = new PortRegistration<DirectionalCarpetBlock>("original_25z_floor_carpet")
            .factory(DirectionalCarpetBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<DropExperienceBlock> SALT_ORE = new PortRegistration<DropExperienceBlock>("salt_ore")
            .factory(p -> new DropExperienceBlock(UniformInt.of(3, 7), p)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<DropExperienceBlock> DEEPSLATE_SALT_ORE = new PortRegistration<DropExperienceBlock>("deepslate_salt_ore")
            .factory(p -> new DropExperienceBlock(UniformInt.of(3, 7), p)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<Block> SALT_BLOCK = new PortRegistration<Block>("salt_block")
            .factory(Block::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<PlatformDoorBlock> PLATFORM_DOOR = new PortRegistration<PlatformDoorBlock>("platform_door")
            .factory(PlatformDoorBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<PlatformWallBlock> PLATFORM_WALL = new PortRegistration<PlatformWallBlock>("platform_wall")
            .factory(PlatformWallBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<SimpleOverheadLinePillarBlock> OVERHEAD_PILLAR_CONCRETE = new PortRegistration<SimpleOverheadLinePillarBlock>("overhead_pillar_concrete")
            .factory(SimpleOverheadLinePillarBlock.Builder.create(
                            SimpleOverheadLinePillarBlock.defaultProperties()
                    )).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<SimpleOverheadLinePillarBlock> OVERHEAD_PILLAR_STEEL = new PortRegistration<SimpleOverheadLinePillarBlock>("overhead_pillar_steel")
            .factory(SimpleOverheadLinePillarBlock.Builder.create(
                            SimpleOverheadLinePillarBlock.defaultProperties()
                    )).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<SimpleOverheadLinePillarBlock> OVERHEAD_SQUARE_PILLAR_CONCRETE_PUNCHING = new PortRegistration<SimpleOverheadLinePillarBlock>("overhead_square_pillar_concrete_punching")
            .factory(SimpleOverheadLinePillarBlock.Builder.create(
                            SimpleOverheadLinePillarBlock.defaultProperties()
                                    .directional()
                    )).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<SimpleOverheadLinePillarBlock> OVERHEAD_SQUARE_PILLAR_STEEL = new PortRegistration<SimpleOverheadLinePillarBlock>("overhead_square_pillar_steel")
            .factory(SimpleOverheadLinePillarBlock.Builder.create(
                            SimpleOverheadLinePillarBlock.defaultProperties()
                                    .directional()
                    )).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<OverheadTruss> OVERHEAD_TRUSS_DOUBLE = new PortRegistration<OverheadTruss>("overhead_truss_double")
            .factory(p -> new OverheadTruss(p, new Vec3(0,0,1), new Vec3(1,1,1))).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<OverheadTruss> OVERHEAD_TRUSS_DOUBLE_AD = new PortRegistration<OverheadTruss>("overhead_truss_double_ad")
            .factory(p -> new OverheadTruss(p, new Vec3(0,0,-1), new Vec3(1,1,1))).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<OverheadTrussSmall> OVERHEAD_TRUSS_SINGLE_AD = new PortRegistration<OverheadTrussSmall>("overhead_truss_single_ad")
            .factory(p -> new OverheadTrussSmall(p, new Vec3(0,0,1), new Vec3(1,1,1))).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<OverheadTrussSmall> OVERHEAD_TRUSS_SINGLE_A = new PortRegistration<OverheadTrussSmall>("overhead_truss_single_a")
            .factory(p -> new OverheadTrussSmall(p, new Vec3(0,0,1), new Vec3(1,1,1))).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<OverheadTrussSmall> OVERHEAD_TRUSS_SINGLE_B = new PortRegistration<OverheadTrussSmall>("overhead_truss_single_b")
            .factory(p -> new OverheadTrussSmall(p, new Vec3(0,0,1), new Vec3(1,1,1))).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<OverheadTrussSmall> OVERHEAD_TRUSS_SINGLE_C = new PortRegistration<OverheadTrussSmall>("overhead_truss_single_c")
            .factory(p -> new OverheadTrussSmall(p, new Vec3(0,0,1), new Vec3(1,1,1))).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<OverheadTrussPillar> OVERHEAD_TRUSS_CONCRETE = new PortRegistration<OverheadTrussPillar>("overhead_truss_concrete")
            .factory(p -> new OverheadTrussPillar(p, new Vec3(0,0,1), new Vec3(1,1,1))).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<OverheadTrussPillar> OVERHEAD_PILLAR_CONCRETE_TRUSS_A = new PortRegistration<OverheadTrussPillar>("overhead_pillar_concrete_truss_a")
            .factory(p -> new OverheadTrussPillar(p, new Vec3(0,0,-2), new Vec3(1,1,1))).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<OverheadTrussPillar> OVERHEAD_PILLAR_CONCRETE_TRUSS_B = new PortRegistration<OverheadTrussPillar>("overhead_pillar_concrete_truss_b")
            .factory(p -> new OverheadTrussPillar(p, new Vec3(0,0,-2), new Vec3(1,1,1))).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<OverheadTrussPillar> OVERHEAD_TRIANGLE_TRUSS_STEEL = new PortRegistration<OverheadTrussPillar>("overhead_triangle_truss_steel")
            .factory(p -> new OverheadTrussPillar(p, new Vec3(0,0,0), new Vec3(1,1,1))).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<OverheadTrussPillar> OVERHEAD_TRIANGLE_TRUSS_STEEL_AD = new PortRegistration<OverheadTrussPillar>("overhead_triangle_truss_steel_ad")
            .factory(p -> new OverheadTrussPillar(p, new Vec3(0,0,0), new Vec3(1,1,1))).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<LinkedTrussBlock> TRUSS_HANGER_SINGLE = new PortRegistration<LinkedTrussBlock>("truss_hanger_single")
            .factory(LinkedTrussBlock.Builder.create(
                            new LinkedTrussBlock.SpecialBlockConfig()
                                    .addSpecialBlock(
                                            ResourceLocation.fromNamespaceAndPath("kuayue", "overhead_truss_single_a"),
                                            LinkedTrussBlock.ConnectedType.SINGLE
                                    )
                                    .addSpecialBlock(
                                            ResourceLocation.fromNamespaceAndPath("kuayue", "overhead_truss_single_b"),
                                            LinkedTrussBlock.ConnectedType.SINGLE
                                    )
                                    .addSpecialBlock(
                                            ResourceLocation.fromNamespaceAndPath("kuayue", "overhead_truss_single_c"),
                                            LinkedTrussBlock.ConnectedType.SINGLE
                                    )
                                    .addSpecialBlock(
                                            ResourceLocation.fromNamespaceAndPath("kuayue", "overhead_truss_double"),
                                            LinkedTrussBlock.ConnectedType.DOUBLE
                                    )
                                    .addSpecialBlock(
                                            ResourceLocation.fromNamespaceAndPath("kuayue", "overhead_truss_concrete"),
                                            LinkedTrussBlock.ConnectedType.CONCRETE
                                    )
                                    .addSpecialBlock(
                                            ResourceLocation.fromNamespaceAndPath("kuayue", "overhead_triangle_truss_steel"),
                                            LinkedTrussBlock.ConnectedType.TRIANGLE
                                    )
                    )).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<LinkedTrussBlock> TRUSS_HANGER_DOUBLE = new PortRegistration<LinkedTrussBlock>("truss_hanger_double")
            .factory(LinkedTrussBlock.Builder.create(
                            new LinkedTrussBlock.SpecialBlockConfig()
                                    .addSpecialBlock(
                                            ResourceLocation.fromNamespaceAndPath("kuayue", "overhead_truss_single_a"),
                                            LinkedTrussBlock.ConnectedType.SINGLE
                                    )
                                    .addSpecialBlock(
                                            ResourceLocation.fromNamespaceAndPath("kuayue", "overhead_truss_single_b"),
                                            LinkedTrussBlock.ConnectedType.SINGLE
                                    )
                                    .addSpecialBlock(
                                            ResourceLocation.fromNamespaceAndPath("kuayue", "overhead_truss_single_c"),
                                            LinkedTrussBlock.ConnectedType.SINGLE
                                    )
                                    .addSpecialBlock(
                                            ResourceLocation.fromNamespaceAndPath("kuayue", "overhead_truss_double"),
                                            LinkedTrussBlock.ConnectedType.DOUBLE
                                    )
                                    .addSpecialBlock(
                                            ResourceLocation.fromNamespaceAndPath("kuayue", "overhead_truss_concrete"),
                                            LinkedTrussBlock.ConnectedType.CONCRETE
                                    )
                                    .addSpecialBlock(
                                            ResourceLocation.fromNamespaceAndPath("kuayue", "overhead_triangle_truss_steel"),
                                            LinkedTrussBlock.ConnectedType.TRIANGLE
                                    )
                    )).strengthAndTool(1.5f, 3f).submit();

    public static void initialize() {}
}

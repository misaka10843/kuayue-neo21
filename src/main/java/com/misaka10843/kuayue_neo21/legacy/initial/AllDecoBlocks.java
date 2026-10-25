package com.misaka10843.kuayue_neo21.legacy.initial;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import com.misaka10843.kuayue_neo21.legacy.block.panels.SkirtBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.CarriageUnderground;
import com.misaka10843.kuayue_neo21.legacy.block.panels.deco.ACOutdoorUnitBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.deco.FlourescentLightBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.deco.TeaBoilerBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.deco.YZTableBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.CeilinShelfBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.TrainSlabBlock;
import com.misaka10843.kuayue_neo21.legacy.block.seat.M1SeatBlock;
import com.misaka10843.kuayue_neo21.legacy.block.seat.RZSeatBlock;
import com.misaka10843.kuayue_neo21.legacy.block.seat.YZSeatBlock;
import com.misaka10843.kuayue_neo21.content.PortRegistration;

public final class AllDecoBlocks {
    public static final YZSeatBlock.OffsetFunction YZ_FUNCTION_1 =
            ((state, index) -> {
                Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
                if (facing == Direction.NORTH || facing == Direction.SOUTH) {
                    return switch (index) {
                        case 0 -> new Vec3(-.25, 0, -0.3125);
                        case 1 -> new Vec3(.25, 0, -0.3125);
                        case 2 -> new Vec3(.25, 0, 0.3125);
                        default -> new Vec3(-.25, 0, 0.3125);
                    };
                }
                return switch (index) {
                    case 0 -> new Vec3(0.3125, 0, .25);
                    case 1 -> new Vec3(0.3125, 0, -.25);
                    case 2 -> new Vec3(-0.3125, 0, -.25);
                    default -> new Vec3(-0.3125, 0, .25);
                };
            });

    public static final YZSeatBlock.OffsetFunction M1_SEAT_FUNCTION =
            ((state, index) -> {
                Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
                Boolean isOffset = state.getValue(M1SeatBlock.SEAT_OFFSET);
                double yOffset = -.8;
                if (facing == Direction.NORTH || facing == Direction.SOUTH) {
                    if (!isOffset)
                        return index == 0 ? new Vec3(-.35, yOffset, 0) : new Vec3(.35, yOffset, 0);
                    if (facing == Direction.NORTH)
                        return index == 0 ? new Vec3(.25, yOffset, 0) : new Vec3(.85, yOffset, 0);
                    else
                        return index == 0 ? new Vec3(-.85, yOffset, 0) : new Vec3(-.25, yOffset, 0);
                }
                if (!isOffset)
                    return index == 0 ? new Vec3(0, yOffset, -.35) : new Vec3(0, yOffset, .35);
                if (facing == Direction.WEST)
                    return index == 0 ? new Vec3(0, yOffset, -.85) : new Vec3(0, yOffset, -.25);
                else
                    return index == 0 ? new Vec3(0, yOffset, .25) : new Vec3(0, yOffset, .85);
            });

    public static final PortRegistration<TeaBoilerBlock> BOILING_WATER_PLACE =
        new PortRegistration<TeaBoilerBlock>("boiling_water_place")
                    .factory(p -> new TeaBoilerBlock(p, new Vec2(0, 0), new Vec2(1, 2)))
                    
                    
                    .addProperty(BlockBehaviour.Properties::noOcclusion)
                    .addProperty(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .addProperty(properties -> properties.strength(1.0f, 2.0f))
                    
                    
                    .submit();

    public static final PortRegistration<CeilinShelfBlock> CEILIN_SHELF =
        new PortRegistration<CeilinShelfBlock>("ceilin_shelf")
                    .factory(CeilinShelfBlock::new)
                    
                    
                    .addProperty(BlockBehaviour.Properties::noOcclusion)
                    .addProperty(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .addProperty(properties -> properties.strength(1.0f, 2.0f))
                    
                    
                    .submit();

    public static final PortRegistration<CeilinShelfBlock> CEILIN_SHELF_2 =
        new PortRegistration<CeilinShelfBlock>("ceilin_shelf_2")
                    .factory(CeilinShelfBlock::new)
                    
                    
                    .addProperty(BlockBehaviour.Properties::noOcclusion)
                    .addProperty(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .addProperty(properties -> properties.strength(1.0f, 2.0f))
                    
                    
                    .submit();

    public static final PortRegistration<CeilinShelfBlock> CEILIN_SHELF_3 =
        new PortRegistration<CeilinShelfBlock>("ceilin_shelf_3")
                    .factory(CeilinShelfBlock::new)
                    
                    
                    .addProperty(BlockBehaviour.Properties::noOcclusion)
                    .addProperty(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .addProperty(properties -> properties.strength(1.0f, 2.0f))
                    
                    
                    .submit();

    public static final PortRegistration<CeilinShelfBlock> LUGGAGE_RACK_M1 =
        new PortRegistration<CeilinShelfBlock>("luggage_rack_m1")
                    .factory(CeilinShelfBlock::new)
                    
                    
                    .addProperty(BlockBehaviour.Properties::noOcclusion)
                    .addProperty(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .addProperty(properties -> properties.strength(1.0f, 2.0f))
                    
                    
                    .submit();

    public static final PortRegistration<YZTableBlock> YZ_TABLE =
        new PortRegistration<YZTableBlock>("yz_table")
                    .factory(YZTableBlock::new)
                    
                    
                    .addProperty(BlockBehaviour.Properties::noOcclusion)
                    .addProperty(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .addProperty(properties -> properties.strength(1.0f, 2.0f))
                    
                    
                    .submit();

    public static final PortRegistration<RZSeatBlock> RZ_SEAT =
        new PortRegistration<RZSeatBlock>("seat_rz")
                    .factory(RZSeatBlock::new)
                    
                    
                    .addProperty(BlockBehaviour.Properties::noOcclusion)
                    .addProperty(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .addProperty(properties -> properties.strength(1.0f, 2.0f))
                    
                    
                    .submit();

    public static final PortRegistration<YZSeatBlock> YZ_SEAT_BLUE =
        new PortRegistration<YZSeatBlock>("yz_seat_blue")
                    .factory(p -> new YZSeatBlock(p, 4, YZ_FUNCTION_1))
                    
                    
                    .addProperty(BlockBehaviour.Properties::noOcclusion)
                    .addProperty(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .addProperty(properties -> properties.strength(1.0f, 2.0f))
                    
                    
                    .submit();

    public static final PortRegistration<YZSeatBlock> YZ_SEAT_BLACK =
        new PortRegistration<YZSeatBlock>("yz_seat_black")
                    .factory(p -> new YZSeatBlock(p, 4, YZ_FUNCTION_1))
                    
                    
                    .addProperty(BlockBehaviour.Properties::noOcclusion)
                    .addProperty(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .addProperty(properties -> properties.strength(1.0f, 2.0f))
                    
                    
                    .submit();

    public static final PortRegistration<YZSeatBlock> YZ_SEAT_GREEN =
        new PortRegistration<YZSeatBlock>("yz_seat_green")
                    .factory(p -> new YZSeatBlock(p, 4, YZ_FUNCTION_1))
                    
                    
                    .addProperty(BlockBehaviour.Properties::noOcclusion)
                    .addProperty(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .addProperty(properties -> properties.strength(1.0f, 2.0f))
                    
                    
                    .submit();

    public static final PortRegistration<YZSeatBlock> YZ_SEAT_RED =
        new PortRegistration<YZSeatBlock>("yz_seat_red")
                    .factory(p -> new YZSeatBlock(p, 4, YZ_FUNCTION_1))
                    
                    
                    .addProperty(BlockBehaviour.Properties::noOcclusion)
                    .addProperty(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .addProperty(properties -> properties.strength(1.0f, 2.0f))
                    
                    
                    .submit();

    public static final PortRegistration<YZSeatBlock> YZ_SEAT_YELLOW =
        new PortRegistration<YZSeatBlock>("yz_seat_yellow")
                    .factory(p -> new YZSeatBlock(p, 4, YZ_FUNCTION_1))
                    
                    
                    .addProperty(BlockBehaviour.Properties::noOcclusion)
                    .addProperty(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .addProperty(properties -> properties.strength(1.0f, 2.0f))
                    
                    
                    .submit();

    public static final PortRegistration<YZSeatBlock> YZ_SEAT_CYAN =
        new PortRegistration<YZSeatBlock>("yz_seat_cyan")
                    .factory(p -> new YZSeatBlock(p, 4, YZ_FUNCTION_1))
                    
                    
                    .addProperty(BlockBehaviour.Properties::noOcclusion)
                    .addProperty(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .addProperty(properties -> properties.strength(1.0f, 2.0f))
                    
                    
                    .submit();

    public static final PortRegistration<YZSeatBlock> YZ_SEAT_GRAY =
        new PortRegistration<YZSeatBlock>("yz_seat_gray")
                    .factory(p -> new YZSeatBlock(p, 4, YZ_FUNCTION_1))
                    
                    
                    .addProperty(BlockBehaviour.Properties::noOcclusion)
                    .addProperty(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .addProperty(properties -> properties.strength(1.0f, 2.0f))
                    
                    
                    .submit();

    public static final PortRegistration<YZSeatBlock> YZ_SEAT_2 =
        new PortRegistration<YZSeatBlock>("yz_seat_22")
                    .factory(p -> new YZSeatBlock(p, 2,
                                ((state, index) -> {
                                    Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
                                    if (facing == Direction.NORTH || facing == Direction.SOUTH) {
                                        return index == 0 ? new Vec3(-.25, 0, 0) : new Vec3(.25, 0, 0);
                                    }
                                    return index == 0 ? new Vec3(0, 0, -.25) : new Vec3(0, 0, .25);
                                })
                            ))
                    
                    
                    .addProperty(BlockBehaviour.Properties::noOcclusion)
                    .addProperty(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .addProperty(properties -> properties.strength(1.0f, 2.0f))
                    
                    
                    .submit();

    public static final PortRegistration<M1SeatBlock> SEAT_M1 =
        new PortRegistration<M1SeatBlock>("seat_m1")
                    .factory(properties ->
                            new M1SeatBlock(properties, 2, M1_SEAT_FUNCTION))
                    
                    
                    .addProperty(BlockBehaviour.Properties::noOcclusion)
                    .addProperty(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .addProperty(properties -> properties.strength(1.0f, 2.0f))
                    
                    
                    .submit();

    public static final PortRegistration<FlourescentLightBlock> FLOURESCENT_LIGHT =
        new PortRegistration<FlourescentLightBlock>("flourescent_light")
                    .factory(p -> new FlourescentLightBlock(p, false))
                    
                    
                    .addProperty(BlockBehaviour.Properties::noOcclusion)
                    .addProperty(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .addProperty(properties -> properties.strength(1.0f, 2.0f))
                    .addProperty(properties -> properties.lightLevel(
                                    state -> state.getValue(FlourescentLightBlock.OPEN) ? 15 : 0))
                    
                    
                    .submit();

    public static final PortRegistration<ACOutdoorUnitBlock> AC_OUTDOOR_UNIT =
        new PortRegistration<ACOutdoorUnitBlock>("ac_outdoor_unit")
                    .factory(ACOutdoorUnitBlock::new)
                    
                    
                    .addProperty(BlockBehaviour.Properties::noOcclusion)
                    .addProperty(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .addProperty(properties -> properties.strength(1.0f, 2.0f))
                    
                    
                    .submit();

    public static final PortRegistration<SkirtBlock> AUXILIARY_RESERVOIR =
        new PortRegistration<SkirtBlock>("auxiliary_reservoir")
                    .factory(SkirtBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<SkirtBlock> AUXILIARY_RESERVOIR_LONGITUDINAL =
        new PortRegistration<SkirtBlock>("auxiliary_reservoir_longitudinal")
                    .factory(SkirtBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<SkirtBlock> CARRIAGE_BATTERY =
        new PortRegistration<SkirtBlock>("carriage_battery")
                    .factory(SkirtBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<CarriageUnderground> VACUUM_TOILET_1 =
        new PortRegistration<CarriageUnderground>("vacuum_toilet_1")
                    .factory(CarriageUnderground::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f,3f)
                    .submit();

    public static final PortRegistration<CarriageUnderground> VACUUM_TOILET_2 =
        new PortRegistration<CarriageUnderground>("vacuum_toilet_2")
                    .factory(CarriageUnderground::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f,3f)
                    .submit();

    public static final PortRegistration<CarriageUnderground> VACUUM_TOILET_3 =
        new PortRegistration<CarriageUnderground>("vacuum_toilet_3")
                    .factory(CarriageUnderground::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f,3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> CARRIAGE_BRAKE_CYLINDER =
        new PortRegistration<TrainSlabBlock>("carriage_brake_cylinder")
                    .factory(p -> new TrainSlabBlock(p, false))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();
}

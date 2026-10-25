package com.misaka10843.kuayue_neo21.legacy.initial.panel;

import net.createmod.catnip.data.Couple;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainPanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.TrainPanelProperties;
import com.misaka10843.kuayue_neo21.legacy.block.panels.door.CustomRenderedDoorBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.end_face.MeterCarriageEndFaceBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.HingeSlabBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.TrainLadderBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.TrainSlabBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.window.TrainOpenableWindowBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.window.TrainSmallWindowBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.window.TrainUnOpenableWindowBlock;
import com.misaka10843.kuayue_neo21.content.PortRegistration;

public final class CM1Panel {
    public static final PortRegistration<TrainSlabBlock> FLOOR_M1 =
        new PortRegistration<TrainSlabBlock>("floor_m1")
                    .factory(p -> new TrainSlabBlock(p, true))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainPanelBlock> BOTTOM_SLAB_M1 =
        new PortRegistration<TrainPanelBlock>("slab_bottom_m1")
                    .factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainOpenableWindowBlock> WINDOW_LARGE_M1 =
        new PortRegistration<TrainOpenableWindowBlock>("window_large_m1")
                    .factory(p -> new TrainOpenableWindowBlock(p, -1, 1, 2))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSmallWindowBlock> WINDOW_LARGE_BETWEEN_M1 =
        new PortRegistration<TrainSmallWindowBlock>("window_large_between_m1")
                    .factory(p -> new TrainSmallWindowBlock(p, 2))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainOpenableWindowBlock> WINDOW_SMALL_M1 =
        new PortRegistration<TrainOpenableWindowBlock>("window_small_m1")
                    .factory(p -> new TrainOpenableWindowBlock(p, -1, 1, 2))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainOpenableWindowBlock> WINDOW_DOUBLE_SMALL_M1 =
        new PortRegistration<TrainOpenableWindowBlock>("window_double_small_m1")
                    .factory(p -> new TrainOpenableWindowBlock(p, -1, 1, 2))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<CustomRenderedDoorBlock> DOOR_M1 =
        new PortRegistration<CustomRenderedDoorBlock>("door_m1")
                    .factory(p -> new CustomRenderedDoorBlock(p,
                            Couple.create(
                                    com.misaka10843.kuayue_neo21.content.PortRegistration.resource("carriage/carriage_m1/door/m1_door_bottom_lh"),
                                    com.misaka10843.kuayue_neo21.content.PortRegistration.resource("carriage/carriage_m1/door/m1_door_upper_lh")
                            ), Couple.create(
                            com.misaka10843.kuayue_neo21.content.PortRegistration.resource("carriage/carriage_m1/door/m1_door_bottom"),
                            com.misaka10843.kuayue_neo21.content.PortRegistration.resource("carriage/carriage_m1/door/m1_door_upper")
                    ), new Vec3(0, 0, 0),new Vec3(0, 0, 0), RenderShape.MODEL, false
                    ))
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    
                    .submit();

    public static final PortRegistration<TrainLadderBlock> LADDER_M1 =
        new PortRegistration<TrainLadderBlock>("ladder_m1")
                    .factory((properties) -> new TrainLadderBlock(properties, true))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<MeterCarriageEndFaceBlock> END_FACE_M1 =
        new PortRegistration<MeterCarriageEndFaceBlock>("end_face_m1")
                    .factory(properties ->
                            new MeterCarriageEndFaceBlock(
                                    properties, TrainPanelProperties.DoorType.NO_DOOR, true))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<MeterCarriageEndFaceBlock> END_FACE_MIDDLE_M1 =
        new PortRegistration<MeterCarriageEndFaceBlock>("end_face_middle_m1")
                    .factory(properties -> new MeterCarriageEndFaceBlock(
                            properties, TrainPanelProperties.DoorType.SLIDE,
                            "carriage/carriage_m1/end_face/m1_middle_end_face_door",
                            null,
                            "carriage/carriage_m1/end_face/m1_middle_end_face_frame", false))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> GENERAL_CARPORT_M1 =
        new PortRegistration<TrainSlabBlock>("carport_general_m1")
                    .factory(p -> new TrainSlabBlock(p, true))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> AIR_VENT_CARPORT_M1 =
        new PortRegistration<TrainSlabBlock>("carport_air_vent_m1")
                    .factory(p -> new TrainSlabBlock(p, true))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> AIR_VENT_CARPORT_M1_2 =
        new PortRegistration<TrainSlabBlock>("carport_air_vent_m1_2")
                    .factory(p -> new TrainSlabBlock(p, true))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> MIDDLE_CARPORT_M1 =
        new PortRegistration<TrainSlabBlock>("carport_middle_m1")
                    .factory(p -> new TrainSlabBlock(p, true))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainOpenableWindowBlock> WINDOW_LARGE_JY30 =
        new PortRegistration<TrainOpenableWindowBlock>("window_large_jy30")
                    .factory(p -> new TrainOpenableWindowBlock(p, -1, 1, 2))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSmallWindowBlock> WINDOW_LARGE_BETWEEN_JY30 =
        new PortRegistration<TrainSmallWindowBlock>("window_large_between_jy30")
                    .factory(p -> new TrainSmallWindowBlock(p, 2))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainPanelBlock> BOTTOM_SLAB_JY30 =
        new PortRegistration<TrainPanelBlock>("slab_bottom_jy30")
                    .factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<HingeSlabBlock> FLOOR_BATTERY_M1 =
        new PortRegistration<HingeSlabBlock>("floor_battery_m1")
                    .factory(p -> new HingeSlabBlock(p, true))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> FLOOR_RESERVOIR_BOX_M1 =
        new PortRegistration<TrainSlabBlock>("floor_reservoir_box_m1")
                    .factory(p -> new TrainSlabBlock(p, true))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainUnOpenableWindowBlock> SLAB_TOP_M1 =
        new PortRegistration<TrainUnOpenableWindowBlock>("slab_top_m1")
                    .factory(p -> new TrainUnOpenableWindowBlock(p, 2, 0, 2))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();
}

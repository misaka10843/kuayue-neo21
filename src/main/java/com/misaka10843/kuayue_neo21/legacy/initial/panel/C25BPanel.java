package com.misaka10843.kuayue_neo21.legacy.initial.panel;

import net.createmod.catnip.data.Couple;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import com.misaka10843.kuayue_neo21.legacy.block.panels.*;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.TrainPanelProperties;
import com.misaka10843.kuayue_neo21.legacy.block.panels.door.CustomRenderedDoorBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.end_face.CustomRenderedEndfaceBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.TrainLadderBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.TrainSlabBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.window.TrainOpenableWindowBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.window.TrainSmallWindowBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.window.TrainUnOpenableWindowBlock;
import com.misaka10843.kuayue_neo21.content.PortRegistration;

public final class C25BPanel {
    public static final PortRegistration<TrainPanelBlock> PANEL_BOTTOM_25B =
        new PortRegistration<TrainPanelBlock>("panel_bottom_25b")
                    .factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainPanelBlock> PANEL_MIDDLE_25B =
        new PortRegistration<TrainPanelBlock>("panel_middle_25b")
                    .factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainHingePanelBlock> PANEL_SYMBOL_MARSHALLED_25B =
        new PortRegistration<TrainHingePanelBlock>("panel_symbol_marshalled_25b")
                    .factory(p -> new TrainHingePanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f).strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainPanelBlock> PANEL_TOP_25B =
        new PortRegistration<TrainPanelBlock>("panel_top_25b")
                    .factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainPanelBlock> PANEL_TOP_25B_2 =
        new PortRegistration<TrainPanelBlock>("panel_top_25b_2")
                    .factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSmallWindowBlock> WINDOW_OC_25B =
        new PortRegistration<TrainSmallWindowBlock>("window_oc_25b")
                    .factory(TrainSmallWindowBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSmallWindowBlock> WINDOW_OC_SMALL_25B =
        new PortRegistration<TrainSmallWindowBlock>("window_oc_small_25b")
                    .factory(TrainSmallWindowBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSmallWindowBlock> WINDOW_OC_TOILET_25B =
        new PortRegistration<TrainSmallWindowBlock>("window_oc_toilet_25b")
                    .factory(TrainSmallWindowBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainOpenableWindowBlock> WINDOW_OC_WIDE_25B =
        new PortRegistration<TrainOpenableWindowBlock>("window_oc_wide_25b")
                    .factory(p -> new TrainOpenableWindowBlock(p, 2))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainUnOpenableWindowBlock> WINDOW_OC_WIDE_SEALED_25B =
        new PortRegistration<TrainUnOpenableWindowBlock>("window_oc_wide_sealed_25b")
                    .factory(p -> new TrainUnOpenableWindowBlock(p, 2))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<CustomRenderedDoorBlock> DOOR_25B =
        new PortRegistration<CustomRenderedDoorBlock>("door_25b")
                    .factory(p -> new CustomRenderedDoorBlock(p,
                            Couple.create(
                                    com.misaka10843.kuayue_neo21.content.PortRegistration.resource("door/original_25b_door_bottom_hinge"),
                                    com.misaka10843.kuayue_neo21.content.PortRegistration.resource("door/original_25b_door_top_hinge")
                            ), Couple.create(
                                    com.misaka10843.kuayue_neo21.content.PortRegistration.resource("door/original_25b_door_bottom"),
                                    com.misaka10843.kuayue_neo21.content.PortRegistration.resource("door/original_25b_door_top")
                    ), new Vec3(0, 0, 0), new Vec3(0, 0, -.124),RenderShape.MODEL, false
                    ))
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    
                    .submit();

    public static final PortRegistration<CustomRenderedEndfaceBlock> END_FACE_25B_1 =
        new PortRegistration<CustomRenderedEndfaceBlock>("end_face_25b_1")
                    .factory(properties ->
                            new CustomRenderedEndfaceBlock(
                                    properties, TrainPanelProperties.DoorType.ROTATE_SINGLE_SIDED,
                                    "carriage/carriage25b/end_face/end_face_door_original_25b",
                                    null,
                                    "carriage/carriage25b/end_face/end_face_original_25b_1"
                            )
                    )
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<CustomRenderedEndfaceBlock> END_FACE_25B_2 =
        new PortRegistration<CustomRenderedEndfaceBlock>("end_face_25b_2")
                    .factory(properties ->
                            new CustomRenderedEndfaceBlock(
                                    properties, TrainPanelProperties.DoorType.ROTATE_SINGLE_SIDED,
                                    "carriage/carriage25b/end_face/end_face_door_original_25b",
                                    null,
                                    "carriage/carriage25b/end_face/end_face_original_25b_2"
                            )
                    )
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<CustomRenderedEndfaceBlock> END_FACE_25B_3 =
        new PortRegistration<CustomRenderedEndfaceBlock>("end_face_25b_3")
                    .factory(properties ->
                            new CustomRenderedEndfaceBlock(
                                    properties, TrainPanelProperties.DoorType.ROTATE_SINGLE_SIDED,
                                    "carriage/carriage25b/end_face/end_face_door_original_25b",
                                    null,
                                    "carriage/carriage25b/end_face/end_face_original_25b_3"
                            )
                    )
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> FLOOR_25B =
        new PortRegistration<TrainSlabBlock>("floor_25b")
                    .factory(p -> new TrainSlabBlock(p, false))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> TOILET_DD_25B =
        new PortRegistration<TrainSlabBlock>("toilet_dd_25b")
                    .factory(p -> new TrainSlabBlock(p, false))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainLadderBlock> LADDER_25B =
        new PortRegistration<TrainLadderBlock>("ladder_25b")
                    .factory(TrainLadderBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();
}

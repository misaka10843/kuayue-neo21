package com.misaka10843.kuayue_neo21.legacy.initial.panel;

import net.createmod.catnip.data.Couple;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainHingePanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainPanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.TrainPanelProperties;
import com.misaka10843.kuayue_neo21.legacy.block.panels.door.CustomRenderedDoorBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.end_face.CustomRenderedEndfaceBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.TrainLadderBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.TrainSlabBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.window.TrainOpenableWindowBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.window.TrainSmallWindowBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.window.TrainUnOpenableSmallWindowBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.window.TrainUnOpenableWindowBlock;
import com.misaka10843.kuayue_neo21.content.PortRegistration;

public final class C25KPanel {
    public static final PortRegistration<CustomRenderedDoorBlock> DOOR_25K =
        new PortRegistration<CustomRenderedDoorBlock>("door_25k")
                    .factory(p -> new CustomRenderedDoorBlock(p,
                            Couple.create(
                                    ResourceLocation.fromNamespaceAndPath(com.misaka10843.kuayue_neo21.KuaYueneo21.MODID, "door/original_25k_door_bottom_hinge"),
                                    ResourceLocation.fromNamespaceAndPath(com.misaka10843.kuayue_neo21.KuaYueneo21.MODID, "door/original_25k_door_top_hinge")
                            ), Couple.create(
                            ResourceLocation.fromNamespaceAndPath(com.misaka10843.kuayue_neo21.KuaYueneo21.MODID, "door/original_25k_door_bottom"),
                            ResourceLocation.fromNamespaceAndPath(com.misaka10843.kuayue_neo21.KuaYueneo21.MODID, "door/original_25k_door_top")
                    ), new Vec3(0, 0, 0), new Vec3(0, 0, -.124),RenderShape.ENTITYBLOCK_ANIMATED, false))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<CustomRenderedDoorBlock> DOOR_SLIDING_25K =
        new PortRegistration<CustomRenderedDoorBlock>("door_sliding_25k")
                    .factory(properties ->
                            new CustomRenderedDoorBlock(properties, Couple.create(
                                    com.misaka10843.kuayue_neo21.content.PortRegistration.resource("door/sliding_door_25k_bottom"),
                                    com.misaka10843.kuayue_neo21.content.PortRegistration.resource("door/sliding_door_25k_upper"))
                                    , Couple.create(
                                    com.misaka10843.kuayue_neo21.content.PortRegistration.resource("door/sliding_door_25k_bottom_lh"),
                                    com.misaka10843.kuayue_neo21.content.PortRegistration.resource("door/sliding_door_25k_upper_lh")),
                                    RenderShape.ENTITYBLOCK_ANIMATED, true))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<CustomRenderedEndfaceBlock> END_FACE_25K_1 =
        new PortRegistration<CustomRenderedEndfaceBlock>("end_face_25k_1")
                    .factory(properties ->
                            new CustomRenderedEndfaceBlock(
                                    properties, TrainPanelProperties.DoorType.ROTATE_SINGLE_SIDED,
                                    "carriage/carriage25k/end_face/end_face_door_original_25k",
                                    null,
                                    "carriage/carriage25k/end_face/end_face_original_25k_1"
                            )
                    )
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<CustomRenderedEndfaceBlock> END_FACE_25K_2 =
        new PortRegistration<CustomRenderedEndfaceBlock>("end_face_25k_2")
                    .factory(properties ->
                            new CustomRenderedEndfaceBlock(
                                    properties, TrainPanelProperties.DoorType.ROTATE_SINGLE_SIDED,
                                    "carriage/carriage25k/end_face/end_face_door_original_25k",
                                    null,
                                    "carriage/carriage25k/end_face/end_face_original_25k_2"
                            )
                    )
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<CustomRenderedEndfaceBlock> END_FACE_25K_3 =
        new PortRegistration<CustomRenderedEndfaceBlock>("end_face_25k_3")
                    .factory(properties ->
                            new CustomRenderedEndfaceBlock(
                                    properties, TrainPanelProperties.DoorType.ROTATE_SINGLE_SIDED,
                                    "carriage/carriage25k/end_face/end_face_door_original_25k",
                                    null,
                                    "carriage/carriage25k/end_face/end_face_original_25k_3"
                            )
                    )
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<CustomRenderedEndfaceBlock> END_FACE_SLIDING_25K_1 =
        new PortRegistration<CustomRenderedEndfaceBlock>("end_face_sliding_25k_1")
                    .factory(properties ->
                            new CustomRenderedEndfaceBlock(
                                    properties, TrainPanelProperties.DoorType.SLIDE_2,
                                    "carriage/carriage25k/end_face/end_face_sliding_door_original_25k_right",
                                    "carriage/carriage25k/end_face/end_face_sliding_door_original_25k_left",
                                    "carriage/carriage25k/end_face/end_face_sliding_original_25k_1"
                            )
                    )
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<CustomRenderedEndfaceBlock> END_FACE_SLIDING_25K_2 =
        new PortRegistration<CustomRenderedEndfaceBlock>("end_face_sliding_25k_2")
                    .factory(properties ->
                            new CustomRenderedEndfaceBlock(
                                    properties, TrainPanelProperties.DoorType.SLIDE_2,
                                    "carriage/carriage25k/end_face/end_face_sliding_door_original_25k_right",
                                    "carriage/carriage25k/end_face/end_face_sliding_door_original_25k_left",
                                    "carriage/carriage25k/end_face/end_face_sliding_original_25k_2"
                            )
                    )
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<CustomRenderedEndfaceBlock> END_FACE_SLIDING_25K_3 =
        new PortRegistration<CustomRenderedEndfaceBlock>("end_face_sliding_25k_3")
                    .factory(properties ->
                            new CustomRenderedEndfaceBlock(
                                    properties, TrainPanelProperties.DoorType.SLIDE_2,
                                    "carriage/carriage25k/end_face/end_face_sliding_door_original_25k_right",
                                    "carriage/carriage25k/end_face/end_face_sliding_door_original_25k_left",
                                    "carriage/carriage25k/end_face/end_face_sliding_original_25k_3"
                            )
                    )
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainPanelBlock> PANEL_BOTTOM_25K =
        new PortRegistration<TrainPanelBlock>("panel_bottom_25k")
                    .factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainPanelBlock> PANEL_BOTTOM_LINE_25K =
        new PortRegistration<TrainPanelBlock>("panel_bottom_line_25k")
                    .factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainPanelBlock> PANEL_MIDDLE_25K =
        new PortRegistration<TrainPanelBlock>("panel_middle_25k")
                    .factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainHingePanelBlock> PANEL_SYMBOL_25K =
        new PortRegistration<TrainHingePanelBlock>("panel_symbol_25k")
                    .factory(p -> new TrainHingePanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainHingePanelBlock> PANEL_SYMBOL_MARSHALLED_25K =
        new PortRegistration<TrainHingePanelBlock>("panel_symbol_marshalled_25k")
                    .factory(p -> new TrainHingePanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainHingePanelBlock> PANEL_SYMBOL_MARSHALLED_25K_B =
        new PortRegistration<TrainHingePanelBlock>("panel_symbol_marshalled_25k_b")
                    .factory(p -> new TrainHingePanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainPanelBlock> PANEL_TOP_25K =
        new PortRegistration<TrainPanelBlock>("panel_top_25k")
                    .factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainUnOpenableSmallWindowBlock> WINDOW_OC_SEALED_SMALL_25K =
        new PortRegistration<TrainUnOpenableSmallWindowBlock>("window_oc_sealed_small_25k")
                    .factory(TrainUnOpenableSmallWindowBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSmallWindowBlock> WINDOW_OC_SMALL_25K =
        new PortRegistration<TrainSmallWindowBlock>("window_oc_small_25k")
                    .factory(TrainSmallWindowBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSmallWindowBlock> WINDOW_OC_TOILET_25K =
        new PortRegistration<TrainSmallWindowBlock>("window_oc_toilet_25k")
                    .factory(TrainSmallWindowBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainOpenableWindowBlock> WINDOW_OC_WIDE_25K =
        new PortRegistration<TrainOpenableWindowBlock>("window_oc_wide_25k")
                    .factory(p -> new TrainOpenableWindowBlock(p, 2))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainUnOpenableWindowBlock> WINDOW_OC_WIDE_SEALED_25K =
        new PortRegistration<TrainUnOpenableWindowBlock>("window_oc_wide_sealed_25k")
                    .factory(p -> new TrainUnOpenableWindowBlock(p, 2))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> FLOOR_25K =
        new PortRegistration<TrainSlabBlock>("floor_25k")
                    .factory(p -> new TrainSlabBlock(p, false))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> TOILET_DD_25K =
        new PortRegistration<TrainSlabBlock>("toilet_dd_25k")
                    .factory(p -> new TrainSlabBlock(p, false))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainLadderBlock> LADDER_SLIDING_25K =
        new PortRegistration<TrainLadderBlock>("ladder_sliding_25k")
                    .factory(TrainLadderBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();
}

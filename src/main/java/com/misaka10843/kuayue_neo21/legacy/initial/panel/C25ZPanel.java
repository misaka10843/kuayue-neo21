package com.misaka10843.kuayue_neo21.legacy.initial.panel;

import net.createmod.catnip.data.Couple;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import com.misaka10843.kuayue_neo21.legacy.block.panels.LevelPanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainHingePanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainPanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.TrainPanelProperties;
import com.misaka10843.kuayue_neo21.legacy.block.panels.door.CustomRenderedDoorBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.end_face.CustomRenderedEndfaceBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.HingeSlabBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.TrainLadderBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.TrainSlabBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.window.LevelWindowBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.window.TrainOpenableWindowBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.window.TrainSmallWindowBlock;
import com.misaka10843.kuayue_neo21.content.PortRegistration;

public final class C25ZPanel {
    public static final PortRegistration<CustomRenderedDoorBlock> DOOR_25Z =
        new PortRegistration<CustomRenderedDoorBlock>("door_25z")
                    .factory(p -> new CustomRenderedDoorBlock(p,
                            Couple.create(
                                    ResourceLocation.fromNamespaceAndPath(com.misaka10843.kuayue_neo21.KuaYueneo21.MODID, "carriage/carriage25z/door/original_25z_door_bottom_hinge"),
                                    ResourceLocation.fromNamespaceAndPath(com.misaka10843.kuayue_neo21.KuaYueneo21.MODID, "carriage/carriage25z/door/original_25z_door_top_hinge")
                            ), Couple.create(
                            ResourceLocation.fromNamespaceAndPath(com.misaka10843.kuayue_neo21.KuaYueneo21.MODID, "carriage/carriage25z/door/original_25z_door_bottom"),
                            ResourceLocation.fromNamespaceAndPath(com.misaka10843.kuayue_neo21.KuaYueneo21.MODID, "carriage/carriage25z/door/original_25z_door_top")
                    ), new Vec3(0, 0, 0), new Vec3(0, 0, -.07),RenderShape.ENTITYBLOCK_ANIMATED, false))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<CustomRenderedEndfaceBlock> END_FACE_25Z_1 =
        new PortRegistration<CustomRenderedEndfaceBlock>("end_face_25z_1")
                    .factory(properties ->
                            new CustomRenderedEndfaceBlock(
                                    properties, TrainPanelProperties.DoorType.SLIDE_3,
                                    "carriage/carriage25z/end_face/end_face_door_sliding_original_25z_right",
                                    "carriage/carriage25z/end_face/end_face_door_sliding_original_25z_left",
                                    "carriage/carriage25z/end_face/end_face_original_25z_1"
                            )
                    )
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<CustomRenderedEndfaceBlock> END_FACE_25Z_2 =
        new PortRegistration<CustomRenderedEndfaceBlock>("end_face_25z_2")
                    .factory(properties ->
                            new CustomRenderedEndfaceBlock(
                                    properties, TrainPanelProperties.DoorType.SLIDE_3,
                                    "carriage/carriage25z/end_face/end_face_door_sliding_original_25z_right",
                                    "carriage/carriage25z/end_face/end_face_door_sliding_original_25z_left",
                                    "carriage/carriage25z/end_face/end_face_original_25z_2"
                            )
                    )
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<CustomRenderedEndfaceBlock> END_FACE_25Z_3 =
        new PortRegistration<CustomRenderedEndfaceBlock>("end_face_25z_3")
                    .factory(properties ->
                            new CustomRenderedEndfaceBlock(
                                    properties, TrainPanelProperties.DoorType.SLIDE_3,
                                    "carriage/carriage25z/end_face/end_face_door_sliding_original_25z_right",
                                    "carriage/carriage25z/end_face/end_face_door_sliding_original_25z_left",
                                    "carriage/carriage25z/end_face/end_face_original_25z_3"
                            )
                    )
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainPanelBlock> PANEL_BOTTOM_LINE_25Z =
        new PortRegistration<TrainPanelBlock>("panel_bottom_line_25z")
                    .factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainPanelBlock> PANEL_BOTTOM_SIDE_25Z =
        new PortRegistration<TrainPanelBlock>("panel_bottom_side_25z")
                    .factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainPanelBlock> PANEL_BOTTOM_SYMBOL_25Z =
        new PortRegistration<TrainPanelBlock>("panel_bottom_symbol_25z")
                    .factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<LevelPanelBlock> PANEL_MIDDLE_25Z =
        new PortRegistration<LevelPanelBlock>("panel_middle_25z")
                    .factory(p -> new LevelPanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainHingePanelBlock> PANEL_MIDDLE_SIDE_25Z =
        new PortRegistration<TrainHingePanelBlock>("panel_middle_side_25z")
                    .factory(p -> new TrainHingePanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainPanelBlock> PANEL_TOP_25Z =
        new PortRegistration<TrainPanelBlock>("panel_top_25z")
                    .factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSmallWindowBlock> WINDOW_OC_25Z =
        new PortRegistration<TrainSmallWindowBlock>("window_oc_25z")
                    .factory(TrainSmallWindowBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<LevelWindowBlock> WINDOW_OC_LEVEL_25Z =
        new PortRegistration<LevelWindowBlock>("window_oc_level_25z")
                    .factory(LevelWindowBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSmallWindowBlock> WINDOW_OC_SMALL_25Z_2 =
        new PortRegistration<TrainSmallWindowBlock>("window_oc_small_25z_2")
                    .factory(TrainSmallWindowBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSmallWindowBlock> WINDOW_OC_TOILET_25Z =
        new PortRegistration<TrainSmallWindowBlock>("window_oc_toilet_25z")
                    .factory(TrainSmallWindowBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainOpenableWindowBlock> WINDOW_OC_WIDE_25Z =
        new PortRegistration<TrainOpenableWindowBlock>("window_oc_wide_25z")
                    .factory(p -> new TrainOpenableWindowBlock(p, 2))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> FLOOR_25Z =
        new PortRegistration<TrainSlabBlock>("floor_25z")
                    .factory(p -> new TrainSlabBlock(p, false))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> TOILET_DD_25Z =
        new PortRegistration<TrainSlabBlock>("toilet_dd_25z")
                    .factory(p -> new TrainSlabBlock(p, false))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainLadderBlock> LADDER_25Z =
        new PortRegistration<TrainLadderBlock>("ladder_25z")
                    .factory(TrainLadderBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> CARPORT_25Z =
        new PortRegistration<TrainSlabBlock>("carport_25z")
                    .factory(p -> new TrainSlabBlock(p, true))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<HingeSlabBlock> CARPORT_AC_25Z =
        new PortRegistration<HingeSlabBlock>("carport_ac_25z")
                    .factory(p -> new HingeSlabBlock(p, true))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> CARPORT_CENTER_25Z =
        new PortRegistration<TrainSlabBlock>("carport_center_25z")
                    .factory(p -> new TrainSlabBlock(p, true))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<HingeSlabBlock> CARPORT_SIDE_AC_25Z =
        new PortRegistration<HingeSlabBlock>("carport_side_ac_25z")
                    .factory(p -> new HingeSlabBlock(p, true))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();
}

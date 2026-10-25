package com.misaka10843.kuayue_neo21.legacy.initial.panel;

import net.createmod.catnip.data.Couple;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import com.misaka10843.kuayue_neo21.legacy.block.panels.SkirtBlock;
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

public final class C25TPanel {
    public static final PortRegistration<CustomRenderedDoorBlock> DOOR_25T =
        new PortRegistration<CustomRenderedDoorBlock>("door_25t")
                    .factory(p -> new CustomRenderedDoorBlock(p,
                            Couple.create(
                                    ResourceLocation.fromNamespaceAndPath(com.misaka10843.kuayue_neo21.KuaYueneo21.MODID, "door/original_25t_door_bottom_hinge"),
                                    ResourceLocation.fromNamespaceAndPath(com.misaka10843.kuayue_neo21.KuaYueneo21.MODID, "door/original_25t_door_top_hinge")
                            ), Couple.create(
                                    ResourceLocation.fromNamespaceAndPath(com.misaka10843.kuayue_neo21.KuaYueneo21.MODID, "door/original_25t_door_bottom"),
                                    ResourceLocation.fromNamespaceAndPath(com.misaka10843.kuayue_neo21.KuaYueneo21.MODID, "door/original_25t_door_top")
                    ), new Vec3(0, 0, 0), new Vec3(0, 0, -.124),RenderShape.ENTITYBLOCK_ANIMATED, false))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<CustomRenderedDoorBlock> DOOR_SLIDING_25T =
        new PortRegistration<CustomRenderedDoorBlock>("door_sliding_25t")
                    .factory(p -> new CustomRenderedDoorBlock(p,
                            Couple.create(
                                    ResourceLocation.fromNamespaceAndPath(com.misaka10843.kuayue_neo21.KuaYueneo21.MODID, "door/sliding_door_25t_bottom"),
                                    ResourceLocation.fromNamespaceAndPath(com.misaka10843.kuayue_neo21.KuaYueneo21.MODID, "door/sliding_door_25t_upper")
                            ), Couple.create(
                                    ResourceLocation.fromNamespaceAndPath(com.misaka10843.kuayue_neo21.KuaYueneo21.MODID, "door/sliding_door_25t_bottom_lh"),
                                    ResourceLocation.fromNamespaceAndPath(com.misaka10843.kuayue_neo21.KuaYueneo21.MODID, "door/sliding_door_25t_upper_lh")
                    ), RenderShape.ENTITYBLOCK_ANIMATED, true))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<CustomRenderedEndfaceBlock> END_FACE_25T_1 =
        new PortRegistration<CustomRenderedEndfaceBlock>("end_face_25t_1")
                    .factory(properties ->
                            new CustomRenderedEndfaceBlock(
                                    properties, TrainPanelProperties.DoorType.ROTATE_SINGLE_SIDED,
                                    "carriage/carriage25t/end_face/end_face_door_original_25t",
                                    null,
                                    "carriage/carriage25t/end_face/end_face_original_rubber_25t_1"
                            )
                    )
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<CustomRenderedEndfaceBlock> END_FACE_25T_2 =
        new PortRegistration<CustomRenderedEndfaceBlock>("end_face_25t_2")
                    .factory(properties ->
                            new CustomRenderedEndfaceBlock(
                                    properties, TrainPanelProperties.DoorType.ROTATE_SINGLE_SIDED,
                                    "carriage/carriage25t/end_face/end_face_door_original_25t",
                                    null,
                                    "carriage/carriage25t/end_face/end_face_original_rubber_25t_2"
                            )
                    )
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<CustomRenderedEndfaceBlock> END_FACE_25T_3 =
        new PortRegistration<CustomRenderedEndfaceBlock>("end_face_25t_3")
                    .factory(properties ->
                            new CustomRenderedEndfaceBlock(
                                    properties, TrainPanelProperties.DoorType.ROTATE_SINGLE_SIDED,
                                    "carriage/carriage25t/end_face/end_face_door_original_25t",
                                    null,
                                    "carriage/carriage25t/end_face/end_face_original_rubber_25t_3"
                            )
                    )
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<CustomRenderedEndfaceBlock> END_FACE_SLIDING_25T_1 =
        new PortRegistration<CustomRenderedEndfaceBlock>("end_face_sliding_25t_1")
                    .factory(properties ->
                            new CustomRenderedEndfaceBlock(
                                    properties, TrainPanelProperties.DoorType.SLIDE_2,
                                    "carriage/carriage25t/end_face/end_face_door_sliding_original_25t_right",
                                    "carriage/carriage25t/end_face/end_face_door_sliding_original_25t_left",
                                    "carriage/carriage25t/end_face/end_face_original_25t_1"
                            )
                    )
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<CustomRenderedEndfaceBlock> END_FACE_SLIDING_25T_2 =
        new PortRegistration<CustomRenderedEndfaceBlock>("end_face_sliding_25t_2")
                    .factory(properties ->
                            new CustomRenderedEndfaceBlock(
                                    properties, TrainPanelProperties.DoorType.SLIDE_2,
                                    "carriage/carriage25t/end_face/end_face_door_sliding_original_25t_right",
                                    "carriage/carriage25t/end_face/end_face_door_sliding_original_25t_left",
                                    "carriage/carriage25t/end_face/end_face_original_25t_2"
                            )
                    )
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<CustomRenderedEndfaceBlock> END_FACE_SLIDING_25T_3 =
        new PortRegistration<CustomRenderedEndfaceBlock>("end_face_sliding_25t_3")
                    .factory(properties ->
                            new CustomRenderedEndfaceBlock(
                                    properties, TrainPanelProperties.DoorType.SLIDE_2,
                                    "carriage/carriage25t/end_face/end_face_door_sliding_original_25t_right",
                                    "carriage/carriage25t/end_face/end_face_door_sliding_original_25t_left",
                                    "carriage/carriage25t/end_face/end_face_original_25t_3"
                            )
                    )
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainPanelBlock> PANEL_BOTTOM_25T =
        new PortRegistration<TrainPanelBlock>("panel_bottom_25t")
                    .factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainHingePanelBlock> PANEL_BOTTOM_25T_2 =
        new PortRegistration<TrainHingePanelBlock>("panel_bottom_25t_2")
                    .factory(p -> new TrainHingePanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainPanelBlock> PANEL_MIDDLE_25T =
        new PortRegistration<TrainPanelBlock>("panel_middle_25t")
                    .factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainHingePanelBlock> PANEL_MIDDLE_25T_2 =
        new PortRegistration<TrainHingePanelBlock>("panel_middle_25t_2")
                    .factory(p -> new TrainHingePanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainPanelBlock> PANEL_TOP_25T =
        new PortRegistration<TrainPanelBlock>("panel_top_25t")
                    .factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainHingePanelBlock> PANEL_TOP_25T_2 =
        new PortRegistration<TrainHingePanelBlock>("panel_top_25t_2")
                    .factory(p -> new TrainHingePanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainPanelBlock> PANEL_TOP_XL25T =
        new PortRegistration<TrainPanelBlock>("panel_top_xl25t")
                    .factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSmallWindowBlock> WINDOW_OC_BLUE_25T =
        new PortRegistration<TrainSmallWindowBlock>("window_oc_blue_25t")
                    .factory(TrainSmallWindowBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainUnOpenableSmallWindowBlock> WINDOW_OC_SEALED_BLUE_25T =
        new PortRegistration<TrainUnOpenableSmallWindowBlock>("window_oc_sealed_blue_25t")
                    .factory(TrainUnOpenableSmallWindowBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainUnOpenableSmallWindowBlock> WINDOW_OC_SEALED_SMALL_25T =
        new PortRegistration<TrainUnOpenableSmallWindowBlock>("window_oc_sealed_small_25t")
                    .factory(TrainUnOpenableSmallWindowBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainUnOpenableSmallWindowBlock> WINDOW_OC_SEALED_SMALL_BLUE_25T =
        new PortRegistration<TrainUnOpenableSmallWindowBlock>("window_oc_sealed_small_blue_25t")
                    .factory(TrainUnOpenableSmallWindowBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainUnOpenableSmallWindowBlock> WINDOW_OC_SEALED_SMALL_BLUE_BSP25T =
        new PortRegistration<TrainUnOpenableSmallWindowBlock>("window_oc_sealed_small_blue_bsp25t")
                    .factory(TrainUnOpenableSmallWindowBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainUnOpenableWindowBlock> WINDOW_OC_SEALED_WIDE_BLUE_BSP25T =
        new PortRegistration<TrainUnOpenableWindowBlock>("window_oc_sealed_wide_blue_bsp25t")
                    .factory(p -> new TrainUnOpenableWindowBlock(p, 2))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSmallWindowBlock> WINDOW_OC_SMALL_25T =
        new PortRegistration<TrainSmallWindowBlock>("window_oc_small_25t")
                    .factory(TrainSmallWindowBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSmallWindowBlock> WINDOW_OC_SMALL_BLUE_25T =
        new PortRegistration<TrainSmallWindowBlock>("window_oc_small_blue_25t")
                    .factory(TrainSmallWindowBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSmallWindowBlock> WINDOW_OC_SMALL_BLUE_BSP25T =
        new PortRegistration<TrainSmallWindowBlock>("window_oc_small_blue_bsp25t")
                    .factory(TrainSmallWindowBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSmallWindowBlock> WINDOW_OC_TOILET_25T =
        new PortRegistration<TrainSmallWindowBlock>("window_oc_toilet_25t")
                    .factory(TrainSmallWindowBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSmallWindowBlock> WINDOW_OC_TOILET_BLUE_25T =
        new PortRegistration<TrainSmallWindowBlock>("window_oc_toilet_blue_25t")
                    .factory(TrainSmallWindowBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSmallWindowBlock> WINDOW_OC_TOILET_BLUE_BSP25T =
        new PortRegistration<TrainSmallWindowBlock>("window_oc_toilet_blue_bsp25t")
                    .factory(TrainSmallWindowBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainOpenableWindowBlock> WINDOW_OC_WIDE_25T =
        new PortRegistration<TrainOpenableWindowBlock>("window_oc_wide_25t")
                    .factory(p -> new TrainOpenableWindowBlock(p, 2))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainOpenableWindowBlock> WINDOW_OC_WIDE_BLUE_25T =
        new PortRegistration<TrainOpenableWindowBlock>("window_oc_wide_blue_25t")
                    .factory(p -> new TrainOpenableWindowBlock(p, 2))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainOpenableWindowBlock> WINDOW_OC_WIDE_BLUE_BSP25T =
        new PortRegistration<TrainOpenableWindowBlock>("window_oc_wide_blue_bsp25t")
                    .factory(p -> new TrainOpenableWindowBlock(p, 2))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainUnOpenableWindowBlock> WINDOW_OC_WIDE_SEALED_25T =
        new PortRegistration<TrainUnOpenableWindowBlock>("window_oc_wide_sealed_25t")
                    .factory(p -> new TrainUnOpenableWindowBlock(p, 2))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainUnOpenableWindowBlock> WINDOW_OC_WIDE_SEALED_BLUE_25T =
        new PortRegistration<TrainUnOpenableWindowBlock>("window_oc_wide_sealed_blue_25t")
                    .factory(p -> new TrainUnOpenableWindowBlock(p, 2))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<SkirtBlock> SKIRT_25T =
        new PortRegistration<SkirtBlock>("skirt_25t")
                    .factory(SkirtBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<SkirtBlock> SKIRT_MARSHALLED_25T =
        new PortRegistration<SkirtBlock>("skirt_marshalled_25t")
                    .factory(SkirtBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainLadderBlock> LADDER_SLIDING_25T =
        new PortRegistration<TrainLadderBlock>("ladder_sliding_25t")
                    .factory(TrainLadderBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> FLOOR_25T =
        new PortRegistration<TrainSlabBlock>("floor_25t")
                    .factory(p -> new TrainSlabBlock(p, false))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> TOILET_DD_25T =
        new PortRegistration<TrainSlabBlock>("toilet_dd_25t")
                    .factory(p -> new TrainSlabBlock(p, false))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> CARPORT_25T =
        new PortRegistration<TrainSlabBlock>("carport_25t")
                    .factory(p -> new TrainSlabBlock(p, true))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> CARPORT_CENTER_NSP25T =
        new PortRegistration<TrainSlabBlock>("carport_center_bsp25t")
                    .factory(p -> new TrainSlabBlock(p, true))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> CARPORT_TOILET_25T =
        new PortRegistration<TrainSlabBlock>("carport_toilet_25t")
                    .factory(p -> new TrainSlabBlock(p, true))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> AIR_CONDITION_BSP25T =
        new PortRegistration<TrainSlabBlock>("air_condition_bsp25t")
                    .factory(p -> new TrainSlabBlock(p, true))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();
}

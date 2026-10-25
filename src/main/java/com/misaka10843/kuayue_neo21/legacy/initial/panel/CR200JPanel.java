package com.misaka10843.kuayue_neo21.legacy.initial.panel;

import net.createmod.catnip.data.Couple;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.Vec2;
import com.misaka10843.kuayue_neo21.legacy.block.panels.*;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.TrainPanelProperties;
import com.misaka10843.kuayue_neo21.legacy.block.panels.block_entity.CR200jFrontBlockEntity;
import com.misaka10843.kuayue_neo21.legacy.block.panels.cr200j.CR200jFrontBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.door.CustomRenderedDoorBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.end_face.CustomRenderedEndfaceBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.TrainLadderBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.TrainSlabBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.window.TrainOpenableWindowBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.window.TrainSmallWindowBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.window.TrainUnOpenableSmallWindowBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.window.TrainUnOpenableWindowBlock;
import com.misaka10843.kuayue_neo21.content.PortRegistration;

public final class CR200JPanel {
    public static final PortRegistration<CustomRenderedDoorBlock> DOOR_CR200J =
        new PortRegistration<CustomRenderedDoorBlock>("door_marshalled_cr200j")
                    .factory(properties ->
                            new CustomRenderedDoorBlock(properties, Couple.create(
                                    com.misaka10843.kuayue_neo21.content.PortRegistration.resource("carriage/carriage_marshalled_cr200j/door/door_bottom_lh"),
                                    com.misaka10843.kuayue_neo21.content.PortRegistration.resource("carriage/carriage_marshalled_cr200j/door/door_top_lh"))
                            , Couple.create(
                                    com.misaka10843.kuayue_neo21.content.PortRegistration.resource("carriage/carriage_marshalled_cr200j/door/door_bottom"),
                                    com.misaka10843.kuayue_neo21.content.PortRegistration.resource("carriage/carriage_marshalled_cr200j/door/door_top")),
                            RenderShape.ENTITYBLOCK_ANIMATED, false))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<CustomRenderedDoorBlock> DOOR_CR200J_2 =
        new PortRegistration<CustomRenderedDoorBlock>("door_marshalled_cr200j_2")
                    .factory(properties ->
                            new CustomRenderedDoorBlock(properties, Couple.create(
                                    com.misaka10843.kuayue_neo21.content.PortRegistration.resource("carriage/carriage_marshalled_cr200j/door/door_bottom_lh_2"),
                                    com.misaka10843.kuayue_neo21.content.PortRegistration.resource("carriage/carriage_marshalled_cr200j/door/door_top_lh"))
                                    , Couple.create(
                                    com.misaka10843.kuayue_neo21.content.PortRegistration.resource("carriage/carriage_marshalled_cr200j/door/door_bottom_2"),
                                    com.misaka10843.kuayue_neo21.content.PortRegistration.resource("carriage/carriage_marshalled_cr200j/door/door_top")),
                                    RenderShape.ENTITYBLOCK_ANIMATED, true))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<CustomRenderedDoorBlock> DOOR_CABIN_MARSHALLED_CR200J =
        new PortRegistration<CustomRenderedDoorBlock>("door_cabin_marshalled_cr200j")
                    .factory(properties ->
                            new CustomRenderedDoorBlock(properties, Couple.create(
                                    com.misaka10843.kuayue_neo21.content.PortRegistration.resource("carriage/carriage_marshalled_cr200j/door/door_bottom_lh"),
                                    com.misaka10843.kuayue_neo21.content.PortRegistration.resource("carriage/carriage_marshalled_cr200j/door/door_top_lh_2"))
                                    , Couple.create(
                                    com.misaka10843.kuayue_neo21.content.PortRegistration.resource("carriage/carriage_marshalled_cr200j/door/door_bottom"),
                                    com.misaka10843.kuayue_neo21.content.PortRegistration.resource("carriage/carriage_marshalled_cr200j/door/door_top_2")),
                                    RenderShape.MODEL, true))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<CustomRenderedEndfaceBlock> END_FACE_MARSHALLED_CR200J =
        new PortRegistration<CustomRenderedEndfaceBlock>("end_face_marshalled_cr200j")
                    .factory(properties ->
                            new CustomRenderedEndfaceBlock(
                                    properties, TrainPanelProperties.DoorType.SLIDE,
                                    "carriage/carriage_marshalled_cr200j/end_face/end_face_door_lh",
                                    "carriage/carriage_marshalled_cr200j/end_face/end_face_door",
                                    "carriage/carriage_marshalled_cr200j/end_face/end_face_frame"
                            )
                    )
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<CustomRenderedEndfaceBlock> END_FACE_MARSHALLED_CR200J_2 =
        new PortRegistration<CustomRenderedEndfaceBlock>("end_face_marshalled_cr200j_2")
                    .factory(properties ->
                            new CustomRenderedEndfaceBlock(
                                    properties, TrainPanelProperties.DoorType.ROTATE,
                                    "carriage/carriage_marshalled_cr200j/end_face/end_face_door_lh",
                                    "carriage/carriage_marshalled_cr200j/end_face/end_face_door",
                                    "carriage/carriage_marshalled_cr200j/end_face/end_face_frame"
                            )
                    )
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainPanelBlock> PANEL_BOTTOM_MARSHALLED_CR200J =
        new PortRegistration<TrainPanelBlock>("panel_bottom_marshalled_cr200j")
            .factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
            
            
            .noOcclusion().strengthAndTool(1.5f, 3f)
            .submit();

    public static final PortRegistration<TrainHingePanelBlock> PANEL_MIDDLE_LOGO_CR_MARSHALLED_CR200J =
        new PortRegistration<TrainHingePanelBlock>("panel_middle_logo_cr_marshalled_cr200j")
            .factory(p -> new TrainHingePanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
            
            
            .noOcclusion().strengthAndTool(1.5f, 3f)
            .submit();

    public static final PortRegistration<TrainUnOpenableSmallWindowBlock> WINDOW_SEALED_MARSHALLED_CR200J =
        new PortRegistration<TrainUnOpenableSmallWindowBlock>("window_sealed_marshalled_cr200j")
                    .factory(TrainUnOpenableSmallWindowBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainHingePanelBlock> PANEL_MIDDLE_MARSHALLED_CR200J =
        new PortRegistration<TrainHingePanelBlock>("panel_middle_marshalled_cr200j")
                    .factory(p -> new TrainHingePanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainPanelBlock> PANEL_TOP_MARSHALLED_CR200J =
        new PortRegistration<TrainPanelBlock>("panel_top_marshalled_cr200j")
                    .factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainHingePanelBlock> PANEL_TOP_MARSHALLED_CR200J_2 =
        new PortRegistration<TrainHingePanelBlock>("panel_top_marshalled_cr200j_2")
                    .factory(p -> new TrainHingePanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainPanelBlock> PANEL_TOP_MARSHALLED_CR200J_3 =
        new PortRegistration<TrainPanelBlock>("panel_top_marshalled_cr200j_3")
                    .factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainHingePanelBlock> PANEL_WIDE_FU_XING_MARSHALLED_CR200J =
        new PortRegistration<TrainHingePanelBlock>("panel_wide_fu_xing_marshalled_cr200j")
                    .factory(p -> new TrainHingePanelBlock(p, new Vec2(-1, 0), new Vec2(2, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainHingePanelBlock> PANEL_WIDE_LOGO_CR_MARSHALLED_CR200J =
        new PortRegistration<TrainHingePanelBlock>("panel_wide_logo_cr_marshalled_cr200j")
                    .factory(p -> new TrainHingePanelBlock(p, new Vec2(-1, 0), new Vec2(2, 2)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainHingePanelBlock> PANEL_WIDE_MARSHALLED_CR200J =
        new PortRegistration<TrainHingePanelBlock>("panel_wide_marshalled_cr200j")
                    .factory(p -> new TrainHingePanelBlock(p, new Vec2(-1, 0), new Vec2(2, 2)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSmallWindowBlock> WINDOW_TOILET_MARSHALLED_CR200J =
        new PortRegistration<TrainSmallWindowBlock>("window_toilet_marshalled_cr200j")
                    .factory(TrainSmallWindowBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainOpenableWindowBlock> WINDOW_WIDE_MARSHALLED_CR200J =
        new PortRegistration<TrainOpenableWindowBlock>("window_wide_marshalled_cr200j")
                    .factory(p -> new TrainOpenableWindowBlock(p, 2))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainUnOpenableWindowBlock> WINDOW_WIDE_SEALED_MARSHALLED_CR200J =
        new PortRegistration<TrainUnOpenableWindowBlock>("window_wide_sealed_marshalled_cr200j")
                    .factory(p -> new TrainUnOpenableWindowBlock(p, 2))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<SkirtBlock> SKIRT_MARSHALLED_CR200J =
        new PortRegistration<SkirtBlock>("skirt_marshalled_cr200j")
                    .factory(SkirtBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainLadderBlock> LADDER_MARSHALLED_CR200J =
        new PortRegistration<TrainLadderBlock>("ladder_marshalled_cr200j")
                    .factory(TrainLadderBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> FLOOR_MARSHALLED_CR200J =
        new PortRegistration<TrainSlabBlock>("floor_marshalled_cr200j")
                    .factory(properties -> new TrainSlabBlock(properties, false))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> TOILET_DD_MARSHALLED_CR200J =
        new PortRegistration<TrainSlabBlock>("toilet_dd_marshalled_cr200j")
                    .factory(properties -> new TrainSlabBlock(properties, false))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> CARPORT_CENTER_MARSHALLED_CR200J =
        new PortRegistration<TrainSlabBlock>("carport_center_marshalled_cr200j")
                    .factory(properties -> new TrainSlabBlock(properties, true))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> CARPORT_MARSHALLED_CR200J =
        new PortRegistration<TrainSlabBlock>("carport_marshalled_cr200j")
                    .factory(properties -> new TrainSlabBlock(properties, true))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<CR200jFrontBlock> CR200J_FRONT_BLOCK =
        new PortRegistration<CR200jFrontBlock>("head_cr200j")
                    .factory(CR200jFrontBlock::new)
                    
                    
                    
                    .addProperty(BlockBehaviour.Properties::noOcclusion)
                    
                    .submit();
}

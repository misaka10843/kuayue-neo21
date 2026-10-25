package com.misaka10843.kuayue_neo21.legacy.initial.panel;

import net.createmod.catnip.data.Couple;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainHingePanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainPanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.door.CustomRenderedDoorBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.TrainPanelProperties;
import com.misaka10843.kuayue_neo21.legacy.block.panels.carport.AirVentBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.end_face.CustomRenderedEndfaceBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.TrainLadderBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.TrainSlabBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.window.TrainOpenableWindowBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.window.TrainSmallWindowBlock;
import com.misaka10843.kuayue_neo21.content.PortRegistration;

public final class C22Panel {
    public static final PortRegistration<TrainSlabBlock> C22_FLOOR =
        new PortRegistration<TrainSlabBlock>("22_floor")
                    .factory(p -> new TrainSlabBlock(p, false))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> C22_FLOOR_CENTER =
        new PortRegistration<TrainSlabBlock>("22_floor_center")
                    .factory(p -> new TrainSlabBlock(p, false))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainPanelBlock> C22_PANEL_BOTTOM =
        new PortRegistration<TrainPanelBlock>("22_panel_bottom")
                    .factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainPanelBlock> C22_PANEL_MIDDLE =
        new PortRegistration<TrainPanelBlock>("22_panel_middle")
                    .factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainLadderBlock> C22_LADDER =
        new PortRegistration<TrainLadderBlock>("22_ladder")
                    .factory((properties) -> new TrainLadderBlock(properties, false))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSmallWindowBlock> C22_WINDOW =
        new PortRegistration<TrainSmallWindowBlock>("22_window")
                    .factory(p -> new TrainSmallWindowBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainOpenableWindowBlock> C22_LARGE_WINDOW =
        new PortRegistration<TrainOpenableWindowBlock>("22_large_window")
                    .factory(p -> new TrainOpenableWindowBlock(p, 2))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainOpenableWindowBlock> C22_DOUBLE_SMALL_WINDOW =
        new PortRegistration<TrainOpenableWindowBlock>("22_double_small_window")
                    .factory(p -> new TrainOpenableWindowBlock(p, 2))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainOpenableWindowBlock> C22_SMALL_WINDOW =
        new PortRegistration<TrainOpenableWindowBlock>("22_small_window")
                    .factory(p -> new TrainOpenableWindowBlock(p, 1))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<CustomRenderedDoorBlock> DOOR_22 =
        new PortRegistration<CustomRenderedDoorBlock>("22_door")
                    .factory(p -> new CustomRenderedDoorBlock(p,
                            Couple.create(
                                    com.misaka10843.kuayue_neo21.content.PortRegistration.resource("carriage/carriage_22/door/22_door_bottom_lh"),
                                    com.misaka10843.kuayue_neo21.content.PortRegistration.resource("carriage/carriage_22/door/22_door_upper_lh")
                            ), Couple.create(
                            com.misaka10843.kuayue_neo21.content.PortRegistration.resource("carriage/carriage_22/door/22_door_bottom"),
                            com.misaka10843.kuayue_neo21.content.PortRegistration.resource("carriage/carriage_22/door/22_door_upper")
                    ), new Vec3(0, 0, 0),new Vec3(0, 0, 0), RenderShape.MODEL, false
                    ))
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    
                    .submit();

    public static final PortRegistration<TrainSlabBlock> C22_COUPLER =
        new PortRegistration<TrainSlabBlock>("22_coupler")
                    .factory(p -> new TrainSlabBlock(p, false))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<CustomRenderedEndfaceBlock> C22_END_FACE =
        new PortRegistration<CustomRenderedEndfaceBlock>("22_end_face")
                    .factory(properties ->
                            new CustomRenderedEndfaceBlock(
                                    properties, TrainPanelProperties.DoorType.NO_DOOR,
                                    null,
                                    null,
                                    "carriage/carriage_22/22_end_face"
                            )
                    )
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainPanelBlock> C22_CARPORT_PANEL_TOP =
        new PortRegistration<TrainPanelBlock>("22_carport_panel_top")
                    .factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainHingePanelBlock> C22_CARPORT_PANEL_TOP_LAMP =
        new PortRegistration<TrainHingePanelBlock>("22_carport_panel_top_lamp")
                    .factory(p -> new TrainHingePanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> C22_CARPORT_TOP_CAP =
        new PortRegistration<TrainSlabBlock>("22_carport_top_cap")
                    .factory(p -> new TrainSlabBlock(p, true))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<AirVentBlock> C22_AIR_VENT =
        new PortRegistration<AirVentBlock>("22_air_vent")
                    .factory(AirVentBlock::new)
                    
                    .addProperty(BlockBehaviour.Properties::noOcclusion)
                    .addProperty(properties -> properties.strength(1.5f, 3f))
                    .addProperty(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    
                    
                    .submit();
}

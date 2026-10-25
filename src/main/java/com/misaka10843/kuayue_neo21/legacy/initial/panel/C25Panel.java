package com.misaka10843.kuayue_neo21.legacy.initial.panel;

import net.minecraft.world.phys.Vec2;
import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainPanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.HingeSlabBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.TrainSlabBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.window.TrainSmallWindowBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.window.TrainUnOpenableSmallWindowBlock;
import com.misaka10843.kuayue_neo21.content.PortRegistration;

public final class C25Panel {
    public static final PortRegistration<TrainPanelBlock> PANEL_BOTTOM_LINE_MARSHALLED_25 =
        new PortRegistration<TrainPanelBlock>("panel_bottom_line_marshalled_25")
                    .factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainPanelBlock> PANEL_BOTTOM_MARSHALLED_25 =
        new PortRegistration<TrainPanelBlock>("panel_bottom_marshalled_25")
                    .factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainPanelBlock> PANEL_MIDDLE_MARSHALLED_25 =
        new PortRegistration<TrainPanelBlock>("panel_middle_marshalled_25")
                    .factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainPanelBlock> PANEL_TOP_MARSHALLED_25 =
        new PortRegistration<TrainPanelBlock>("panel_top_marshalled_25")
                    .factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainPanelBlock> PANEL_TOP_MARSHALLED_25_2 =
        new PortRegistration<TrainPanelBlock>("panel_top_marshalled_25_2")
                    .factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(1, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSmallWindowBlock> WINDOW_OC_25 =
        new PortRegistration<TrainSmallWindowBlock>("window_oc_25")
                    .factory(TrainSmallWindowBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainUnOpenableSmallWindowBlock> WINDOW_OC_SEALED_25 =
        new PortRegistration<TrainUnOpenableSmallWindowBlock>("window_oc_sealed_25")
                    .factory(TrainUnOpenableSmallWindowBlock::new)
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> CARPORT_25BGZK =
        new PortRegistration<TrainSlabBlock>("carport_25bgzk")
                    .factory(p -> new TrainSlabBlock(p, true))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> CARPORT_AIR_CONDITION_25BGZK =
        new PortRegistration<TrainSlabBlock>("carport_air_condition_25bgzk")
                    .factory(p -> new TrainSlabBlock(p, true))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<HingeSlabBlock> CARPORT_AIR_CONDITION_SIDE_25BGZK =
        new PortRegistration<HingeSlabBlock>("carport_air_condition_side_25bgzk")
                    .factory(p -> new HingeSlabBlock(p, true))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> CARPORT_CENTER_25BGZKT =
        new PortRegistration<TrainSlabBlock>("carport_center_25bgzkt")
                    .factory(p -> new TrainSlabBlock(p, true))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> CARPORT_WATER_TANK_25BGZKT =
        new PortRegistration<TrainSlabBlock>("carport_water_tank_25bgzkt")
                    .factory(p -> new TrainSlabBlock(p, true))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> FLOOR_MARSHALLED_25 =
        new PortRegistration<TrainSlabBlock>("floor_marshalled_25")
                    .factory(p -> new TrainSlabBlock(p, false))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> PANEL_SLAB_UNIVERSAL_25 =
        new PortRegistration<TrainSlabBlock>("panel_slab_universal_25")
                    .factory(p -> new TrainSlabBlock(p, false))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();
}

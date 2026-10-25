package com.misaka10843.kuayue_neo21.legacy.initial.panel;

import net.minecraft.world.phys.Vec2;
import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainPanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.TrainPanelProperties;
import com.misaka10843.kuayue_neo21.legacy.block.panels.conductor.FreightC70EndFaceBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.conductor.FreightNX70EndFaceBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.door.DoubleRotateDoorBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.end_face.FreightEndFaceBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.HingeSlabBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.TrainSlabBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.window.TrainOpenableWindowBlock;
import com.misaka10843.kuayue_neo21.content.PortRegistration;

public final class CFreightPanel {
    public static final PortRegistration<FreightC70EndFaceBlock> FREIGHT_C70_END_FACE =
        new PortRegistration<FreightC70EndFaceBlock>("freight_c70_end_face")
                    .factory((properties) -> new FreightC70EndFaceBlock(properties,
                            FreightEndFaceBlock.FreightType.C70))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainPanelBlock> FREIGHT_C70_SLAB_BOTTOM =
        new PortRegistration<TrainPanelBlock>("freight_c70_slab_bottom")
                    .factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(2, 1)))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainOpenableWindowBlock> FREIGHT_C70_SLAB_TOP =
        new PortRegistration<TrainOpenableWindowBlock>("freight_c70_slab_top")
                    .factory(p -> new TrainOpenableWindowBlock(p, -1, 1, 1))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<DoubleRotateDoorBlock> FREIGHT_C70_DOOR =
        new PortRegistration<DoubleRotateDoorBlock>("freight_c70_door")
                    .factory(properties ->
                            new DoubleRotateDoorBlock(
                                    properties, TrainPanelProperties.DoorType.ROTATE_SINGLE_SIDED,
                                    "carriage/freight/c70/freight_c70_door_left",
                                    "carriage/freight/c70/freight_c70_door_right",
                                    "carriage/freight/c70/freight_c70_door_frame"
                            )
                    )
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> FREIGHT_NX70_FLOOR_CENTER =
        new PortRegistration<TrainSlabBlock>("freight_nx70_floor_center")
                    .factory(p -> new TrainSlabBlock(p, false))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> FREIGHT_NX70_FLOOR_HIGH =
        new PortRegistration<TrainSlabBlock>("freight_nx70_floor_high")
                    .factory(p -> new TrainSlabBlock(p, false))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> FREIGHT_NX70_FLOOR_COVER_PLATE =
        new PortRegistration<TrainSlabBlock>("freight_nx70_floor_cover_plate")
                    .factory(p -> new TrainSlabBlock(p, true))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<HingeSlabBlock> FREIGHT_NX70_FLOOR_CONNECTION =
        new PortRegistration<HingeSlabBlock>("freight_nx70_floor_connection")
                    .factory(p -> new HingeSlabBlock(p, false, 2))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> FREIGHT_NX70_FLOOR_LOW =
        new PortRegistration<TrainSlabBlock>("freight_nx70_floor_low")
                    .factory(p -> new TrainSlabBlock(p, false))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<TrainSlabBlock> FREIGHT_NX70_FLOOR_LOW_LADDER =
        new PortRegistration<TrainSlabBlock>("freight_nx70_floor_low_ladder")
                    .factory(p -> new TrainSlabBlock(p, false))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();

    public static final PortRegistration<FreightNX70EndFaceBlock> FREIGHT_NX70_END_FACE =
        new PortRegistration<FreightNX70EndFaceBlock>("freight_nx70_end_face")
                    .factory((properties) -> new FreightNX70EndFaceBlock(properties,
                            FreightEndFaceBlock.FreightType.NX70))
                    
                    
                    .noOcclusion().strengthAndTool(1.5f, 3f)
                    .submit();
}

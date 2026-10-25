package com.misaka10843.kuayue_neo21.content.infrastructure;

import com.misaka10843.kuayue_neo21.legacy.block.panels.base.TrainPanelProperties.ShapeType;
import net.minecraft.world.phys.Vec3;

public final class OverheadTrussPillar extends OverheadTruss {
    public OverheadTrussPillar(Properties properties, Vec3 begin, Vec3 end) { super(properties, begin, end, ShapeType.PILLAR_TRUSS); }
}

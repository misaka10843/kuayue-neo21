package com.misaka10843.kuayue_neo21.legacy.block.panels.conductor;

import com.misaka10843.kuayue_neo21.legacy.block.panels.FullShapeDirectionalBlock;

public class DF5CowCatcherBlock extends FullShapeDirectionalBlock implements com.misaka10843.kuayue_neo21.content.coupling.CouplerPart {
    @Override public double couplerOffset() { return 0.5; }
    public DF5CowCatcherBlock(Properties properties) {
        super(properties);
    }
}

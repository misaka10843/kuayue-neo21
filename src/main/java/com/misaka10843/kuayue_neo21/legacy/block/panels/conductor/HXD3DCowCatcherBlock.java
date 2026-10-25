package com.misaka10843.kuayue_neo21.legacy.block.panels.conductor;

import com.misaka10843.kuayue_neo21.legacy.block.panels.FullShapeDirectionalBlock;

public class HXD3DCowCatcherBlock extends FullShapeDirectionalBlock implements com.misaka10843.kuayue_neo21.content.coupling.CouplerPart {
    @Override public double couplerOffset() { return 1.5; }
    public HXD3DCowCatcherBlock(Properties properties) {
        super(properties);
    }
}

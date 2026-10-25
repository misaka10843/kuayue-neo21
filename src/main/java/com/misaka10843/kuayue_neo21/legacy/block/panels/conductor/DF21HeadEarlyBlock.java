package com.misaka10843.kuayue_neo21.legacy.block.panels.conductor;

import com.misaka10843.kuayue_neo21.legacy.block.panels.FullShapeDirectionalBlock;

public class DF21HeadEarlyBlock extends FullShapeDirectionalBlock implements com.misaka10843.kuayue_neo21.content.coupling.CouplerPart {
    @Override public double couplerOffset() { return 2; }
    public DF21HeadEarlyBlock(Properties properties) {
        super(properties);
    }
}

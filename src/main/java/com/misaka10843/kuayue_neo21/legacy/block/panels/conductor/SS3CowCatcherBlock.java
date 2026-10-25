package com.misaka10843.kuayue_neo21.legacy.block.panels.conductor;

import com.misaka10843.kuayue_neo21.legacy.block.panels.FullShapeDirectionalBlock;

public class SS3CowCatcherBlock extends FullShapeDirectionalBlock implements com.misaka10843.kuayue_neo21.content.coupling.CouplerPart {
    @Override public double couplerOffset() { return 2; }
    public SS3CowCatcherBlock(Properties properties) {
        super(properties);
    }
}

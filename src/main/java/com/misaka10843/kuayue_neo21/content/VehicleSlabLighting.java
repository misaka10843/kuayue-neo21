package com.misaka10843.kuayue_neo21.content;

import com.misaka10843.kuayue_neo21.mixin.BlockPropertiesAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class VehicleSlabLighting {
    private VehicleSlabLighting() {}

    public static BlockBehaviour.Properties opaque(BlockBehaviour.Properties properties) {
        ((BlockPropertiesAccessor) properties).kuayue$setCanOcclude(true);
        return properties;
    }

    public static VoxelShape localOcclusion(VoxelShape shape) {
        return Shapes.join(shape, Shapes.block(), BooleanOp.AND);
    }
}

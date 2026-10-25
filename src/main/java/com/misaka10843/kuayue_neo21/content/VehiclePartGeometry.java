package com.misaka10843.kuayue_neo21.content;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class VehiclePartGeometry {
    public static List<BlockPos> footprint(BlockPos root, Direction facing, VoxelShape closedShape) {
        var result = new ArrayList<BlockPos>();
        if (closedShape.isEmpty()) return List.of(root);
        var bounds = closedShape.bounds();
        boolean alongX = facing.getAxis() == Direction.Axis.Z;
        int first = Mth.floor((alongX ? bounds.minX : bounds.minZ) + 1e-6);
        int last = Mth.ceil((alongX ? bounds.maxX : bounds.maxZ) - 1e-6);
        for (int side = first; side < last; side++) {
            for (int y = Mth.floor(bounds.minY + 1e-6); y < Mth.ceil(bounds.maxY - 1e-6); y++) {
                result.add(root.offset(alongX ? side : 0, y, alongX ? 0 : side));
            }
        }
        if (!result.contains(root)) result.add(root);
        return result;
    }

    public static VoxelShape localShape(VoxelShape rootShape, BlockPos root, BlockPos part) {
        BlockPos offset = root.subtract(part);
        return Shapes.join(rootShape.move(offset.getX(), offset.getY(), offset.getZ()), Shapes.block(), BooleanOp.AND);
    }
}

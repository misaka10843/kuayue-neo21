package com.misaka10843.kuayue_neo21.content.infrastructure;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.CarriageContraption;
import net.minecraft.world.level.block.Rotation;

public final class PantographMounting {
    public static final String OFFSET_TAG = "KuayueMountOffset";
    public static final String VERSION_TAG = "KuayueMountVersion";
    private PantographMounting() {}

    public static double movingOffset(Carriage carriage, BlockPos rotatedPos) {
        var entity = carriage.anyAvailableEntity();
        if (entity == null || !(entity.getContraption() instanceof CarriageContraption contraption)) return 0;
        Rotation rotation = switch (contraption.getAssemblyDirection()) {
            case NORTH -> Rotation.COUNTERCLOCKWISE_90;
            case EAST -> Rotation.CLOCKWISE_180;
            case SOUTH -> Rotation.CLOCKWISE_90;
            default -> Rotation.NONE;
        };
        for (var block : contraption.getBlocks().values()) {
            if (block.nbt() != null && block.nbt().contains(OFFSET_TAG) && block.pos().rotate(rotation).equals(rotatedPos))
                return savedOffset(block.nbt());
        }
        return 0;
    }

    public static double offset(BlockGetter level, BlockPos pos) {
        BlockPos support = pos.below();
        BlockState state = level.getBlockState(support);
        if (!BuiltInRegistries.BLOCK.getKey(state.getBlock()).getNamespace().equals("kuayue")) return 0;
        VoxelShape shape = state.getShape(level, support);
        if (shape.isEmpty()) return 0;
        double top = shape.max(Direction.Axis.Y);
        return top > 0 && top <= 1 ? top - 1 : 0;
    }

    public static double savedOffset(net.minecraft.nbt.CompoundTag tag) {
        double offset = tag.getDouble(OFFSET_TAG);
        return offset != 0 && tag.getInt(VERSION_TAG) < 2 ? offset + 5.0 / 16 : offset;
    }
}

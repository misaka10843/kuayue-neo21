package com.misaka10843.kuayue_neo21.content;

import com.misaka10843.kuayue_neo21.legacy.block.panels.block_entity.CustomRenderedDoorEntity;
import com.misaka10843.kuayue_neo21.legacy.block.panels.block_entity.CustomRenderedEndfaceEntity;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

public final class VehicleDoorAnimations {
    public record Progress(Block block, float value) {}

    private VehicleDoorAnimations() {}

    public static Map<BlockPos, Progress> capture(Iterable<BlockEntity> entities) {
        var result = new HashMap<BlockPos, Progress>();
        for (var entity : entities) {
            Float value = null;
            if (entity instanceof CustomRenderedDoorEntity door) value = door.animation_controller;
            else if (entity instanceof CustomRenderedEndfaceEntity end) value = end.counter;
            if (value != null) result.put(entity.getBlockPos(), new Progress(entity.getBlockState().getBlock(), Mth.clamp(value, 0, 1)));
        }
        return result;
    }

    public static void restore(Iterable<BlockEntity> entities, Map<BlockPos, Progress> saved) {
        for (var entity : entities) {
            var progress = saved.get(entity.getBlockPos());
            if (progress == null || progress.block() != entity.getBlockState().getBlock()) continue;
            if (entity instanceof CustomRenderedDoorEntity door) door.animation_controller = progress.value();
            else if (entity instanceof CustomRenderedEndfaceEntity end) end.counter = progress.value();
        }
    }
}

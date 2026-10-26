package com.misaka10843.kuayue_neo21.data;

import com.misaka10843.kuayue_neo21.content.PortRegistration;
import com.misaka10843.kuayue_neo21.content.VehicleDoorAnimations;
import com.misaka10843.kuayue_neo21.legacy.block.panels.block_entity.CustomRenderedDoorEntity;
import com.simibubi.create.content.decoration.slidingDoor.SlidingDoorBlockEntity;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;

final class DoorAnimationAudit {
    static Map<String, Integer> run(List<String> failures) {
        int cases = 0, mismatches = 0;
        for (String id : List.of("door_25b", "door_sliding_25g", "22_door")) {
            var block = net.minecraft.core.registries.BuiltInRegistries.BLOCK.get(PortRegistration.resource(id));
            for (Direction facing : Direction.Plane.HORIZONTAL) for (DoorHingeSide hinge : DoorHingeSide.values()) {
                var closed = block.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, facing)
                        .setValue(BlockStateProperties.DOOR_HINGE, hinge).setValue(BlockStateProperties.OPEN, false);
                var entity = (CustomRenderedDoorEntity) ((EntityBlock) block).newBlockEntity(BlockPos.ZERO, closed);
                entity.setOpen(true);
                cases++;
                if (entity.isOpen() != SlidingDoorBlockEntity.isOpen(closed)) mismatches++;
                var opened = closed.setValue(BlockStateProperties.OPEN, true);
                entity.setBlockState(opened);
                entity.update(new StructureBlockInfo(BlockPos.ZERO, opened, null), null, BlockPos.ZERO, null);
                cases++;
                if (entity.isOpen() != SlidingDoorBlockEntity.isOpen(opened)) mismatches++;
                entity.setBlockState(closed);
                entity.setOpen(true);
                entity.tick();
                cases++;
                if (entity.isOpen() != SlidingDoorBlockEntity.isOpen(closed)) mismatches++;
            }
        }
        if (mismatches > 0) failures.add("Door animation used cached state instead of its own block state: " + mismatches + "/" + cases);
        int resetCases = 0;
        for (String id : List.of("door_25b", "door_sliding_25g", "end_face_25t_1")) {
            var block = net.minecraft.core.registries.BuiltInRegistries.BLOCK.get(PortRegistration.resource(id));
            var closed = block.defaultBlockState().setValue(BlockStateProperties.OPEN, false);
            var opened = closed.setValue(BlockStateProperties.OPEN, true);
            var old = new java.util.ArrayList<net.minecraft.world.level.block.entity.BlockEntity>();
            var rebuilt = new java.util.ArrayList<net.minecraft.world.level.block.entity.BlockEntity>();
            float[] expected = {0, 1, 0.35f};
            for (int index = 0; index < expected.length; index++) {
                var pos = new BlockPos(index, 0, 0);
                var previous = ((EntityBlock) block).newBlockEntity(pos, index == 0 ? closed : opened);
                setProgress(previous, expected[index]);
                old.add(previous);
                var fresh = ((EntityBlock) block).newBlockEntity(pos, opened);
                if (progress(fresh) != 1) failures.add("Opened door replayed its animation on creation: " + id);
                rebuilt.add(fresh);
            }
            var chest = new net.minecraft.world.level.block.entity.ChestBlockEntity(new BlockPos(10, 0, 0), net.minecraft.world.level.block.Blocks.CHEST.defaultBlockState());
            chest.setItem(0, new net.minecraft.world.item.ItemStack(net.minecraft.world.item.Items.IRON_INGOT, 7));
            old.add(chest);
            rebuilt.add(chest);
            var saved = VehicleDoorAnimations.capture(old);
            VehicleDoorAnimations.restore(rebuilt, saved);
            for (int index = 0; index < expected.length; index++) {
                resetCases++;
                if (progress(rebuilt.get(index)) != expected[index]) failures.add("Unrelated door animation restarted after render reset: " + id + "/" + index);
            }
            if (saved.containsKey(chest.getBlockPos()) || chest.getItem(0).getCount() != 7) failures.add("Door animation capture affected unrelated inventory");
        }
        return Map.of("animationStateCases", cases, "mismatches", mismatches, "renderResetCases", resetCases);
    }

    private static void setProgress(net.minecraft.world.level.block.entity.BlockEntity entity, float value) {
        if (entity instanceof CustomRenderedDoorEntity door) door.animation_controller = value;
        else ((com.misaka10843.kuayue_neo21.legacy.block.panels.block_entity.CustomRenderedEndfaceEntity) entity).counter = value;
    }

    private static float progress(net.minecraft.world.level.block.entity.BlockEntity entity) {
        return entity instanceof CustomRenderedDoorEntity door ? door.animation_controller
                : ((com.misaka10843.kuayue_neo21.legacy.block.panels.block_entity.CustomRenderedEndfaceEntity) entity).counter;
    }
}

package com.misaka10843.kuayue_neo21.data;

import com.misaka10843.kuayue_neo21.content.PortRegistration;
import com.misaka10843.kuayue_neo21.content.VehicleDoorPairs;
import com.simibubi.create.content.decoration.slidingDoor.SlidingDoorBlock;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;

final class DoorPairingAudit {
    static Map<String, Integer> run(List<String> failures) {
        BlockPos root = BlockPos.ZERO;
        int cases = 0, mismatches = 0;
        var nativeRoot = com.simibubi.create.AllBlocks.ANDESITE_DOOR.get().defaultBlockState();
        var foreignPart = door("company_panel_door", Direction.NORTH, DoorHingeSide.LEFT, false);
        if (VehicleDoorPairs.ownsPart(nativeRoot, root, foreignPart, root.above())) failures.add("Vehicle companion claimed a foreign Create door");
        for (Direction facing : Direction.Plane.HORIZONTAL) for (DoorHingeSide hinge : DoorHingeSide.values()) {
            for (boolean open : new boolean[]{false, true}) {
                var state = door("door_25b", facing, hinge, open);
                Direction physicalFacing = facing.getOpposite();
                BlockPos partner = root.relative(hinge == DoorHingeSide.LEFT ? physicalFacing.getClockWise() : physicalFacing.getCounterClockWise());
                var nativeClicked = nativeDoor(physicalFacing, hinge, !open);
                for (Direction otherFacing : Direction.Plane.HORIZONTAL) for (DoorHingeSide otherHinge : DoorHingeSide.values()) {
                    for (boolean otherOpen : new boolean[]{false, true}) for (String id : List.of("door_25b", "door_sliding_25g", "company_panel_door")) {
                        var other = door(id, otherFacing, otherHinge, otherOpen);
                        var nativeOther = nativeDoor(otherFacing.getOpposite(), otherHinge, otherOpen);
                        for (Direction offset : Direction.Plane.HORIZONTAL) for (int height = -1; height <= 1; height++) {
                            BlockPos candidate = root.relative(offset).offset(0, height, 0);
                            boolean expected = id.equals("door_25b") && candidate.equals(partner)
                                    && SlidingDoorBlock.isDoubleDoor(nativeClicked, hinge, physicalFacing, nativeOther);
                            boolean actual = VehicleDoorPairs.roots(root, state, pos -> pos.equals(candidate) ? other : Blocks.AIR.defaultBlockState()).size() == 2;
                            cases++;
                            if (actual != expected) {
                                mismatches++;
                                if (mismatches <= 8) failures.add("Create door pairing mismatch: " + state + " / " + other + " at " + candidate);
                            }
                        }
                    }
                }
            }
        }
        return Map.of("createPairingCases", cases, "mismatches", mismatches);
    }

    private static BlockState door(String id, Direction facing, DoorHingeSide hinge, boolean open) {
        return net.minecraft.core.registries.BuiltInRegistries.BLOCK.get(PortRegistration.resource(id)).defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, facing).setValue(BlockStateProperties.DOOR_HINGE, hinge)
                .setValue(BlockStateProperties.OPEN, open);
    }

    private static BlockState nativeDoor(Direction facing, DoorHingeSide hinge, boolean open) {
        return com.simibubi.create.AllBlocks.ANDESITE_DOOR.get().defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, facing).setValue(BlockStateProperties.DOOR_HINGE, hinge)
                .setValue(BlockStateProperties.OPEN, open);
    }
}

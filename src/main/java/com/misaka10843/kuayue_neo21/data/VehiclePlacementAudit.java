package com.misaka10843.kuayue_neo21.data;

import com.misaka10843.kuayue_neo21.content.PortRegistration;
import com.misaka10843.kuayue_neo21.content.VehiclePlacement;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.TrainSlabBlock;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

public final class VehiclePlacementAudit {
    public static Map<String, Integer> run(List<String> failures) {
        var floor = (TrainSlabBlock) PortRegistration.ENTRIES.stream().filter(entry -> entry.id().equals("floor_25b")).findFirst().orElseThrow().get();
        BlockPos root = new BlockPos(0, 64, 0);
        int checks = 0;
        for (Direction facing : Direction.Plane.HORIZONTAL) {
            var states = new HashMap<BlockPos, BlockState>();
            states.put(root, floor.defaultBlockState().setValue(TrainSlabBlock.FACING, facing));
            var world = new PlacementView(states);
            for (Direction face : Direction.values()) {
                Direction[] targets = face.getAxis().isVertical()
                        ? new Direction[]{Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH}
                        : new Direction[]{face.getClockWise(), face.getCounterClockWise()};
                for (Direction side : targets) {
                    BlockPos position = root.relative(face);
                    BlockPos expected = root.relative(side);
                    Vec3 click = Vec3.atCenterOf(root).add(Vec3.atLowerCornerOf(face.getNormal()).scale(0.5))
                            .add(Vec3.atLowerCornerOf(side.getNormal()).scale(0.4));
                    var result = VehiclePlacement.resolve(world, position, face, facing.getOpposite(), click, true, floor, floor.defaultBlockState());
                    if (!result.pos().equals(expected) || result.state().getValue(TrainSlabBlock.FACING) != facing) {
                        failures.add("Incorrect 25B assisted placement: " + face + " " + side + " " + facing);
                    }
                    var direct = VehiclePlacement.resolve(world, position, face, facing, click, false, floor, floor.defaultBlockState());
                    if (!direct.pos().equals(position)) failures.add("Shift placement redirected: " + face);
                    states.put(expected, Blocks.STONE.defaultBlockState());
                    var blocked = VehiclePlacement.resolve(world, position, face, facing, click, true, floor, floor.defaultBlockState());
                    if (!blocked.pos().equals(position)) failures.add("Occupied assisted target accepted: " + face + " " + side);
                    states.remove(expected);
                    checks += 3;
                }
            }
        }
        return Map.of("floorPlacementChecks", checks);
    }

    private record PlacementView(Map<BlockPos, BlockState> states) implements BlockGetter {
        @Override public BlockState getBlockState(BlockPos pos) { return states.getOrDefault(pos, Blocks.AIR.defaultBlockState()); }
        @Override public BlockEntity getBlockEntity(BlockPos pos) { return null; }
        @Override public FluidState getFluidState(BlockPos pos) { return getBlockState(pos).getFluidState(); }
        @Override public int getHeight() { return 256; }
        @Override public int getMinBuildHeight() { return 0; }
    }
}

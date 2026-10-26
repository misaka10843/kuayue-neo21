package com.misaka10843.kuayue_neo21.content;

import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainPanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.CompanyTrainPanel;
import com.misaka10843.kuayue_neo21.legacy.block.panels.door.DoubleRotateDoorBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.door.TrainDoorBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.window.TrainOpenableWindowBlock;
import java.util.List;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;

public final class VehicleDoorPairs {
    private VehicleDoorPairs() {}

    public static boolean isLeafDoor(BlockState state) {
        return state.getBlock() instanceof TrainDoorBlock || state.getBlock() instanceof DoubleRotateDoorBlock;
    }

    private static BlockPos partnerPosition(BlockPos root, BlockState state, DoorHingeSide hinge) {
        var facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        // Vehicle door models face opposite their stored facing property.
        return root.relative(hinge == DoorHingeSide.LEFT ? facing.getCounterClockWise() : facing.getClockWise());
    }

    public static List<BlockPos> roots(BlockPos root, BlockState state, Function<BlockPos, BlockState> states) {
        if (!isLeafDoor(state)) return List.of(root);
        var hinge = state.getValue(BlockStateProperties.DOOR_HINGE);
        BlockPos partner = partnerPosition(root, state, hinge);
        BlockState other = states.apply(partner);
        return matches(state, other, hinge) && state.getValue(BlockStateProperties.OPEN) == other.getValue(BlockStateProperties.OPEN)
                ? List.of(root, partner) : List.of(root);
    }

    private static boolean matches(BlockState state, BlockState other, DoorHingeSide hinge) {
        return other.is(state.getBlock()) && other.getValue(BlockStateProperties.HORIZONTAL_FACING) == state.getValue(BlockStateProperties.HORIZONTAL_FACING)
                && other.getValue(BlockStateProperties.DOOR_HINGE) != hinge;
    }

    public static DoorHingeSide hingeForPlacement(BlockPlaceContext context, BlockState state) {
        DoorHingeSide preferred = TrainOpenableWindowBlock.getHinge(context);
        for (var hinge : new DoorHingeSide[]{preferred, preferred == DoorHingeSide.LEFT ? DoorHingeSide.RIGHT : DoorHingeSide.LEFT}) {
            BlockState other = context.getLevel().getBlockState(partnerPosition(context.getClickedPos(), state, hinge));
            if (matches(state, other, hinge)) return hinge;
        }
        return preferred;
    }

    public static void setOpen(Level level, BlockPos root, BlockState state, boolean open) {
        for (BlockPos door : roots(root, state, level::getBlockState)) {
            BlockState current = level.getBlockState(door);
            level.setBlock(door, current.setValue(BlockStateProperties.OPEN, open), 10);
            var panel = (TrainPanelBlock) current.getBlock();
            for (BlockPos part : panel.getOccupiedPositions(level, door, current)) {
                if (part.equals(door)) continue;
                BlockState companion = level.getBlockState(part);
                if (companion.getBlock() instanceof CompanyTrainPanel && companion.hasProperty(BlockStateProperties.OPEN)
                        && door.equals(CompanyTrainPanel.getParentPos(level, part)) && ownsPart(current, door, companion, part)) {
                    level.setBlock(part, companion.setValue(BlockStateProperties.OPEN, open), 10);
                }
            }
        }
    }

    public static boolean ownsPart(BlockState rootState, BlockPos root, BlockState partState, BlockPos part) {
        if (!rootState.hasProperty(BlockStateProperties.OPEN)) return true;
        if (!(rootState.getBlock() instanceof TrainPanelBlock panel)) return false;
        if (root.equals(part) || !(partState.getBlock() instanceof CompanyTrainPanel)) return false;
        var hinge = rootState.hasProperty(BlockStateProperties.DOOR_HINGE) ? rootState.getValue(BlockStateProperties.DOOR_HINGE) : DoorHingeSide.LEFT;
        var expected = panel.generateCompanyState(rootState.getValue(BlockStateProperties.HORIZONTAL_FACING), hinge, rootState.getValue(BlockStateProperties.OPEN));
        return expected.getBlock().getClass() == partState.getBlock().getClass()
                && expected.getValue(BlockStateProperties.HORIZONTAL_FACING) == partState.getValue(BlockStateProperties.HORIZONTAL_FACING)
                && expected.getValue(BlockStateProperties.DOOR_HINGE) == partState.getValue(BlockStateProperties.DOOR_HINGE)
                && panel.getOccupiedPositions(EmptyBlockGetter.INSTANCE, root, rootState).contains(part);
    }
}

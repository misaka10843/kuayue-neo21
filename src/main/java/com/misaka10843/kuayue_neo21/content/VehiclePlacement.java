package com.misaka10843.kuayue_neo21.content;

import com.misaka10843.kuayue_neo21.legacy.block.panels.SkirtBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainPanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.TrainPanelProperties;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.TrainSlabBlock;
import com.misaka10843.kuayue_neo21.legacy.utils.DirectionUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

public final class VehiclePlacement {
    private static final TagKey<Block> SIDE_PLACEMENT = TagKey.create(Registries.BLOCK, PortRegistration.resource("side_placement"));

    public record Placement(BlockPos pos, BlockState state) {}

    public static Placement resolve(BlockPlaceContext context, TrainPanelBlock panel, BlockState state) {
        var original = new Placement(context.getClickedPos(), state);
        if (context.getPlayer() == null || context.getPlayer().isShiftKeyDown()) return original;
        Direction face = context.getClickedFace();
        BlockPos parent = context.getClickedPos().relative(face.getOpposite());
        BlockState parentState = context.getLevel().getBlockState(parent);
        if (panel instanceof SkirtBlock) return skirt(context, original, parent, parentState);
        if (panel instanceof TrainSlabBlock) return slab(context, original, parent, parentState);
        if (!parentState.is(SIDE_PLACEMENT) || !parentState.hasProperty(TrainPanelBlock.FACING)) return original;
        if (face == Direction.UP) return new Placement(original.pos().above(-(int) panel.beginPos.y), state);
        if (face == Direction.DOWN) return new Placement(original.pos().below((int) panel.endPos.y - 1), state);
        Direction facing = parentState.getValue(TrainPanelBlock.FACING);
        if (facing != face && facing != face.getOpposite()) return original;
        Direction nearest = nearest(context.getClickedPos(), context.getClickLocation(), context.getHorizontalDirection());
        if (nearest == Direction.UP) return new Placement(parent.above(1 - (int) panel.beginPos.y), state);
        if (nearest == Direction.DOWN) return new Placement(parent.below((int) panel.endPos.y), state);
        boolean left = nearest == context.getHorizontalDirection().getClockWise();
        BlockPos target = DirectionUtil.left(parent, face.getOpposite(), left ? 1 : -1);
        if (!panel.beginPos.equals(net.minecraft.world.phys.Vec2.ZERO) || !panel.endPos.equals(net.minecraft.world.phys.Vec2.ONE))
            target = DirectionUtil.left(target, face.getOpposite(), (int) ((left ? 1 : -1) * (panel.beginPos.x + panel.endPos.x)));
        return new Placement(target, state);
    }

    private static Placement skirt(BlockPlaceContext context, Placement original, BlockPos parent, BlockState parentState) {
        Direction face = context.getClickedFace();
        if (face.getAxis().isVertical() || !parentState.is(original.state().getBlock())) return original;
        BlockPos left = DirectionUtil.left(parent, face, 1);
        BlockPos right = DirectionUtil.right(parent, face, 1);
        boolean chooseLeft = distance(context.getClickLocation(), left) <= distance(context.getClickLocation(), right);
        BlockPos target = chooseLeft ? left : right;
        if (!context.getLevel().getBlockState(target).canBeReplaced()) return original;
        var state = original.state().setValue(SkirtBlock.SKIRT_TYPE,
                chooseLeft ? TrainPanelProperties.SkirtType.RIGHT : TrainPanelProperties.SkirtType.LEFT);
        return new Placement(target, state);
    }

    private static Placement slab(BlockPlaceContext context, Placement original, BlockPos parent, BlockState parentState) {
        if (!(parentState.getBlock() instanceof TrainSlabBlock)) return original;
        Direction face = context.getClickedFace();
        BlockPos target;
        if (face.getAxis().isVertical()) {
            Direction nearest = nearestHorizontal(parent, context.getClickLocation());
            target = parent.relative(nearest);
        } else {
            BlockPos left = DirectionUtil.left(parent, face, 1);
            BlockPos right = DirectionUtil.right(parent, face, 1);
            target = distance(context.getClickLocation(), left) <= distance(context.getClickLocation(), right) ? left : right;
        }
        if (!context.getLevel().getBlockState(target).canBeReplaced()) return original;
        return new Placement(target, original.state().setValue(TrainPanelBlock.FACING, parentState.getValue(TrainPanelBlock.FACING)));
    }

    private static Direction nearestHorizontal(BlockPos parent, Vec3 click) {
        Direction nearest = Direction.NORTH;
        double minimum = Double.POSITIVE_INFINITY;
        for (Direction side : Direction.Plane.HORIZONTAL) {
            double distance = distance(click, parent.relative(side));
            if (distance < minimum) { minimum = distance; nearest = side; }
        }
        return nearest;
    }

    private static Direction nearest(BlockPos pos, Vec3 click, Direction facing) {
        Direction nearest = facing.getClockWise();
        double minimum = Double.POSITIVE_INFINITY;
        for (Direction side : new Direction[]{facing.getClockWise(), facing.getCounterClockWise(), Direction.UP, Direction.DOWN}) {
            double distance = distance(click, pos.relative(side));
            if (distance < minimum) { minimum = distance; nearest = side; }
        }
        return nearest;
    }

    private static double distance(Vec3 click, BlockPos pos) { return click.distanceToSqr(Vec3.atCenterOf(pos)); }
}

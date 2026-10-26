package com.misaka10843.kuayue_neo21.legacy.block.panels.base;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CompanyTrainDoor extends CompanyTrainPanel {
    public CompanyTrainDoor(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        var shape = parentShape(pLevel, pPos, pState, pContext, false);
        if (shape != null) return shape;
        return TrainPanelShapes.getDoorShape(pState.getValue(FACING).getOpposite(), pState.getValue(HINGE), pState.getValue(OPEN));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        var shape = parentShape(pLevel, pPos, pState, pContext, true);
        return shape != null ? shape : getShape(pState, pLevel, pPos, pContext);
    }

    protected VoxelShape parentShape(BlockGetter level, BlockPos part, BlockState partState, CollisionContext context, boolean collision) {
        var root = getParentPos(level, part);
        if (root == null) return null;
        var state = level.getBlockState(root);
        if (!(state.getBlock() instanceof com.misaka10843.kuayue_neo21.legacy.block.panels.door.TrainDoorBlock)
                || !com.misaka10843.kuayue_neo21.content.VehicleDoorPairs.ownsPart(state, root, partState, part)) return null;
        var shape = collision ? state.getCollisionShape(level, root, context) : state.getShape(level, root, context);
        return com.misaka10843.kuayue_neo21.content.VehiclePartGeometry.localShape(shape, root, part);
    }

    public static class Sliding extends CompanyTrainDoor {

        public Sliding(Properties pProperties) {
            super(pProperties);
        }

        @Override
        public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
            var shape = parentShape(pLevel, pPos, pState, pContext, false);
            if (shape != null) return shape;
            return TrainPanelShapes.getSlidingDoorShape(pState.getValue(FACING).getOpposite(), pState.getValue(HINGE), pState.getValue(OPEN));
        }
    }
}

package com.misaka10843.kuayue_neo21.legacy.block.panels.base;

import com.misaka10843.kuayue_neo21.legacy.block.panels.base.TrainPanelShapes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CompanyTrainSlab extends CompanyTrainPanel{
    public final boolean isCarport;
    public CompanyTrainSlab(Properties pProperties, boolean isCarport) {
        super(com.misaka10843.kuayue_neo21.content.VehicleSlabLighting.opaque(pProperties));
        this.isCarport = isCarport;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return isCarport ? TrainPanelShapes.CARPORT_CENTER : TrainPanelShapes.FLOOR;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return getShape(pState, pLevel, pPos, pContext);
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState state) { return true; }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return com.misaka10843.kuayue_neo21.content.VehicleSlabLighting.localOcclusion(getShape(state, level, pos, CollisionContext.empty()));
    }
}

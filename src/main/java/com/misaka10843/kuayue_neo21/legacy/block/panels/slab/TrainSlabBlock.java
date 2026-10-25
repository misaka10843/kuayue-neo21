package com.misaka10843.kuayue_neo21.legacy.block.panels.slab;

import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainPanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.TrainPanelShapes;

import com.misaka10843.kuayue_neo21.legacy.initial.AllBlocks;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.CompanyTrainPanel;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class TrainSlabBlock extends TrainPanelBlock implements IWrenchable {
    public final boolean carport;

    public TrainSlabBlock(Properties pProperties, boolean isCarport) {
        super(com.misaka10843.kuayue_neo21.content.VehicleSlabLighting.opaque(pProperties));
        this.carport = isCarport;
    }

    public TrainSlabBlock(Properties properties, boolean isCarport, int left, int right){
        super(com.misaka10843.kuayue_neo21.content.VehicleSlabLighting.opaque(properties), new Vec2(left, 0), new Vec2(right, 1));
        this.carport = isCarport;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if (carport) return TrainPanelShapes.CARPORT_CENTER;
        return TrainPanelShapes.FLOOR;
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

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return super.getStateForPlacement(pContext)
                .setValue(FACING, pContext.getHorizontalDirection());
    }

    @Override
    public BlockState generateCompanyState(Direction direction, DoorHingeSide hingeSide, boolean open) {
        if(carport) {
            return AllBlocks.COMPANY_CARPORT.instance().defaultBlockState()
                    .setValue(CompanyTrainPanel.FACING, direction);
        } else {
            return AllBlocks.COMPANY_FLOOR.instance().defaultBlockState()
                    .setValue(CompanyTrainPanel.FACING, direction);
        }
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        Level world = context.getLevel();
        BlockState rotated = state.cycle(FACING);
        if (!rotated.canSurvive(world, context.getClickedPos()))
            return InteractionResult.PASS;

        KineticBlockEntity.switchToBlockState(world, context.getClickedPos(), updateAfterWrenched(rotated, context));

        BlockEntity be = context.getLevel()
                .getBlockEntity(context.getClickedPos());
        if (be instanceof GeneratingKineticBlockEntity) {
            ((GeneratingKineticBlockEntity) be).reActivateSource = true;
        }

        if (world.getBlockState(context.getClickedPos()) != state)
            IWrenchable.playRotateSound(world, context.getClickedPos());

        return InteractionResult.SUCCESS;
    }
}

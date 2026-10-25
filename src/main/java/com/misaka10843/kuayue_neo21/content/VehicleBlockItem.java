package com.misaka10843.kuayue_neo21.content;

import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainPanelBlock;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;

public final class VehicleBlockItem extends BlockItem {
    public VehicleBlockItem(TrainPanelBlock block, Properties properties) { super(block, properties); }

    public VehiclePlacement.Placement preview(BlockPlaceContext source) {
        BlockPlaceContext context = updatePlacementContext(source);
        if (context == null) return null;
        BlockState state = getPlacementState(context);
        return state == null ? null : new VehiclePlacement.Placement(context.getClickedPos(), state);
    }

    @Override
    public BlockPlaceContext updatePlacementContext(BlockPlaceContext context) {
        BlockState state = getBlock().getStateForPlacement(context);
        if (state == null) return null;
        var placement = VehiclePlacement.resolve(context, (TrainPanelBlock) getBlock(), state);
        return new PlacementContext(context, placement);
    }

    @Override
    protected BlockState getPlacementState(BlockPlaceContext context) {
        if (!(context instanceof PlacementContext resolved)) return super.getPlacementState(context);
        return canPlace(context, resolved.placement.state()) ? resolved.placement.state() : null;
    }

    @Override
    protected boolean canPlace(BlockPlaceContext context, BlockState state) {
        if (!super.canPlace(context, state)) return false;
        boolean[] available = {true};
        ((TrainPanelBlock) getBlock()).walkAllValidPos(context.getLevel(), context.getClickedPos(), state,
                context.getPlayer(), context.getHand(), null, (level, parent, parentState, pos, current, player, hand, hit) -> {
                    if (!level.isInWorldBounds(pos) || !level.getWorldBorder().isWithinBounds(pos)
                            || !current.canBeReplaced(context)) available[0] = false;
                    return InteractionResult.PASS;
                });
        return available[0];
    }

    private static final class PlacementContext extends BlockPlaceContext {
        private final VehiclePlacement.Placement placement;

        private PlacementContext(BlockPlaceContext source, VehiclePlacement.Placement placement) {
            super(source.getLevel(), source.getPlayer(), source.getHand(), source.getItemInHand(),
                    new net.minecraft.world.phys.BlockHitResult(source.getClickLocation(), source.getClickedFace(), placement.pos(), source.isInside()));
            this.placement = placement;
        }

        @Override public net.minecraft.core.BlockPos getClickedPos() {
            // The superclass queries the target before this field is initialized.
            return placement == null ? super.getClickedPos() : placement.pos();
        }
    }
}

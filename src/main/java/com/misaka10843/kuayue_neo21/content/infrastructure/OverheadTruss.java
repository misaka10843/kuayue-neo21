package com.misaka10843.kuayue_neo21.content.infrastructure;

import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainPanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.CompanyTrainPanel;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.TrainPanelProperties.ShapeType;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.TrainPanelShapes;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class OverheadTruss extends TrainPanelBlock {
    private final Vec3 begin;
    private final Vec3 end;
    private final ShapeType shape;

    public OverheadTruss(Properties properties, Vec3 begin, Vec3 end) { this(properties, begin, end, ShapeType.BIG_TRUSS); }

    protected OverheadTruss(Properties properties, Vec3 begin, Vec3 end, ShapeType shape) {
        super(properties, new Vec2((float) begin.x, (float) begin.y), new Vec2((float) end.x, (float) end.y));
        this.begin = begin;
        this.end = end;
        this.shape = shape;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        return switch (shape) {
            case SMALL_TRUSS -> TrainPanelShapes.getOverheadSmallTrussShape(facing);
            case PILLAR_TRUSS -> TrainPanelShapes.getOverheadPillarTrussShape(facing);
            default -> TrainPanelShapes.getOverheadBigTrussShape(facing);
        };
    }

    @Override public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) { return getShape(state, level, pos, context); }

    @Override
    public List<BlockPos> getOccupiedPositions(BlockGetter level, BlockPos root, BlockState state) {
        List<BlockPos> positions = new ArrayList<>();
        for (int x = (int) begin.x; x < end.x; x++) {
            for (int y = (int) begin.y; y < end.y; y++) {
                for (int z = (int) begin.z; z < end.z; z++) {
                    positions.add(switch (state.getValue(FACING)) {
                        case NORTH -> root.offset(x, y, z);
                        case SOUTH -> root.offset(-x, y, -z);
                        case WEST -> root.offset(z, y, -x);
                        case EAST -> root.offset(-z, y, x);
                        default -> root;
                    });
                }
            }
        }
        if (!positions.contains(root)) positions.add(root);
        return positions;
    }

    @Override
    public BlockState generateCompanyState(Direction direction, DoorHingeSide hinge, boolean open) {
        return super.generateCompanyState(direction, hinge, open).setValue(CompanyTrainPanel.SHAPE_TYPE, shape);
    }
}

package com.misaka10843.kuayue_neo21.legacy.block.seat;

import com.simibubi.create.content.contraptions.actors.seat.SeatBlock;
import com.simibubi.create.content.contraptions.actors.seat.SeatEntity;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import com.misaka10843.kuayue_neo21.legacy.initial.AllBlocks;
import com.misaka10843.kuayue_neo21.legacy.utils.DirectionUtil;

import java.util.List;

public class YZSeatBlock extends SeatBlock implements IWrenchable, IBE<SeatBlockEntity> {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    protected static final VoxelShape BASE_AABB = Block.box(0, 4, 0, 16, 8, 16);
    
    
    public final int seatSize;
    public final OffsetFunction offsetFunction;

    public YZSeatBlock(Properties properties, DyeColor color, int seatSize, OffsetFunction offsetFunction, boolean inCreativeTab) {
        super(properties, color);
        this.seatSize = seatSize;
        this.offsetFunction = offsetFunction;
    }

    public YZSeatBlock(@NotNull Properties settings, int seatSize, OffsetFunction offsetFunction) {
        this(settings.noOcclusion(), DyeColor.BLUE, seatSize, offsetFunction, true);
        registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(
            BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(FACING)) {
            case EAST, WEST -> weShape();
            default -> snShape();
        };
    }

    @Override
    protected void createBlockStateDefinition(
            @NotNull StateDefinition.Builder<Block, BlockState> stateManager) {
        super.createBlockStateDefinition(stateManager.add(FACING));
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState()
                .setValue(FACING, ctx.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, false);
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    public VoxelShape snShape() {
        
        return BASE_AABB;
    }

    public VoxelShape weShape() {
        
        return BASE_AABB;
    }

    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult useWithoutItem(
            BlockState state,
            Level world,
            BlockPos pos,
            Player player,
            BlockHitResult p_225533_6_) {
        InteractionHand hand = InteractionHand.MAIN_HAND;

        if (player.isShiftKeyDown()) return InteractionResult.PASS;

        if (world.isClientSide) return InteractionResult.SUCCESS;

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof SeatBlockEntity)) return InteractionResult.PASS;
        SeatBlockEntity seat = (SeatBlockEntity) blockEntity;

        sitAvailable(world, state, pos, player);
        return InteractionResult.SUCCESS;
    }

    public void sitAvailable(Level world, BlockState state, BlockPos pos, Entity passenger) {
        if (world.isClientSide || !(world.getBlockEntity(pos) instanceof SeatBlockEntity seat)) return;
        seat.clearAllSeat();
        boolean[] occupied = new boolean[seatSize];
        int previous = -1;
        for (SeatEntity entity : world.getEntitiesOfClass(SeatEntity.class, new AABB(pos).inflate(1.5))) {
            if (entity.getPassengers().isEmpty()) continue;
            if (entity instanceof M1SeatEntity meter ? !pos.equals(meter.anchor()) : !entity.blockPosition().equals(pos)) continue;
            int index = getIndex(state, entity.position().subtract(Vec3.atLowerCornerOf(pos).add(.5, 0, .5)));
            if (index < 0) continue;
            occupied[index] = true;
            seat.fillPassenger(index, entity.getFirstPassenger().getUUID());
            if (entity.hasPassenger(passenger)) previous = index;
        }
        for (int step = 0; step < seatSize; step++) {
            int index = (previous + 1 + step) % seatSize;
            if (occupied[index]) continue;
            resolveSitDown(world, state, pos, passenger, index);
            if (previous >= 0) seat.removePassenger(previous);
            seat.fillPassenger(index, passenger.getUUID());
            return;
        }
    }

    @Override
    public void fallOn(Level p_152426_, BlockState p_152427_, BlockPos p_152428_, Entity p_152429_, float p_152430_) {
        super.fallOn(p_152426_, p_152427_, p_152428_, p_152429_, p_152430_);
    }

    public void resolveSitDown(
            Level level, BlockState state, BlockPos pos, Entity entity, int index) {
        Direction direction = state.getValue(FACING);
        sitDown(level, pos, entity, getOffset(state, index));
    }

    public Vec3 getOffset(BlockState state, int index) {
        return offsetFunction.getOffset(state, index);
    }

    public int getIndex(BlockState state, Vec3 position) {
        for(int i = 0; i < getSeatSize(); i++) {
            if (getOffset(state, i).distanceToSqr(position) < 0.000001)
                return i;
        }
        return -1;
    }

    public void sitDown(
            Level world, BlockPos pos, Entity entity, Vec3 offset) {
        if (world.isClientSide) return;
        SeatEntity seat = com.simibubi.create.AllEntityTypes.SEAT.create(world);
        seat.setPos(
                (float) pos.getX() + offset.x() + .5,
                (float) pos.getY() + offset.y(),
                (float) pos.getZ() + offset.z() + .5);
        world.addFreshEntity(seat);
        entity.startRiding(seat, true);
        if (entity instanceof TamableAnimal ta) ta.setInSittingPose(true);
    }

    public int getSeatSize() {
        return seatSize;
    }

    @Override
    public Class<SeatBlockEntity> getBlockEntityClass() {
        return SeatBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends SeatBlockEntity> getBlockEntityType() {
        return AllBlocks.SEAT_BLOCK_ENTITY.getType();
    }

    public interface OffsetFunction {
        Vec3 getOffset(BlockState state, int index);
    }
}

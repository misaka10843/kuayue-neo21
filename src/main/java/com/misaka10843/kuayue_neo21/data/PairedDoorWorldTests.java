package com.misaka10843.kuayue_neo21.data;

import com.misaka10843.kuayue_neo21.content.PortRegistration;
import com.misaka10843.kuayue_neo21.content.VehicleDoorInteraction;
import com.simibubi.create.AllEntityTypes;
import com.simibubi.create.api.behaviour.interaction.MovingInteractionBehaviour;
import com.simibubi.create.content.trains.entity.CarriageContraption;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import java.util.ArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("kuayue")
@PrefixGameTestTemplate(false)
public final class PairedDoorWorldTests {
    private record Pair(BlockPos first, BlockPos second) {}

    @GameTest(template = "schematic_room", timeoutTicks = 50)
    public static void pairedDoorsAndUpperClicks(GameTestHelper helper) {
        var pairs = new ArrayList<Pair>();
        String[] ids = {"door_25b", "door_sliding_25g", "freight_c70_door"};
        for (int type = 0; type < ids.length; type++) {
            int index = 0;
            for (Direction facing : Direction.Plane.HORIZONTAL) {
                BlockPos first = new BlockPos(4 + index++ * 6, 2, 3 + type * 4);
                BlockPos second = first.relative(facing.getCounterClockWise());
                var state = state(ids[type], facing, DoorHingeSide.LEFT);
                helper.setBlock(first, state);
                helper.setBlock(second, state.setValue(BlockStateProperties.DOOR_HINGE, DoorHingeSide.RIGHT));
                pairs.add(new Pair(first, second));
            }
        }
        helper.runAtTickTime(3, () -> {
            for (Pair pair : pairs) {
                helper.useBlock(pair.first);
                assertOpen(helper, pair, true);
                helper.useBlock(pair.second.above());
                helper.assertTrue(!helper.getBlockState(pair.first).getValue(BlockStateProperties.OPEN), "Upper click did not close root: " + helper.getBlockState(pair.first));
                assertOpen(helper, pair, false);
                helper.setBlock(pair.second, helper.getBlockState(pair.second).setValue(BlockStateProperties.OPEN, true));
                helper.useBlock(pair.first);
                helper.assertTrue(helper.getBlockState(pair.first).getValue(BlockStateProperties.OPEN), "Desynced click did not open root: " + helper.getBlockState(pair.first));
                assertOpen(helper, pair, true);
            }
            helper.succeed();
        });
    }

    @GameTest(template = "schematic_room")
    public static void placementAndUnpairedNeighbors(GameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.CREATIVE);
        int index = 0;
        for (Direction facing : Direction.Plane.HORIZONTAL) {
            BlockPos first = new BlockPos(4 + index++ * 6, 2, 4);
            BlockPos second = first.relative(facing.getCounterClockWise());
            BlockState door = state("door_25b", facing, DoorHingeSide.LEFT);
            helper.setBlock(first, door);
            helper.setBlock(second.below(), Blocks.STONE);
            player.setYRot(facing.getOpposite().toYRot());
            var support = helper.absolutePos(second.below());
            var hit = new BlockHitResult(Vec3.atCenterOf(support).add(0, 0.5, 0), Direction.UP, support, false);
            var context = new BlockPlaceContext(helper.getLevel(), player, InteractionHand.MAIN_HAND, new ItemStack(door.getBlock()), hit);
            var placed = door.getBlock().getStateForPlacement(context);
            helper.assertTrue(placed != null && placed.getValue(BlockStateProperties.DOOR_HINGE) == DoorHingeSide.RIGHT,
                    "Adjacent door placement did not select opposite hinge");
            helper.setBlock(second, door);
            helper.useBlock(first);
            helper.assertBlockProperty(first, BlockStateProperties.OPEN, true);
            helper.assertBlockProperty(second, BlockStateProperties.OPEN, false);
            helper.setBlock(first, door);
            helper.setBlock(second, door.setValue(BlockStateProperties.DOOR_HINGE, DoorHingeSide.RIGHT)
                    .setValue(BlockStateProperties.HORIZONTAL_FACING, facing.getOpposite()));
            helper.useBlock(first);
            helper.assertBlockProperty(second, BlockStateProperties.OPEN, false);
        }
        helper.succeed();
    }

    @GameTest(template = "schematic_room")
    public static void mountedPairsSyncOwnedCompanions(GameTestHelper helper) throws Exception {
        var contraption = new CarriageContraption(Direction.EAST);
        contraption.bounds = new AABB(0, 0, 0, 32, 6, 8);
        var pairs = new ArrayList<Pair>();
        int index = 0;
        for (Direction facing : Direction.Plane.HORIZONTAL) {
            BlockPos first = new BlockPos(4 + index++ * 6, 1, 4);
            BlockPos second = first.relative(facing.getCounterClockWise());
            BlockState state = state("door_sliding_25g", facing, DoorHingeSide.LEFT);
            contraption.getBlocks().put(first, new StructureBlockInfo(first, state, null));
            contraption.getBlocks().put(second, new StructureBlockInfo(second, state.setValue(BlockStateProperties.DOOR_HINGE, DoorHingeSide.RIGHT), null));
            for (var root : java.util.List.of(first, second)) {
                var panel = (com.misaka10843.kuayue_neo21.legacy.block.panels.TrainPanelBlock) state.getBlock();
                var rootState = contraption.getBlocks().get(root).state();
                var companion = panel.generateCompanyState(facing, rootState.getValue(BlockStateProperties.DOOR_HINGE), false);
                var tag = new CompoundTag();
                tag.putInt("px", 0); tag.putInt("py", -1); tag.putInt("pz", 0);
                contraption.getBlocks().put(root.above(), new StructureBlockInfo(root.above(), companion, tag));
            }
            pairs.add(new Pair(first, second));
        }
        contraption.getBlocks().forEach((pos, info) -> {
            var behavior = MovingInteractionBehaviour.REGISTRY.get(info.state());
            if (behavior != null) contraption.getInteractors().put(pos, behavior);
        });
        class DoorEntity extends CarriageContraptionEntity {
            DoorEntity(CarriageContraption scene) {
                super(AllEntityTypes.CARRIAGE_CONTRAPTION.get(), helper.getLevel());
                setContraption(scene);
            }
        }
        var entity = new DoorEntity(contraption);
        var bogey = (com.simibubi.create.content.trains.bogey.AbstractBogeyBlock<?>) BuiltInRegistries.BLOCK.get(PortRegistration.resource("pk209p_bogey"));
        var carriage = new com.simibubi.create.content.trains.entity.Carriage(
                new com.simibubi.create.content.trains.entity.CarriageBogey(bogey, false, new CompoundTag()), null, 0);
        var carriageField = CarriageContraptionEntity.class.getDeclaredField("carriage");
        carriageField.setAccessible(true);
        carriageField.set(entity, carriage);
        carriage.getDimensional(helper.getLevel()).entity = new java.lang.ref.WeakReference<>(entity);
        var player = helper.makeMockPlayer(GameType.CREATIVE);
        var interaction = new VehicleDoorInteraction();
        for (Pair pair : pairs) {
            helper.assertTrue(interaction.handlePlayerInteraction(player, InteractionHand.MAIN_HAND, pair.first, entity), "Mounted first door did not interact");
            for (var root : java.util.List.of(pair.first, pair.second)) {
                helper.assertTrue(contraption.getBlocks().get(root).state().getValue(BlockStateProperties.OPEN)
                        && contraption.getBlocks().get(root.above()).state().getValue(BlockStateProperties.OPEN), "Mounted companion did not follow paired roots: "
                        + contraption.getBlocks().get(root) + " / " + contraption.getBlocks().get(root.above()));
            }
            helper.assertTrue(entity.handlePlayerInteraction(player, pair.second.above(), Direction.NORTH, InteractionHand.MAIN_HAND), "Mounted upper door did not route to root");
            for (var root : java.util.List.of(pair.first, pair.second))
                helper.assertTrue(!contraption.getBlocks().get(root).state().getValue(BlockStateProperties.OPEN)
                        && !contraption.getBlocks().get(root.above()).state().getValue(BlockStateProperties.OPEN), "Mounted upper click did not close both doors");
        }
        helper.succeed();
    }

    private static void assertOpen(GameTestHelper helper, Pair pair, boolean open) {
        for (var root : java.util.List.of(pair.first, pair.second)) {
            helper.assertBlockProperty(root, BlockStateProperties.OPEN, open);
            helper.assertBlockProperty(root.above(), BlockStateProperties.OPEN, open);
        }
    }

    private static BlockState state(String id, Direction facing, DoorHingeSide hinge) {
        return BuiltInRegistries.BLOCK.get(PortRegistration.resource(id)).defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, facing).setValue(BlockStateProperties.DOOR_HINGE, hinge)
                .setValue(BlockStateProperties.OPEN, false);
    }
}

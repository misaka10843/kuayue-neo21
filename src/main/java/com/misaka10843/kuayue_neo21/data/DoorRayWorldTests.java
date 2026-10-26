package com.misaka10843.kuayue_neo21.data;

import com.misaka10843.kuayue_neo21.content.PortRegistration;
import com.misaka10843.kuayue_neo21.content.VehicleContraptionRaycast;
import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainPanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.CompanyTrainPanel;
import com.misaka10843.kuayue_neo21.legacy.block.panels.door.CustomRenderedDoorBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.end_face.CustomRenderedEndfaceBlock;
import com.simibubi.create.AllEntityTypes;
import com.simibubi.create.api.behaviour.interaction.MovingInteractionBehaviour;
import com.simibubi.create.content.trains.entity.CarriageContraption;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("kuayue")
@PrefixGameTestTemplate(false)
public final class DoorRayWorldTests {
    @GameTest(template = "schematic_room", timeoutTicks = 200)
    public static void mountedSlidingDoorsCloseFromEveryHeight(GameTestHelper helper) throws Exception {
        int rays = 0;
        var player = helper.makeMockPlayer(GameType.CREATIVE);
        for (var entry : PortRegistration.ENTRIES) {
            boolean sliding = entry.get() instanceof CustomRenderedDoorBlock door && door.isSlideDoor()
                    || entry.get() instanceof CustomRenderedEndfaceBlock end && end.DOOR_TYPE.isSliding();
            if (!sliding) continue;
            for (Direction facing : Direction.Plane.HORIZONTAL) {
                var closed = entry.get().defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, facing)
                        .setValue(BlockStateProperties.OPEN, false);
                for (var hinge : closed.hasProperty(BlockStateProperties.DOOR_HINGE) ? DoorHingeSide.values() : new DoorHingeSide[]{DoorHingeSide.LEFT}) {
                    if (closed.hasProperty(BlockStateProperties.DOOR_HINGE)) closed = closed.setValue(BlockStateProperties.DOOR_HINGE, hinge);
                    var contraption = new CarriageContraption(Direction.EAST);
                    contraption.bounds = new AABB(-16, -16, -16, 16, 16, 16);
                    var entity = bind(helper, contraption);
                    BlockPos root = new BlockPos(4, 2, 4);
                    addDoor(contraption, root, closed.setValue(BlockStateProperties.OPEN, true));
                    String decoyId = entry.id().equals("door_sliding_25g") ? "door_sliding_25t" : "door_sliding_25g";
                    var decoyState = net.minecraft.core.registries.BuiltInRegistries.BLOCK.get(PortRegistration.resource(decoyId)).defaultBlockState()
                            .setValue(BlockStateProperties.HORIZONTAL_FACING, facing.getClockWise()).setValue(BlockStateProperties.OPEN, false);
                    var decoy = root.relative(facing, 2);
                    addDoor(contraption, decoy, decoyState);
                    var world = contraption.getContraptionWorld();
                    var bounds = closed.getShape(world, root).bounds();
                    var normal = Vec3.atLowerCornerOf(facing.getOpposite().getNormal());
                    net.minecraft.world.phys.BlockHitResult last = null;
                    for (double height : new double[]{bounds.minY + 0.1, (bounds.minY + bounds.maxY) / 2, bounds.maxY - 0.05}) {
                        var point = new Vec3((bounds.minX + bounds.maxX) / 2, height, (bounds.minZ + bounds.maxZ) / 2).add(Vec3.atLowerCornerOf(root));
                        var origin = point.add(normal.scale(3));
                        var target = point.subtract(normal.scale(3));
                        var expected = contraption.getBlocks().get(root).state().getShape(world, root).clip(origin, target, root);
                        var hit = VehicleContraptionRaycast.trace(origin, target, contraption);
                        helper.assertTrue(expected != null && hit != null, "Open sliding door cannot be selected: " + entry.id() + "/" + facing + "/" + height);
                        helper.assertTrue(hit.getLocation().distanceToSqr(expected.getLocation()) < 1e-8,
                                "Sliding door selected a surface behind the model: " + entry.id() + "/" + facing);
                        var owner = contraption.getBlocks().get(hit.getBlockPos()).state().getBlock() instanceof CompanyTrainPanel
                                ? CompanyTrainPanel.getParentPos(world, hit.getBlockPos()) : hit.getBlockPos();
                        helper.assertTrue(root.equals(owner), "Ray selected another door: " + entry.id());
                        last = hit;
                        rays++;
                    }
                    helper.assertTrue(entity.handlePlayerInteraction(player, last.getBlockPos(), last.getDirection(), InteractionHand.MAIN_HAND), "Upper sliding-door click was rejected");
                    helper.assertTrue(!contraption.getBlocks().get(root).state().getValue(BlockStateProperties.OPEN), "Upper click did not close door");
                    helper.assertTrue(!contraption.getBlocks().get(decoy).state().getValue(BlockStateProperties.OPEN), "Quarter-turn decoy door was activated");
                }
            }
        }
        helper.assertTrue(rays >= 48, "Sliding-door ray coverage is incomplete");
        helper.succeed();
    }

    private static void addDoor(CarriageContraption contraption, BlockPos root, BlockState state) {
        var panel = (TrainPanelBlock) state.getBlock();
        var world = contraption.getContraptionWorld();
        var hinge = state.hasProperty(BlockStateProperties.DOOR_HINGE) ? state.getValue(BlockStateProperties.DOOR_HINGE) : DoorHingeSide.LEFT;
        contraption.getBlocks().put(root, new StructureBlockInfo(root, state, null));
        for (var part : panel.getOccupiedPositions(world, root, state)) {
            if (part.equals(root) || contraption.getBlocks().containsKey(part)) continue;
            var partState = panel.generateCompanyState(state.getValue(BlockStateProperties.HORIZONTAL_FACING), hinge, state.getValue(BlockStateProperties.OPEN));
            var offset = root.subtract(part);
            var tag = new CompoundTag();
            tag.putInt("px", offset.getX()); tag.putInt("py", offset.getY()); tag.putInt("pz", offset.getZ());
            contraption.getBlocks().put(part, new StructureBlockInfo(part, partState, tag));
        }
        contraption.getBlocks().forEach((pos, info) -> {
            var interaction = MovingInteractionBehaviour.REGISTRY.get(info.state());
            if (interaction != null) contraption.getInteractors().put(pos, interaction);
        });
    }

    private static CarriageContraptionEntity bind(GameTestHelper helper, CarriageContraption contraption) throws Exception {
        class BoundEntity extends CarriageContraptionEntity {
            BoundEntity(CarriageContraption scene) { super(AllEntityTypes.CARRIAGE_CONTRAPTION.get(), helper.getLevel()); setContraption(scene); }
        }
        var entity = new BoundEntity(contraption);
        var block = (com.simibubi.create.content.trains.bogey.AbstractBogeyBlock<?>) net.minecraft.core.registries.BuiltInRegistries.BLOCK.get(PortRegistration.resource("pk209p_bogey"));
        var carriage = new com.simibubi.create.content.trains.entity.Carriage(
                new com.simibubi.create.content.trains.entity.CarriageBogey(block, false, new CompoundTag()), null, 0);
        var field = CarriageContraptionEntity.class.getDeclaredField("carriage");
        field.setAccessible(true);
        field.set(entity, carriage);
        carriage.getDimensional(helper.getLevel()).entity = new java.lang.ref.WeakReference<>(entity);
        return entity;
    }
}

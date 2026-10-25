package com.misaka10843.kuayue_neo21.data;

import com.misaka10843.kuayue_neo21.content.PortRegistration;
import com.misaka10843.kuayue_neo21.content.coupling.TrainMass;
import com.misaka10843.kuayue_neo21.mixin.CarriageDataAccessor;
import com.misaka10843.kuayue_neo21.mixin.MountedStorageAccessor;
import com.simibubi.create.content.trains.bogey.AbstractBogeyBlock;
import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.CarriageBogey;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.entity.TravellingPoint;
import com.simibubi.create.content.trains.graph.TrackEdge;
import com.simibubi.create.content.trains.graph.TrackGraph;
import com.simibubi.create.content.trains.graph.TrackNode;
import com.simibubi.create.content.trains.graph.TrackNodeLocation;
import com.simibubi.create.content.trains.track.TrackMaterial;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.createmod.catnip.data.Pair;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("kuayue")
@PrefixGameTestTemplate(false)
public final class TrainCollisionWorldTests {
    private static final class CollisionTrain extends Train {
        private Train other;
        private final Vec3 contact;

        CollisionTrain(TrackGraph graph, Carriage carriage, Vec3 contact) {
            super(UUID.randomUUID(), null, graph, new ArrayList<>(List.of(carriage)), new ArrayList<>(), false, 0);
            this.contact = contact;
        }

        @Override public Pair<Train, Vec3> findCollidingTrain(Level level, Vec3 start, Vec3 end, ResourceKey<Level> dimension) {
            return Pair.of(other, contact);
        }
    }

    private record Fixture(CollisionTrain first, CollisionTrain second, TrackGraph graph, Method collide) {
        void run(GameTestHelper helper) {
            try { collide.invoke(first, helper.getLevel(), first.carriages.getFirst()); }
            catch (ReflectiveOperationException error) { throw new IllegalStateException("Native collision hook failed", error); }
        }
    }

    @GameTest(template = "schematic_room", timeoutTicks = 30)
    public static void weightedImpactAndCooldown(GameTestHelper helper) throws Exception {
        Fixture fixture = fixture(helper, true, false, 9, 1);
        fixture.first.speed = 0.1;
        fixture.run(helper);
        helper.assertTrue(Math.abs(fixture.first.speed - 0.08) < 1e-8 && Math.abs(fixture.second.speed - 0.18) < 1e-8,
                "Native collision did not use structural mass or assigned speeds to wrong trains");
        helper.assertTrue(fixture.first.graph == fixture.graph && fixture.second.graph == fixture.graph
                && !fixture.first.derailed && !fixture.second.derailed, "Soft impact derailed a train");
        helper.assertTrue(fixture.first.runtime.paused && fixture.second.runtime.paused, "Collision schedule did not pause");
        helper.assertTrue(!((MountedStorageAccessor) fixture.first.carriages.getFirst().storage).kuayue$isInitialized(),
                "Mass lookup changed uninitialized cargo");
        fixture.first.speed = 0.1;
        fixture.second.speed = 0.05;
        fixture.run(helper);
        helper.assertTrue(fixture.first.speed == 0.1 && fixture.second.speed == 0.05, "Same contact received a duplicate impulse");
        helper.runAtTickTime(5, () -> {
            fixture.run(helper);
            helper.assertTrue(Math.abs(fixture.first.speed - 0.09) < 1e-8 && Math.abs(fixture.second.speed - 0.14) < 1e-8,
                    "A later collision was suppressed permanently");
            helper.succeed();
        });
    }

    @GameTest(template = "schematic_room")
    public static void opposingFacingImpact(GameTestHelper helper) throws Exception {
        Fixture fixture = fixture(helper, true, true, 1, 1);
        fixture.first.speed = 0.1;
        fixture.second.speed = 0.1;
        fixture.run(helper);
        helper.assertTrue(Math.abs(fixture.first.speed + 0.1) < 1e-8 && Math.abs(fixture.second.speed + 0.1) < 1e-8,
                "Opposing train axes did not rebound in world coordinates");
        helper.assertTrue(fixture.first.graph == fixture.graph && fixture.second.graph == fixture.graph, "Head-on rebound lost graph");
        helper.succeed();
    }

    @GameTest(template = "schematic_room")
    public static void mountedCargoChangesCollisionMass(GameTestHelper helper) throws Exception {
        Fixture fixture = fixture(helper, true, false, 4, 1);
        var chestPos = new net.minecraft.core.BlockPos(2, 2, 2);
        helper.setBlock(chestPos, Blocks.CHEST);
        var chest = (net.minecraft.world.level.block.entity.ChestBlockEntity) helper.getBlockEntity(chestPos);
        chest.setItem(0, new net.minecraft.world.item.ItemStack(net.minecraft.world.item.Items.IRON_INGOT, 10));
        var tankPos = new net.minecraft.core.BlockPos(4, 2, 2);
        helper.setBlock(tankPos, com.simibubi.create.AllBlocks.FLUID_TANK.get());
        var tank = (com.simibubi.create.content.fluids.tank.FluidTankBlockEntity) helper.getBlockEntity(tankPos);
        tank.getTankInventory().setFluid(new net.neoforged.neoforge.fluids.FluidStack(net.minecraft.world.level.material.Fluids.WATER, 1000));
        var cargo = fixture.first.carriages.getFirst().storage;
        cargo.addBlock(helper.getLevel(), chest.getBlockState(), helper.absolutePos(chestPos), chestPos, chest);
        cargo.addBlock(helper.getLevel(), tank.getBlockState(), helper.absolutePos(tankPos), tankPos, tank);
        cargo.initialize();
        helper.assertTrue(Math.abs(TrainMass.of(fixture.first) - 410) < 1e-8, "Mounted items/fluids did not contribute source mass weights");
        helper.assertTrue(cargo.getMountedItems().getStackInSlot(0).getCount() == 10
                && cargo.getFluids().getFluidInTank(0).getAmount() == 1000, "Mass lookup changed cargo contents");
        helper.succeed();
    }

    @GameTest(template = "schematic_room")
    public static void unrelatedTrainsKeepNativeCollision(GameTestHelper helper) throws Exception {
        Fixture fixture = fixture(helper, false, false, 1, 1);
        fixture.first.speed = 0.1;
        fixture.run(helper);
        helper.assertTrue(fixture.first.derailed && fixture.second.derailed, "Unrelated trains lost native crash behavior");
        helper.succeed();
    }

    private static Fixture fixture(GameTestHelper helper, boolean ported, boolean opposite, int firstMass, int secondMass) throws Exception {
        var graph = new TrackGraph();
        Vec3 origin = helper.absolutePos(new net.minecraft.core.BlockPos(2, 2, 2)).getCenter();
        var node1 = new TrackNode(new TrackNodeLocation(origin).in(helper.getLevel()), 1, new Vec3(0, 1, 0));
        var node2 = new TrackNode(new TrackNodeLocation(origin.add(100, 0, 0)).in(helper.getLevel()), 2, new Vec3(0, 1, 0));
        graph.addNode(node1);
        graph.addNode(node2);
        var edge = new TrackEdge(node1, node2, null, TrackMaterial.ANDESITE);
        var reverse = new TrackEdge(node2, node1, null, TrackMaterial.ANDESITE);
        graph.putConnection(node1, node2, edge);
        graph.putConnection(node2, node1, reverse);
        Carriage first = carriage(edge, 50, 48, firstMass, ported);
        Carriage second = opposite ? carriage(reverse, 50, 48, secondMass, ported) : carriage(edge, 52, 50, secondMass, ported);
        Vec3 contact = origin.add(50, 0, 0);
        var a = new CollisionTrain(graph, first, contact);
        var b = new CollisionTrain(graph, second, contact);
        a.other = b;
        b.other = a;
        Method method = Train.class.getDeclaredMethod("collideWithOtherTrains", Level.class, Carriage.class);
        method.setAccessible(true);
        return new Fixture(a, b, graph, method);
    }

    private static Carriage carriage(TrackEdge edge, double front, double rear, int mass, boolean ported) {
        var block = (AbstractBogeyBlock<?>) BuiltInRegistries.BLOCK.get(PortRegistration.resource("pk209p_bogey"));
        var bogey = new CarriageBogey(block, false, new CompoundTag(),
                new TravellingPoint(edge.node1, edge.node2, edge, front, false),
                new TravellingPoint(edge.node1, edge.node2, edge, rear, false));
        var carriage = new Carriage(bogey, null, 0);
        var saved = ((CarriageDataAccessor) carriage).kuayue$savedEntity();
        var blocks = new CompoundTag();
        var palette = new ListTag();
        palette.add(NbtUtils.writeBlockState(ported ? block.defaultBlockState() : Blocks.OAK_PLANKS.defaultBlockState()));
        blocks.put("Palette", palette);
        var entries = new ListTag();
        for (int i = 0; i < mass; i++) entries.add(new CompoundTag());
        blocks.put("BlockList", entries);
        var contraption = new CompoundTag();
        contraption.put("Blocks", blocks);
        saved.put("Contraption", contraption);
        return carriage;
    }
}

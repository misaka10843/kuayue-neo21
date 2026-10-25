package com.misaka10843.kuayue_neo21.data;

import com.misaka10843.kuayue_neo21.content.PortRegistration;
import com.misaka10843.kuayue_neo21.content.coupling.TrainTopology;
import com.simibubi.create.AllEntityTypes;
import com.simibubi.create.content.trains.GlobalRailwayManager;
import com.simibubi.create.content.trains.bogey.AbstractBogeyBlock;
import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.CarriageBogey;
import com.simibubi.create.content.trains.entity.CarriageContraption;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import com.simibubi.create.content.trains.entity.Train;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("kuayue")
@PrefixGameTestTemplate(false)
public final class CouplingStorageWorldTests {
    @GameTest(template = "schematic_room")
    public static void couplingPreservesClientStorage(GameTestHelper helper) throws Exception {
        var manager = new GlobalRailwayManager();
        var block = (AbstractBogeyBlock<?>) BuiltInRegistries.BLOCK.get(PortRegistration.resource("pk209p_bogey"));
        var first = new Carriage(new CarriageBogey(block, false, new CompoundTag()), null, 0);
        var second = new Carriage(new CarriageBogey(block, false, new CompoundTag()), null, 0);
        var front = new Train(UUID.randomUUID(), null, null, new ArrayList<>(List.of(first)), new ArrayList<>(), false, 0);
        var rear = new Train(UUID.randomUUID(), null, null, new ArrayList<>(List.of(second)), new ArrayList<>(), false, 0);
        manager.addTrain(front);
        manager.addTrain(rear);
        var entities = new ArrayList<CarriageContraptionEntity>();
        var field = CarriageContraptionEntity.class.getDeclaredField("carriage");
        field.setAccessible(true);
        class BoundEntity extends CarriageContraptionEntity {
            BoundEntity() {
                super(AllEntityTypes.CARRIAGE_CONTRAPTION.get(), helper.getLevel());
                var contraption = new CarriageContraption(Direction.EAST);
                contraption.bounds = new net.minecraft.world.phys.AABB(0, 0, 0, 1, 1, 1);
                setContraption(contraption);
            }
        }
        for (Carriage carriage : List.of(first, second)) {
            var entity = new BoundEntity();
            // Client network carriages keep storage in the contraption, not their empty cargo manager.
            field.set(entity, carriage);
            carriage.getDimensional(helper.getLevel()).entity = new WeakReference<>(entity);
            entities.add(entity);
        }
        var originalStorage = entities.stream().map(entity -> entity.getContraption().getStorage()).toList();
        for (int attempt = 0; attempt < 3; attempt++) {
            helper.assertTrue(TrainTopology.merge(manager, front, rear, 8), "Native train merge failed");
            helper.assertTrue(entities.get(1).trainId.equals(front.id) && entities.get(1).carriageIndex == 1,
                    "Merge did not update client entity identity");
            rear = TrainTopology.split(manager, front, UUID.randomUUID(), 0);
            helper.assertTrue(rear != null && entities.get(1).trainId.equals(rear.id) && entities.get(1).carriageIndex == 0,
                    "Split did not update client entity identity");
            for (int i = 0; i < entities.size(); i++) {
                var contraption = entities.get(i).getContraption();
                helper.assertTrue(contraption.getStorage() == originalStorage.get(i), "Coupling replaced client storage");
                contraption.tickStorage(entities.get(i));
            }
        }
        helper.succeed();
    }
}

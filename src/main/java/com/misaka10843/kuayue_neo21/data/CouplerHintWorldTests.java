package com.misaka10843.kuayue_neo21.data;

import com.misaka10843.kuayue_neo21.content.PortRegistration;
import com.misaka10843.kuayue_neo21.content.coupling.CouplerInteraction;
import com.simibubi.create.AllEntityTypes;
import com.simibubi.create.content.trains.bogey.AbstractBogeyBlock;
import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.CarriageBogey;
import com.simibubi.create.content.trains.entity.CarriageContraption;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.graph.TrackGraph;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("kuayue")
@PrefixGameTestTemplate(false)
public final class CouplerHintWorldTests {
    @GameTest(template = "schematic_room")
    public static void couplerHintsMatchOperationEligibility(GameTestHelper helper) throws Exception {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var bogey = (AbstractBogeyBlock<?>) BuiltInRegistries.BLOCK.get(PortRegistration.resource("pk209p_bogey"));
        var coupler = BuiltInRegistries.BLOCK.get(PortRegistration.resource("22_coupler")).defaultBlockState();
        var field = CarriageContraptionEntity.class.getDeclaredField("carriage");
        field.setAccessible(true);
        class TestEntity extends CarriageContraptionEntity {
            TestEntity(CarriageContraption contraption) {
                super(AllEntityTypes.CARRIAGE_CONTRAPTION.get(), helper.getLevel());
                setContraption(contraption);
            }
        }
        for (var facing : Direction.Plane.HORIZONTAL) {
            var carriages = new ArrayList<Carriage>();
            var entities = new ArrayList<CarriageContraptionEntity>();
            var leading = BlockPos.ZERO.relative(facing, -2);
            var trailing = BlockPos.ZERO.relative(facing, 2);
            for (int i = 0; i < 2; i++) {
                var carriage = new Carriage(new CarriageBogey(bogey, false, new CompoundTag()), null, 0);
                carriages.add(carriage);
                var contraption = new CarriageContraption(facing);
                contraption.bounds = new net.minecraft.world.phys.AABB(-3, 0, -3, 3, 1, 3);
                contraption.getBlocks().put(leading, new StructureBlockInfo(leading, coupler, null));
                contraption.getBlocks().put(trailing, new StructureBlockInfo(trailing, coupler, null));
                var entity = new TestEntity(contraption);
                entity.carriageIndex = i;
                field.set(entity, carriage);
                carriage.getDimensional(helper.getLevel()).entity = new WeakReference<>(entity);
                entities.add(entity);
            }
            var train = new Train(UUID.randomUUID(), player.getUUID(), new TrackGraph(), carriages, new ArrayList<>(List.of(8)), false, 0);
            check(helper, player, entities.getFirst(), trailing, CouplerInteraction.Hint.CAN_DIVIDE);
            check(helper, player, entities.getLast(), leading, CouplerInteraction.Hint.CAN_DIVIDE);
            check(helper, player, entities.getFirst(), leading, CouplerInteraction.Hint.CAN_SELECT);
            check(helper, player, entities.getLast(), trailing, CouplerInteraction.Hint.CAN_SELECT);
            train.speed = 0.1;
            check(helper, player, entities.getFirst(), trailing, CouplerInteraction.Hint.UNSAFE);
            train.speed = 0;
            train.derailed = true;
            check(helper, player, entities.getFirst(), trailing, CouplerInteraction.Hint.UNSAFE);
            train.derailed = false;
            train.owner = UUID.randomUUID();
            check(helper, player, entities.getFirst(), trailing, CouplerInteraction.Hint.UNSAFE);
            train.owner = player.getUUID();
            entities.getLast().getContraption().getBlocks().remove(leading);
            check(helper, player, entities.getFirst(), trailing, CouplerInteraction.Hint.FAILED);
            check(helper, player, entities.getFirst(), BlockPos.ZERO, CouplerInteraction.Hint.CANNOT_DIVIDE);
        }
        helper.succeed();
    }

    private static void check(GameTestHelper helper, net.minecraft.world.entity.player.Player player,
                              CarriageContraptionEntity entity, BlockPos pos, CouplerInteraction.Hint expected) {
        helper.assertTrue(CouplerInteraction.hint(player, entity, pos) == expected, "Incorrect coupler hint: " + expected);
    }
}

package com.misaka10843.kuayue_neo21.data;

import com.misaka10843.kuayue_neo21.content.PortRegistration;
import com.misaka10843.kuayue_neo21.content.VehicleBlockItem;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.CompanyTrainPanel;
import com.misaka10843.kuayue_neo21.legacy.block.panels.door.CustomRenderedDoorBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("kuayue")
@PrefixGameTestTemplate(false)
public final class DoorPlacementWorldTests {
    @GameTest(template = "schematic_room")
    public static void slidingDoorsKeepOriginalPlacementAnchor(GameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.CREATIVE);
        int index = 0;
        for (var entry : PortRegistration.ENTRIES) {
            if (!(entry.get() instanceof CustomRenderedDoorBlock door) || !door.isSlideDoor()) continue;
            for (Direction facing : Direction.Plane.HORIZONTAL) {
                var root = new BlockPos(2 + index % 5 * 6, 2, 2 + index++ / 5 * 6);
                helper.setBlock(root.below(), Blocks.STONE);
                for (Direction side : Direction.Plane.HORIZONTAL) {
                    helper.setBlock(root.relative(side), Blocks.STONE);
                    helper.setBlock(root.relative(side).above(), Blocks.STONE);
                }
                player.setYRot(facing.getOpposite().toYRot());
                var stack = new ItemStack(entry.item());
                player.setItemInHand(InteractionHand.MAIN_HAND, stack);
                var support = helper.absolutePos(root.below());
                var hit = new BlockHitResult(Vec3.atCenterOf(support).add(0, 0.5, 0), Direction.UP, support, false);
                var context = new BlockPlaceContext(helper.getLevel(), player, InteractionHand.MAIN_HAND, stack, hit);
                var item = (VehicleBlockItem) entry.item();
                var preview = item.preview(context);
                helper.assertTrue(preview != null && preview.pos().equals(helper.absolutePos(root)), "Sliding door placement moved off its original anchor: " + entry.id());
                helper.assertTrue(item.place(context).consumesAction(), "Floor prevented sliding-door placement: " + entry.id());
                helper.assertBlockPresent(door, root);
                helper.assertBlockPresent(Blocks.STONE, root.below());
                for (Direction side : Direction.Plane.HORIZONTAL) helper.assertBlockPresent(Blocks.STONE, root.relative(side));
                var state = helper.getBlockState(root);
                var oldPart = door.generateCompanyState(state.getValue(BlockStateProperties.HORIZONTAL_FACING), state.getValue(BlockStateProperties.DOOR_HINGE), false);
                var extras = com.misaka10843.kuayue_neo21.content.VehiclePartGeometry.footprint(root, facing,
                        com.misaka10843.kuayue_neo21.content.SlidingDoorGeometry.closed(door, state)).stream()
                        .filter(part -> !door.getOccupiedPositions(helper.getLevel(), root, state).contains(part)).toList();
                for (var part : extras) {
                    helper.setBlock(part, oldPart);
                    CompanyTrainPanel.setParentBlock(helper.absolutePos(part), helper.getLevel(), oldPart, helper.absolutePos(root));
                }
                helper.destroyBlock(root);
                for (var part : extras) helper.assertBlockPresent(Blocks.AIR, part);
                if (!extras.contains(root.below())) helper.assertBlockPresent(Blocks.STONE, root.below());
            }
        }
        helper.assertTrue(index == 20, "Sliding-door placement coverage changed");
        helper.succeed();
    }
}

package com.misaka10843.kuayue_neo21.data;

import com.misaka10843.kuayue_neo21.content.PortRegistration;
import com.misaka10843.kuayue_neo21.content.SlidingDoorGeometry;
import com.misaka10843.kuayue_neo21.content.VehicleDoorMotion;
import com.misaka10843.kuayue_neo21.legacy.block.panels.door.CustomRenderedDoorBlock;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import org.joml.Matrix4f;
import org.joml.Vector3f;

final class DoorGeometryAudit {
    static Map<String, Integer> run(List<String> failures) {
        int cases = 0, models = 0;
        for (var entry : PortRegistration.ENTRIES) {
            if (!(entry.get() instanceof CustomRenderedDoorBlock block) || !block.isSlideDoor()) continue;
            for (var pair : List.of(block.getLeftDoorModels(), block.getRightDoorModels())) for (boolean lower : new boolean[]{true, false}) {
                models++;
                if (!SlidingDoorGeometry.hasBounds(pair.get(lower).modelLocation())) failures.add("Missing sliding-door model bounds: " + entry.id());
            }
            for (Direction facing : Direction.Plane.HORIZONTAL) for (DoorHingeSide hinge : DoorHingeSide.values()) {
                var closed = block.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, facing)
                        .setValue(BlockStateProperties.DOOR_HINGE, hinge).setValue(BlockStateProperties.OPEN, false);
                var opened = closed.setValue(BlockStateProperties.OPEN, true);
                var selection = opened.getShape(EmptyBlockGetter.INSTANCE, BlockPos.ZERO);
                var leaf = opened.getCollisionShape(EmptyBlockGetter.INSTANCE, BlockPos.ZERO);
                var frame = closed.getShape(EmptyBlockGetter.INSTANCE, BlockPos.ZERO);
                if (Shapes.joinIsNotEmpty(frame, selection, BooleanOp.ONLY_FIRST)) failures.add("Opened sliding door lost frame interaction: " + entry.id());
                if (Shapes.joinIsNotEmpty(leaf, selection, BooleanOp.ONLY_FIRST)) failures.add("Opened sliding leaf is not selectable: " + entry.id());
                var rendererOffset = new Matrix4f().rotateY((float) Math.toRadians(-facing.getOpposite().toYRot()))
                        .transformDirection(new Vector3f(hinge == DoorHingeSide.LEFT ? 0.9f : -0.9f, 0, -0.2f));
                var expected = frame.bounds().move(rendererOffset.x, rendererOffset.y, rendererOffset.z);
                var actual = leaf.bounds();
                if (Math.abs(actual.minX - expected.minX) + Math.abs(actual.minZ - expected.minZ)
                        + Math.abs(actual.maxX - expected.maxX) + Math.abs(actual.maxZ - expected.maxZ) > 1e-5)
                    failures.add("Sliding-door collision is behind rendered leaf: " + entry.id() + "/" + facing);
                for (float phase : new float[]{0, 0.4f, 1}) {
                    var offset = VehicleDoorMotion.slide(phase, hinge == DoorHingeSide.LEFT);
                    if (Math.abs(offset.y) > 1e-8 || Math.abs(offset.x) > 0.901 || offset.z < -0.201) failures.add("Invalid sliding-door animation displacement");
                }
                cases++;
            }
        }
        return Map.of("modelBounds", models, "slidingGeometryStates", cases);
    }
}

package com.misaka10843.kuayue_neo21.data;

import com.misaka10843.kuayue_neo21.content.PortRegistration;
import com.misaka10843.kuayue_neo21.content.VehicleDoorInteraction;
import com.misaka10843.kuayue_neo21.content.VehiclePartGeometry;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.TrainPanelProperties;
import com.misaka10843.kuayue_neo21.legacy.block.panels.end_face.TrainEndfaceBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.end_face.CustomRenderedEndfaceBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.block_entity.CustomRenderedEndfaceEntity;
import com.misaka10843.kuayue_neo21.legacy.block.seat.YZSeatBlock;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.phys.shapes.CollisionContext;

public final class VehicleInteractionAudit {
    public static Map<String, Integer> run(List<String> failures) {
        int checks = 0;
        int seatChecks = 0;
        if (java.util.Arrays.stream(YZSeatBlock.class.getDeclaredMethods()).noneMatch(method -> method.getName().equals("useItemOn"))) {
            failures.add("Vehicle seats inherit the centered Create item interaction");
        }
        for (var entry : PortRegistration.ENTRIES) {
            if (entry.get() instanceof YZSeatBlock seat) {
                for (Direction facing : Direction.Plane.HORIZONTAL) {
                    var state = seat.defaultBlockState().setValue(YZSeatBlock.FACING, facing);
                    for (int slot = 0; slot < seat.getSeatSize(); slot++) {
                        if (seat.getIndex(state, seat.getOffset(state, slot)) != slot) failures.add("Ambiguous seat offset: " + entry.id() + " " + slot);
                        seatChecks++;
                    }
                }
            }
            if (!(entry.get() instanceof TrainEndfaceBlock end)) continue;
            for (Direction facing : Direction.Plane.HORIZONTAL) {
                var state = end.defaultBlockState().setValue(TrainEndfaceBlock.FACING, facing);
                var outline = end.getShape(state, EmptyBlockGetter.INSTANCE, BlockPos.ZERO, CollisionContext.empty());
                var footprint = VehiclePartGeometry.footprint(BlockPos.ZERO, facing, outline);
                if (!footprint.contains(BlockPos.ZERO.above())) failures.add("Missing upper interaction cell: " + entry.id() + " " + facing);
                boolean hasDoor = end.DOOR_TYPE != TrainPanelProperties.DoorType.NO_DOOR;
                if (VehicleDoorInteraction.canInteract(state) != hasDoor) failures.add("Incorrect door interaction: " + entry.id());
                if (hasDoor && VehiclePartGeometry.localShape(outline, BlockPos.ZERO, BlockPos.ZERO.above()).isEmpty()) {
                    failures.add("Empty upper door selection: " + entry.id() + " " + facing);
                }
                if (end instanceof CustomRenderedEndfaceBlock rendered) {
                    var entity = (CustomRenderedEndfaceEntity) rendered.newBlockEntity(BlockPos.ZERO, state);
                    if (entity.type != end.DOOR_TYPE) failures.add("Incorrect first-frame door type: " + entry.id());
                    if (!entity.getRenderBoundingBox().contains(0.5, 3.1, 0.5)) failures.add("Clipped upper end frame: " + entry.id());
                    if (entity.getModels() == null) failures.add("Missing first-frame models: " + entry.id());
                }
                checks++;
            }
        }
        return Map.of("endFaceOrientationChecks", checks, "seatOffsetChecks", seatChecks);
    }
}

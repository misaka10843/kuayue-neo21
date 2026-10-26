package com.misaka10843.kuayue_neo21.content;

import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainPanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.CompanyTrainPanel;
import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.foundation.utility.RaycastHelper;
import java.util.HashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public final class VehicleContraptionRaycast {
    private VehicleContraptionRaycast() {}

    public static boolean containsVehicleParts(Contraption contraption) {
        return contraption.getBlocks().values().stream().anyMatch(info -> info.state().getBlock() instanceof TrainPanelBlock
                || info.state().getBlock() instanceof CompanyTrainPanel);
    }

    public static BlockHitResult trace(Vec3 origin, Vec3 target, Contraption contraption) {
        var seen = new HashSet<BlockPos>();
        var closest = new BlockHitResult[1];
        double[] distance = {Double.POSITIVE_INFINITY};
        var world = contraption.getContraptionWorld();
        java.util.function.Consumer<BlockPos> test = pos -> {
            if (!seen.add(pos) || contraption.isHiddenInPortal(pos)) return;
            var info = contraption.getBlocks().get(pos);
            if (info == null) return;
            if (info.state().getBlock() instanceof CompanyTrainPanel) {
                var parent = CompanyTrainPanel.getParentPos(world, pos);
                var owner = parent == null ? null : contraption.getBlocks().get(parent);
                if (owner == null || owner.state().getBlock() instanceof CompanyTrainPanel
                        || !VehicleDoorPairs.ownsPart(owner.state(), parent, info.state(), pos)) return;
            }
            var shape = info.state().getShape(world, pos);
            if (shape.isEmpty()) return;
            var hit = shape.clip(origin, target, pos);
            if (hit == null) return;
            double current = origin.distanceToSqr(hit.getLocation());
            if (current < distance[0]) {
                distance[0] = current;
                closest[0] = hit;
            }
        };
        RaycastHelper.rayTraceUntil(origin, target, cell -> {
            for (int x = -1; x <= 1; x++) for (int y = -2; y <= 2; y++) for (int z = -1; z <= 1; z++) {
                BlockPos pos = cell.offset(x, y, z).immutable();
                var info = contraption.getBlocks().get(pos);
                if (info == null) continue;
                if (info.state().getBlock() instanceof CompanyTrainPanel && !contraption.isHiddenInPortal(pos)) {
                    var parent = CompanyTrainPanel.getParentPos(world, pos);
                    var rootInfo = parent == null ? null : contraption.getBlocks().get(parent);
                    if (rootInfo != null && VehicleDoorPairs.ownsPart(rootInfo.state(), parent, info.state(), pos)) test.accept(parent);
                }
                test.accept(pos);
            }
            return false;
        });
        return closest[0];
    }
}

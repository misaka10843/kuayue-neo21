package com.misaka10843.kuayue_neo21.content;

import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public final class VehicleDoorMotion {
    private VehicleDoorMotion() {}

    public static Vec3 slide(float progress, boolean left) {
        double amount = Mth.clamp(progress, 0, 1);
        double side = left ? 1 : -1;
        if (amount <= 0.4) return new Vec3(side * 0.2 * amount / 0.4, 0, -0.2 * amount / 0.4);
        return new Vec3(side * (0.2 + 0.7 * (amount - 0.4) / 0.6), 0, -0.2);
    }

    public static Vec3 worldSlide(Direction storedFacing, boolean left) {
        return toWorld(storedFacing, slide(1, left));
    }

    public static Vec3 toWorld(Direction storedFacing, Vec3 local) {
        Direction facing = storedFacing.getOpposite();
        return Vec3.atLowerCornerOf(facing.getCounterClockWise().getNormal()).scale(local.x)
                .add(Vec3.atLowerCornerOf(facing.getNormal()).scale(local.z)).add(0, local.y, 0);
    }
}

package com.misaka10843.kuayue_neo21.content;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.misaka10843.kuayue_neo21.legacy.block.panels.door.CustomRenderedDoorBlock;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.UnaryOperator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public final class SlidingDoorGeometry {
    private static final Map<ResourceLocation, Optional<AABB>> MODELS = new HashMap<>();
    private static final Map<BlockState, VoxelShape> SHAPES = new HashMap<>();

    private SlidingDoorGeometry() {}

    public static synchronized boolean hasBounds(ResourceLocation model) { return bounds(model, new HashSet<>()).isPresent(); }

    public static synchronized VoxelShape closed(CustomRenderedDoorBlock block, BlockState state) {
        state = state.setValue(BlockStateProperties.OPEN, false);
        var cached = SHAPES.get(state);
        if (cached != null) return cached;
        boolean left = state.getValue(BlockStateProperties.DOOR_HINGE) == DoorHingeSide.LEFT;
        var models = left ? block.getLeftDoorModels() : block.getRightDoorModels();
        VoxelShape result = Shapes.empty();
        for (boolean lower : new boolean[]{true, false}) {
            var box = bounds(models.get(lower).modelLocation(), new HashSet<>()).orElseThrow();
            var offset = block.getOffset();
            var placement = new Vec3(left ? 0.5 : -0.5, lower ? 0 : 1, -0.5)
                    .add(left ? offset.x : -offset.x, offset.y, offset.z);
            var facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            var transformed = transform(box, vertex -> VehicleDoorMotion.toWorld(facing, vertex.add(placement)).add(0.5, 0, 0.5));
            result = Shapes.or(result, Shapes.create(transformed));
        }
        SHAPES.put(state, result);
        return result;
    }

    private static Optional<AABB> bounds(ResourceLocation id, Set<ResourceLocation> parents) {
        var cached = MODELS.get(id);
        if (cached != null) return cached;
        if (!parents.add(id)) throw new IllegalStateException("Cyclic door model " + id);
        String resource = "/assets/" + id.getNamespace() + "/models/" + id.getPath() + ".json";
        try (var stream = SlidingDoorGeometry.class.getResourceAsStream(resource)) {
            if (stream == null) throw new IllegalStateException("Missing door geometry " + id);
            var model = JsonParser.parseReader(new InputStreamReader(stream, StandardCharsets.UTF_8)).getAsJsonObject();
            AABB result = null;
            if (model.has("elements")) {
                for (var value : model.getAsJsonArray("elements")) {
                    var element = value.getAsJsonObject();
                    if (!element.has("faces") || element.getAsJsonObject("faces").isEmpty()) continue;
                    var from = vector(element.getAsJsonArray("from"));
                    var to = vector(element.getAsJsonArray("to"));
                    var box = new AABB(from, to);
                    if (element.has("rotation")) box = rotate(box, element.getAsJsonObject("rotation"));
                    result = result == null ? box : result.minmax(box);
                }
            } else if (model.has("parent")) {
                result = bounds(ResourceLocation.parse(model.get("parent").getAsString()), parents).orElse(null);
            }
            var loaded = Optional.ofNullable(result);
            MODELS.put(id, loaded);
            return loaded;
        } catch (java.io.IOException error) { throw new IllegalStateException("Cannot read door geometry " + id, error); }
        finally { parents.remove(id); }
    }

    private static Vec3 vector(JsonArray vector) {
        return new Vec3(vector.get(0).getAsDouble() / 16, vector.get(1).getAsDouble() / 16, vector.get(2).getAsDouble() / 16);
    }

    private static AABB rotate(AABB box, JsonObject rotation) {
        double angle = rotation.get("angle").getAsDouble();
        boolean rescale = rotation.has("rescale") && rotation.get("rescale").getAsBoolean();
        if (angle == 0 && !rescale) return box;
        var origin = vector(rotation.getAsJsonArray("origin"));
        String axis = rotation.get("axis").getAsString();
        float scale = rescale ? (float) (1 / Math.cos(Math.toRadians(Math.abs(angle) == 22.5 ? 22.5 : 45))) : 1;
        var matrix = new Matrix4f().translate((float) origin.x, (float) origin.y, (float) origin.z)
                .rotate((float) Math.toRadians(angle), axis.equals("x") ? 1 : 0, axis.equals("y") ? 1 : 0, axis.equals("z") ? 1 : 0)
                .scale(axis.equals("x") ? 1 : scale, axis.equals("y") ? 1 : scale, axis.equals("z") ? 1 : scale)
                .translate((float) -origin.x, (float) -origin.y, (float) -origin.z);
        return transform(box, vertex -> {
            var transformed = matrix.transformPosition(new Vector3f((float) vertex.x, (float) vertex.y, (float) vertex.z));
            return new Vec3(transformed.x, transformed.y, transformed.z);
        });
    }

    private static AABB transform(AABB box, UnaryOperator<Vec3> transform) {
        AABB result = null;
        for (double x : new double[]{box.minX, box.maxX}) for (double y : new double[]{box.minY, box.maxY}) for (double z : new double[]{box.minZ, box.maxZ}) {
            Vec3 vertex = transform.apply(new Vec3(x, y, z));
            var point = new AABB(vertex, vertex);
            result = result == null ? point : result.minmax(point);
        }
        return result;
    }
}

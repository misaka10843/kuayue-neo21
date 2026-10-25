package com.misaka10843.kuayue_neo21.data;

import com.misaka10843.kuayue_neo21.content.PortRegistration;
import com.misaka10843.kuayue_neo21.client.lighting.LightingVertexCollector;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceMetadata;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.client.model.data.ModelData;

final class SlabSurfaceLightingAudit {
    static Map<String, Object> run(List<String> failures) throws Exception {
        Path project = Path.of(System.getProperty("kuayue.projectDir"));
        var parsed = new HashMap<ResourceLocation, BlockModel>();
        var source = new java.util.function.Function<ResourceLocation, net.minecraft.client.resources.model.UnbakedModel>() {
            @Override public BlockModel apply(ResourceLocation id) {
                return parsed.computeIfAbsent(id, key -> {
                    String relative = "assets/" + key.getNamespace() + "/models/" + key.getPath() + ".json";
                    try {
                        Path path = project.resolve("src/main/resources").resolve(relative);
                        String json;
                        if (Files.exists(path)) json = Files.readString(path);
                        else try (var input = SlabSurfaceLightingAudit.class.getClassLoader().getResourceAsStream(relative)) {
                            if (input == null) throw new IllegalStateException("Missing lighting model " + key);
                            json = new String(input.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
                        }
                        return BlockModel.fromString(json);
                    } catch (java.io.IOException error) { throw new IllegalStateException(error); }
                });
            }
        };
        var observations = new java.util.LinkedHashMap<String, Object>();
        try (var contents = new SpriteContents(PortRegistration.resource("lighting_audit"), new FrameSize(16, 16), new NativeImage(16, 16, false), ResourceMetadata.EMPTY)) {
            class AuditSprite extends TextureAtlasSprite {
                AuditSprite() { super(PortRegistration.resource("lighting_audit"), contents, 16, 16, 0, 0); }
            }
            var sprite = new AuditSprite();
            var renderer = new ModelBlockRenderer(new BlockColors());
            for (String id : List.of("floor_25b", "floor_25g", "22_floor", "22_floor_center")) {
                var state = net.minecraft.core.registries.BuiltInRegistries.BLOCK.get(PortRegistration.resource(id)).defaultBlockState();
                var definition = com.google.gson.JsonParser.parseString(Files.readString(project.resolve("src/main/resources/assets/kuayue/blockstates/" + id + ".json"))).getAsJsonObject();
                var variant = definition.getAsJsonObject("variants").entrySet().iterator().next().getValue().getAsJsonObject();
                var model = (BlockModel) source.apply(ResourceLocation.parse(variant.get("model").getAsString()));
                model.resolveParents(source);
                var baked = model.bake(null, model, material -> sprite, BlockModelRotation.X0_Y0, true);
                if (!baked.useAmbientOcclusion()) failures.add("Floor disables ambient occlusion: " + id);
                var lights = new ArrayList<List<Integer>>();
                var level = (BlockAndTintGetter) Proxy.newProxyInstance(BlockAndTintGetter.class.getClassLoader(), new Class<?>[]{BlockAndTintGetter.class}, (proxy, method, args) -> switch (method.getName()) {
                    case "getBlockState" -> ((BlockPos) args[0]).getY() == 0 ? state : Blocks.AIR.defaultBlockState();
                    case "getBrightness" -> args[0] == LightLayer.SKY ? 0 : Math.max(0, 12 - Math.abs(((BlockPos) args[1]).getX() + 2) - Math.abs(((BlockPos) args[1]).getZ() + 2));
                    case "getShade" -> 1f;
                    case "getBlockTint" -> -1;
                    default -> throw new UnsupportedOperationException(method.toString());
                });
                var consumer = (VertexConsumer) Proxy.newProxyInstance(VertexConsumer.class.getClassLoader(), new Class<?>[]{VertexConsumer.class}, (proxy, method, args) -> {
                    if (method.getName().equals("putBulkData")) {
                        for (Object argument : args) if (argument instanceof int[] values) lights.add(java.util.Arrays.stream(values).boxed().toList());
                    }
                    return method.getReturnType() == VertexConsumer.class ? proxy : null;
                });
                renderer.tesselateWithAO(level, baked, state, BlockPos.ZERO, new PoseStack(), consumer, false, RandomSource.create(), 0, 0, ModelData.EMPTY, null);
                var details = new java.util.LinkedHashMap<String, Object>();
                details.put("ambientOcclusion", baked.useAmbientOcclusion());
                details.put("vertexLights", lights);
                var first = sample(renderer, level, baked, state, BlockPos.ZERO);
                var next = sample(renderer, level, baked, state, new BlockPos(1, 0, 0));
                int shared = 0;
                for (var vertex : first) {
                    if (Math.abs(vertex.x - 1) > 1e-5) continue;
                    var other = next.stream().filter(value -> Math.abs(value.x) < 1e-5 && Math.abs(value.z - vertex.z) < 1e-5).findFirst();
                    if (other.isEmpty()) continue;
                    shared++;
                    if (vertex.blockLight != other.get().blockLight || vertex.skyLight != other.get().skyLight || vertex.red != other.get().red)
                        failures.add("Floor light seam at " + id + ": " + vertex.z);
                }
                if (shared < 2) failures.add("Missing adjacent floor vertices: " + id);
                if (first.stream().map(vertex -> vertex.blockLight).distinct().count() < 2) failures.add("Floor light was flattened: " + id);
                details.put("sharedEdgeVertices", shared);
                details.put("renderedTopVertices", first);
                details.put("emission", state.getLightEmission(level, BlockPos.ZERO));
                var top = baked.getQuads(state, net.minecraft.core.Direction.UP, RandomSource.create());
                details.put("quadAO", top.stream().map(net.minecraft.client.renderer.block.model.BakedQuad::hasAmbientOcclusion).toList());
                boolean sodiumClasses;
                try { Class.forName("net.caffeinemc.mods.sodium.client.model.light.smooth.SmoothLightPipeline"); sodiumClasses = true; }
                catch (ClassNotFoundException ignored) { sodiumClasses = false; }
                details.put("sodiumClasses", sodiumClasses);
                if (sodiumClasses && !top.isEmpty()) {
                    String base = "net.caffeinemc.mods.sodium.client.model.";
                    var accessType = Class.forName(base + "light.data.LightDataAccess");
                    var cacheType = Class.forName(base + "light.data.SingleBlockLightDataCache");
                    var cache = cacheType.getConstructor().newInstance();
                    cacheType.getMethod("reset", BlockPos.class, BlockAndTintGetter.class).invoke(cache, BlockPos.ZERO, level);
                    var pipelineType = Class.forName(base + "light.smooth.SmoothLightPipeline");
                    var pipeline = pipelineType.getConstructor(accessType).newInstance(cache);
                    var outputType = Class.forName(base + "light.data.QuadLightData");
                    var output = outputType.getConstructor().newInstance();
                    var quadType = Class.forName(base + "quad.ModelQuadView");
                    details.put("sodiumQuadInterface", quadType.isInstance(top.getFirst()));
                    details.put("sodiumQuadFlags", quadType.getMethod("getFlags").invoke(top.getFirst()));
                    pipelineType.getMethod("calculate", quadType, BlockPos.class, outputType, net.minecraft.core.Direction.class, net.minecraft.core.Direction.class, boolean.class, boolean.class)
                            .invoke(pipeline, top.getFirst(), BlockPos.ZERO, output, net.minecraft.core.Direction.UP, net.minecraft.core.Direction.UP, true, false);
                    details.put("sodiumVertexLights", java.util.Arrays.stream((int[]) outputType.getField("lm").get(output)).boxed().toList());
                }
                observations.put(id, details);
            }
        }
        return observations;
    }

    private static List<LightingVertexCollector.Vertex> sample(ModelBlockRenderer renderer, BlockAndTintGetter level,
            net.minecraft.client.resources.model.BakedModel model, net.minecraft.world.level.block.state.BlockState state, BlockPos pos) {
        var vertices = new LightingVertexCollector();
        renderer.tesselateWithAO(level, model, state, pos, new PoseStack(), vertices, false, RandomSource.create(42), 0, 0, ModelData.EMPTY, null);
        return vertices.vertices().stream().filter(vertex -> vertex.normalY > 0.9f && Math.abs(vertex.y - 1) < 1e-5).toList();
    }
}

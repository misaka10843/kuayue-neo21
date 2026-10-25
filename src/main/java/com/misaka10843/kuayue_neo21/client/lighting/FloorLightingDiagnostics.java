package com.misaka10843.kuayue_neo21.client.lighting;

import com.google.gson.GsonBuilder;
import com.misaka10843.kuayue_neo21.KuaYueneo21;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.model.data.ModelData;

@EventBusSubscriber(modid = KuaYueneo21.MODID, value = Dist.CLIENT)
public final class FloorLightingDiagnostics {
    @SubscribeEvent
    public static void register(RegisterClientCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("kuayue_lighting").executes(context -> {
            var minecraft = Minecraft.getInstance();
            if (minecraft.level == null || !(minecraft.hitResult instanceof BlockHitResult hit)) {
                context.getSource().sendFailure(Component.literal("Look at a stationary floor block first."));
                return 0;
            }
            try {
                var report = new LinkedHashMap<String, Object>();
                report.put("smoothLightingOption", minecraft.options.ambientOcclusion().get());
                report.put("smoothLightingActive", Minecraft.useAmbientOcclusion());
                report.put("mods", ModList.get().getMods().stream().map(mod -> mod.getModId() + "@" + mod.getVersion()).toList());
                var surfaces = new ArrayList<Map<String, Object>>();
                for (int x = -1; x <= 1; x++) for (int z = -1; z <= 1; z++) {
                    var pos = hit.getBlockPos().offset(x, 0, z);
                    surfaces.add(surface(minecraft, pos, minecraft.level.getBlockState(pos)));
                }
                report.put("surfaces", surfaces);
                var reference = Blocks.SMOOTH_STONE_SLAB.defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, SlabType.TOP);
                // Sample the reference model in place without changing the world or its light engine.
                report.put("vanillaUpperSlabAtTarget", surface(minecraft, hit.getBlockPos(), reference));
                var directory = minecraft.gameDirectory.toPath().resolve("logs");
                Files.createDirectories(directory);
                var file = directory.resolve("kuayue-lighting-" + DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss-SSS").format(LocalDateTime.now()) + ".json");
                Files.writeString(file, new GsonBuilder().setPrettyPrinting().create().toJson(report));
                context.getSource().sendSuccess(() -> Component.literal("Lighting report saved: " + file.toAbsolutePath()), false);
                return 1;
            } catch (Exception error) {
                LogUtils.getLogger().error("Could not capture floor lighting", error);
                context.getSource().sendFailure(Component.literal("Lighting capture failed; see latest.log."));
                return 0;
            }
        }));
    }

    private static Map<String, Object> surface(Minecraft minecraft, BlockPos pos, BlockState state) {
        var level = minecraft.level;
        var renderer = minecraft.getBlockRenderer();
        var model = renderer.getBlockModel(state);
        var entity = level.getBlockEntity(pos);
        var data = entity == null ? ModelData.EMPTY : entity.getModelData();
        var result = new LinkedHashMap<String, Object>();
        result.put("position", List.of(pos.getX(), pos.getY(), pos.getZ()));
        result.put("state", state.toString());
        result.put("modelClass", model.getClass().getName());
        result.put("ambientOcclusion", model.useAmbientOcclusion());
        result.put("usesBlockLight", model.usesBlockLight());
        result.put("emission", state.getLightEmission(level, pos));
        result.put("blockLight", level.getBrightness(LightLayer.BLOCK, pos));
        result.put("blockLightAbove", level.getBrightness(LightLayer.BLOCK, pos.above()));
        result.put("shadeBrightness", state.getShadeBrightness(level, pos));
        result.put("occludes", state.canOcclude());
        var layers = new ArrayList<Map<String, Object>>();
        for (var layer : model.getRenderTypes(state, RandomSource.create(42), data)) {
            var entry = new LinkedHashMap<String, Object>();
            entry.put("renderType", layer.toString());
            entry.put("ambientOcclusionPolicy", model.useAmbientOcclusion(state, data, layer).toString());
            entry.put("topQuads", topQuads(model, state, data, layer));
            var vertices = new LightingVertexCollector();
            renderer.getModelRenderer().tesselateBlock(level, model, state, pos, new PoseStack(), vertices, false,
                    RandomSource.create(42), state.getSeed(pos), 0, data, layer);
            entry.put("renderedTopVertices", vertices.vertices().stream().filter(vertex -> vertex.normalY > 0.9f).toList());
            layers.add(entry);
        }
        result.put("layers", layers);
        return result;
    }

    private static List<Map<String, Object>> topQuads(BakedModel model, BlockState state, ModelData data, RenderType layer) {
        var result = new ArrayList<Map<String, Object>>();
        for (Direction side : new Direction[]{null, Direction.UP}) {
            for (var quad : model.getQuads(state, side, RandomSource.create(42), data, layer)) {
                if (quad.getDirection() != Direction.UP) continue;
                var entry = new LinkedHashMap<String, Object>();
                entry.put("ambientOcclusion", quad.hasAmbientOcclusion());
                entry.put("shade", quad.isShade());
                entry.put("texture", quad.getSprite().contents().name().toString());
                entry.put("vertices", java.util.Arrays.stream(quad.getVertices()).boxed().toList());
                result.add(entry);
            }
        }
        return result;
    }
}

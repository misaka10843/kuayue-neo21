package com.misaka10843.kuayue_neo21.data;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.misaka10843.kuayue_neo21.content.PortRegistration;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.CompanyTrainPanel;
import com.mojang.serialization.JsonOps;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.core.BlockPos;

public final class PortAuditProvider implements DataProvider {
    private final CompletableFuture<HolderLookup.Provider> registries;
    private final Path project = Path.of(System.getProperty("kuayue.projectDir"));

    public PortAuditProvider(CompletableFuture<HolderLookup.Provider> registries) { this.registries = registries; }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return registries.thenAccept(provider -> {
            List<String> failures = new ArrayList<>();
            int recipes = 0;
            try {
                Class<?>[] mixinTargets = {com.simibubi.create.content.contraptions.Contraption.class,
                        com.simibubi.create.content.contraptions.AbstractContraptionEntity.class};
                for (Class<?> target : mixinTargets) target.getDeclaredMethods();
                RegistryOps<com.google.gson.JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, provider);
                try (var paths = Files.walk(project.resolve("src/generated/resources/data/kuayueneo21/recipe"))) {
                    for (Path file : paths.filter(path -> path.toString().endsWith(".json")).sorted().toList()) {
                        recipes++;
                        var parsed = Recipe.CODEC.parse(ops, JsonParser.parseString(Files.readString(file)));
                        parsed.error().ifPresent(error -> failures.add(project.relativize(file) + ": " + error.message()));
                    }
                }
                for (var entry : PortRegistration.ENTRIES) {
                    var block = entry.get();
                    Path stateFile = project.resolve("src/main/resources/assets/kuayueneo21/blockstates/" + entry.id() + ".json");
                    if (!Files.exists(stateFile)) stateFile = project.resolve("src/generated/resources/assets/kuayueneo21/blockstates/" + entry.id() + ".json");
                    if (!Files.exists(stateFile)) {
                        failures.add("Missing blockstate: " + entry.id());
                    } else {
                        var definition = JsonParser.parseString(Files.readString(stateFile)).getAsJsonObject();
                        if (definition.has("variants")) {
                            var variants = definition.getAsJsonObject("variants").keySet();
                            for (String variant : variants) {
                                for (String pair : variant.split(",")) {
                                    if (pair.isBlank()) continue;
                                    String[] parts = pair.split("=", 2);
                                    var property = block.getStateDefinition().getProperty(parts[0]);
                                    if (property == null || parts.length != 2 || property.getValue(parts[1]).isEmpty()) failures.add("Unknown state variant " + entry.id() + ": " + pair);
                                }
                            }
                            for (var state : block.getStateDefinition().getPossibleStates()) {
                                long matches = variants.stream().filter(variant -> matchesVariant(state, variant)).count();
                                if (matches != 1) failures.add("Blockstate coverage " + matches + ": " + state);
                            }
                        }
                    }
                    if (block instanceof EntityBlock entityBlock) {
                        var entity = entityBlock.newBlockEntity(BlockPos.ZERO, block.defaultBlockState());
                        if (entity != null && !entity.getType().isValid(block.defaultBlockState())) failures.add("Invalid entity type for " + entry.id());
                    }
                    if (entry.isHidden() && !(block instanceof CompanyTrainPanel)) failures.add("Unexpected hidden block " + entry.id());
                }
                Map<String, Object> report = new LinkedHashMap<>();
                report.put("blocks", PortRegistration.ENTRIES.size());
                report.put("obtainableBlocks", PortRegistration.ENTRIES.stream().filter(entry -> !entry.isHidden()).count());
                report.put("decodedRecipes", recipes);
                report.put("loadedMixinTargets", mixinTargets.length);
                report.put("createIntegration", BogeyIntegrationAudit.run(provider, failures));
                report.put("failures", failures);
                Path destination = project.resolve("build/reports/port-audit.json");
                Files.createDirectories(destination.getParent());
                Files.writeString(destination, new GsonBuilder().setPrettyPrinting().create().toJson(report));
            } catch (IOException error) {
                throw new IllegalStateException("Cannot audit port data", error);
            }
            if (!failures.isEmpty()) throw new IllegalStateException(String.join("\n", failures));
        });
    }

    @Override public String getName() { return "Vehicle port registry and recipe audit"; }

    private static boolean matchesVariant(net.minecraft.world.level.block.state.BlockState state, String variant) {
        for (String pair : variant.split(",")) {
            if (pair.isBlank()) continue;
            String[] parts = pair.split("=", 2);
            var property = state.getBlock().getStateDefinition().getProperty(parts[0]);
            if (property == null || parts.length != 2 || !serializedValue(state, property).equals(parts[1])) return false;
        }
        return true;
    }

    private static <T extends Comparable<T>> String serializedValue(net.minecraft.world.level.block.state.BlockState state,
                                                                    net.minecraft.world.level.block.state.properties.Property<T> property) {
        return property.getName(state.getValue(property));
    }
}

package com.misaka10843.kuayue_neo21.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("kuayue")
@PrefixGameTestTemplate(false)
public final class RecipeTagWorldTests {
    @GameTest(template = "schematic_room")
    public static void recipeIngredientTags(GameTestHelper helper) throws Exception {
        Set<String> tags = new HashSet<>();
        Set<String> fluids = new HashSet<>();
        Path project = Path.of(System.getProperty("kuayue.projectDir"));
        try (var paths = Files.walk(project.resolve("src/generated/resources/data/kuayue/recipe"))) {
            for (Path path : paths.filter(file -> file.toString().endsWith(".json")).toList())
                collect(JsonParser.parseString(Files.readString(path)), tags, fluids);
        }
        Set<String> empty = new java.util.TreeSet<>();
        for (String id : fluids) {
            var key = TagKey.create(Registries.FLUID, ResourceLocation.parse(id));
            if (!BuiltInRegistries.FLUID.getTag(key).map(set -> set.size() > 0).orElse(false)) empty.add("fluid " + id);
        }
        for (String id : tags) {
            var key = TagKey.create(Registries.ITEM, ResourceLocation.parse(id));
            if (!BuiltInRegistries.ITEM.getTag(key).map(set -> set.size() > 0).orElse(false)) empty.add(id);
        }
        helper.assertTrue(empty.isEmpty(), "Empty recipe ingredient tags: " + empty);
        helper.succeed();
    }

    private static void collect(JsonElement element, Set<String> tags, Set<String> fluids) {
        if (element.isJsonObject()) {
            var object = element.getAsJsonObject();
            if (object.has("tag") && object.get("tag").isJsonPrimitive()) {
                var destination = object.has("type") && object.get("type").getAsString().equals("neoforge:tag") ? fluids : tags;
                destination.add(object.get("tag").getAsString());
            }
            object.entrySet().forEach(entry -> collect(entry.getValue(), tags, fluids));
        } else if (element.isJsonArray()) element.getAsJsonArray().forEach(value -> collect(value, tags, fluids));
    }
}

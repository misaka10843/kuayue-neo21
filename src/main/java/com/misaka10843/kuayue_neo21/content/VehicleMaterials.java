package com.misaka10843.kuayue_neo21.content;

import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

public final class VehicleMaterials {
    public static final Map<String, DeferredItem<Item>> ITEMS = new LinkedHashMap<>();

    public static void initialize() {
        try (var stream = VehicleMaterials.class.getResourceAsStream("/porting/materials.json")) {
            if (stream == null) throw new IllegalStateException("Missing generated production material catalog");
            String[] materials = new Gson().fromJson(new InputStreamReader(stream, StandardCharsets.UTF_8), String[].class);
            for (String material : materials) ITEMS.put(material, PortRegistration.ITEMS.registerSimpleItem(material));
        } catch (IOException error) {
            throw new IllegalStateException("Cannot read production material catalog", error);
        }
    }
}

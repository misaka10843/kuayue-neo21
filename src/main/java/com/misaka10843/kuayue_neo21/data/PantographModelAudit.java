package com.misaka10843.kuayue_neo21.data;

import com.misaka10843.kuayue_neo21.client.PantographBaseModels;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.neoforged.neoforge.client.model.data.ModelData;

final class PantographModelAudit {
    static Map<String, Integer> run(List<String> failures) {
        var block = BuiltInRegistries.BLOCK.get(ResourceLocation.parse("electroenergetics:pantograph"));
        BakedModel original = (BakedModel) Proxy.newProxyInstance(BakedModel.class.getClassLoader(), new Class<?>[]{BakedModel.class},
                (proxy, method, arguments) -> method.getName().equals("getQuads") ? java.util.Collections.singletonList(null) : null);
        var models = new HashMap<ModelResourceLocation, BakedModel>();
        var inventory = new ModelResourceLocation(ResourceLocation.parse("electroenergetics:pantograph"), "inventory");
        models.put(inventory, original);
        for (var state : block.getStateDefinition().getPossibleStates()) models.put(BlockModelShaper.stateToModelLocation(state), original);
        PantographBaseModels.redirectBakedModels(models);
        for (var state : block.getStateDefinition().getPossibleStates()) {
            var model = models.get(BlockModelShaper.stateToModelLocation(state));
            if (PantographBaseModels.original(state) != original) failures.add("Pantograph fixed base model was lost: " + state);
            if (!model.getQuads(state, null, RandomSource.create()).isEmpty()
                    || !model.getQuads(state, null, RandomSource.create(), ModelData.EMPTY, null).isEmpty())
                failures.add("Pantograph fixed base would still render at original height: " + state);
        }
        if (models.get(inventory) != original) failures.add("Pantograph inventory model changed");
        PantographBaseModels.clear();
        return Map.of("singleAndDoubleBaseStates", block.getStateDefinition().getPossibleStates().size());
    }
}

package com.misaka10843.kuayue_neo21.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.trains.bogey.BogeyVisual;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.nbt.CompoundTag;

final class LegacyBogeyVisual implements BogeyVisual {
    private final VisualizationContext context;
    private final LegacyBogeyRenderer renderer;
    private final boolean inContraption;
    private final Map<PartialModel, List<TransformedInstance>> instances = new HashMap<>();
    private final Map<PartialModel, Integer> used = new HashMap<>();
    private int light;

    LegacyBogeyVisual(VisualizationContext context, LegacyBogeyRenderer renderer, boolean inContraption) {
        this.context = context;
        this.renderer = renderer;
        this.inContraption = inContraption;
    }

    @Override
    public void update(CompoundTag data, float wheelAngle, PoseStack pose) {
        used.clear();
        renderer.renderVisual(data, wheelAngle, pose, inContraption, (model, transform) -> {
            List<TransformedInstance> copies = instances.computeIfAbsent(model, ignored -> new ArrayList<>());
            int index = used.getOrDefault(model, 0);
            if (index == copies.size()) copies.add(context.instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(model)).createInstance());
            copies.get(index).setTransform(transform).light(light).setChanged();
            used.put(model, index + 1);
        });
        instances.forEach((model, copies) -> {
            for (int i = used.getOrDefault(model, 0); i < copies.size(); i++) copies.get(i).setZeroTransform().setChanged();
        });
    }

    @Override public void hide() { instances.values().forEach(copies -> copies.forEach(instance -> instance.setZeroTransform().setChanged())); }
    @Override public void updateLight(int light) { this.light = light; instances.values().forEach(copies -> copies.forEach(instance -> instance.light(light).setChanged())); }
    @Override public void collectCrumblingInstances(Consumer<Instance> consumer) { instances.values().forEach(copies -> copies.forEach(consumer)); }
    @Override public void delete() { instances.values().forEach(copies -> copies.forEach(Instance::delete)); instances.clear(); }
}

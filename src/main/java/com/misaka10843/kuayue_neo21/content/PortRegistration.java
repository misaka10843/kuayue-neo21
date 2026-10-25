package com.misaka10843.kuayue_neo21.content;

import com.misaka10843.kuayue_neo21.KuaYueneo21;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class PortRegistration<T extends Block> implements Supplier<T> {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(KuaYueneo21.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(KuaYueneo21.MODID);
    public static final List<PortRegistration<?>> ENTRIES = new ArrayList<>();

    public final PortRegistration<T> block = this;
    private final String id;
    private final List<Consumer<BlockBehaviour.Properties>> properties = new ArrayList<>();
    private Function<BlockBehaviour.Properties, T> factory;
    private DeferredBlock<T> registered;
    private DeferredItem<BlockItem> item;
    private boolean hidden;

    public PortRegistration(String id) {
        this.id = id;
    }

    public PortRegistration<T> factory(Function<BlockBehaviour.Properties, T> factory) {
        this.factory = factory;
        return this;
    }

    public PortRegistration<T> hidden() { hidden = true; return this; }
    public boolean isHidden() { return hidden; }

    public PortRegistration<T> noOcclusion() {
        return addProperty(BlockBehaviour.Properties::noOcclusion);
    }

    public PortRegistration<T> strengthAndTool(float strength, float resistance) {
        return strength(strength, resistance).requireCorrectTools();
    }

    public PortRegistration<T> strength(float strength, float resistance) {
        return addProperty(p -> p.strength(strength, resistance));
    }

    public PortRegistration<T> requireCorrectTools() {
        return addProperty(BlockBehaviour.Properties::requiresCorrectToolForDrops);
    }

    public PortRegistration<T> addProperty(Consumer<BlockBehaviour.Properties> property) {
        properties.add(property);
        return this;
    }

    public PortRegistration<T> submit() {
        if (factory == null) throw new IllegalStateException("Missing block factory: " + id);
        registered = BLOCKS.register(id, () -> {
            BlockBehaviour.Properties settings = BlockBehaviour.Properties.of().strength(1.5f, 3f).sound(SoundType.METAL).noOcclusion();
            properties.forEach(property -> property.accept(settings));
            return factory.apply(settings);
        });
        if (!hidden) item = ITEMS.register(id, () -> {
            var block = registered.get();
            if (block instanceof com.simibubi.create.content.trains.track.TrackBlock) return new com.simibubi.create.content.trains.track.TrackBlockItem(block, new Item.Properties());
            return block instanceof com.misaka10843.kuayue_neo21.legacy.block.panels.TrainPanelBlock panel
                    ? new VehicleBlockItem(panel, new Item.Properties()) : new BlockItem(block, new Item.Properties());
        });
        ENTRIES.add(this);
        return this;
    }

    public String id() { return id; }
    public BlockItem item() { return item.get(); }
    @Override public T get() { return registered.get(); }
    public T getBlock() { return get(); }
    public T instance() { return get(); }
    public static ResourceLocation resource(String path) { return ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, path); }
}

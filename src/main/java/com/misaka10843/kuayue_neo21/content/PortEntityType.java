package com.misaka10843.kuayue_neo21.content;

import com.misaka10843.kuayue_neo21.KuaYueneo21;
import java.util.function.Predicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class PortEntityType<T extends BlockEntity> {
    public static final DeferredRegister<BlockEntityType<?>> TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, KuaYueneo21.MODID);
    private final DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> type;

    public PortEntityType(String id, BlockEntityType.BlockEntitySupplier<T> factory, Predicate<Block> supports) {
        type = TYPES.register(id, () -> BlockEntityType.Builder.of(factory,
                PortRegistration.ENTRIES.stream().map(PortRegistration::get).filter(supports).toArray(Block[]::new)).build(null));
    }

    public BlockEntityType<T> getType() { return type.get(); }
}

package com.misaka10843.kuayue_neo21.legacy.initial;

import com.misaka10843.kuayue_neo21.KuaYueneo21;
import com.misaka10843.kuayue_neo21.legacy.block.seat.M1SeatEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public final class AllEntities {
    public static final DeferredRegister<EntityType<?>> TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, KuaYueneo21.MODID);
    public static final DeferredHolder<EntityType<?>, EntityType<M1SeatEntity>> M1_SEAT = TYPES.register("meter_seat", () -> EntityType.Builder.<M1SeatEntity>of(M1SeatEntity::new, MobCategory.MISC).sized(0.25f, 0.25f).clientTrackingRange(5).build("meter_seat"));
}

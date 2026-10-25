package com.misaka10843.kuayue_neo21.content.coupling;

import com.misaka10843.kuayue_neo21.KuaYueneo21;
import com.misaka10843.kuayue_neo21.content.PortRegistration;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class CouplingEffects {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, KuaYueneo21.MODID);
    public static final DeferredHolder<SoundEvent, SoundEvent> COUPLER = SOUNDS.register("coupler",
            () -> SoundEvent.createVariableRangeEvent(PortRegistration.resource("coupler")));

    private CouplingEffects() {}

    public static void play(ServerLevel level, Vec3 position) {
        level.playSound(null, position.x, position.y, position.z, COUPLER.get(), SoundSource.NEUTRAL, 0.2f, 1);
        level.sendParticles(ParticleTypes.CRIT, position.x, position.y, position.z, 20, 0.2, 0.2, 0.2, 0.8);
    }
}

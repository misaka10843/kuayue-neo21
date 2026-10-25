package com.misaka10843.kuayue_neo21;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(KuaYueneo21.MODID)
public class KuaYueneo21 {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "kuayue";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public KuaYueneo21(IEventBus modEventBus, ModContainer modContainer) {
        com.misaka10843.kuayue_neo21.legacy.initial.AllBlocks.initialize();
        com.misaka10843.kuayue_neo21.content.bogey.VehicleBogeys.initialize();
        com.misaka10843.kuayue_neo21.content.track.RailwayTracks.initialize();
        com.misaka10843.kuayue_neo21.content.infrastructure.InfrastructureBlocks.initialize();
        com.misaka10843.kuayue_neo21.content.VehicleMaterials.initialize();
        com.misaka10843.kuayue_neo21.content.VehicleTabs.initialize();
        com.misaka10843.kuayue_neo21.content.PortRegistration.BLOCKS.register(modEventBus);
        com.misaka10843.kuayue_neo21.content.PortRegistration.ITEMS.register(modEventBus);
        com.misaka10843.kuayue_neo21.content.PortEntityType.TYPES.register(modEventBus);
        com.misaka10843.kuayue_neo21.legacy.initial.AllEntities.TYPES.register(modEventBus);
        com.misaka10843.kuayue_neo21.content.VehicleTabs.TABS.register(modEventBus);
        com.misaka10843.kuayue_neo21.content.coupling.CouplingEffects.SOUNDS.register(modEventBus);
        modEventBus.addListener((net.neoforged.neoforge.data.event.GatherDataEvent event) ->
                event.addProvider(new com.misaka10843.kuayue_neo21.data.PortAuditProvider(event.getLookupProvider())));
        modEventBus.addListener((net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent event) ->
                event.enqueueWork(com.misaka10843.kuayue_neo21.content.VehicleBehaviours::register));
    }
}

package com.misaka10843.kuayue_neo21.client;

import com.misaka10843.kuayue_neo21.KuaYueneo21;
import com.misaka10843.kuayue_neo21.content.bogey.VehicleBogeys;
import com.misaka10843.kuayue_neo21.legacy.initial.AllBlocks;
import com.misaka10843.kuayue_neo21.legacy.initial.AllEntities;
import com.misaka10843.kuayue_neo21.legacy.block.panels.block_entity.renderer.*;
import com.simibubi.create.content.trains.bogey.BogeyBlockEntityRenderer;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = KuaYueneo21.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class VehicleClient {
    @SubscribeEvent
    public static void setupTracks(net.neoforged.fml.event.lifecycle.FMLClientSetupEvent event) {
        event.enqueueWork(() -> com.misaka10843.kuayue_neo21.content.track.RailwayTracks.BLOCKS.values().forEach(entry ->
                net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(entry.get(), net.minecraft.client.renderer.RenderType.cutoutMipped())));
    }

    @SubscribeEvent
    public static void bakeTracks(net.neoforged.neoforge.client.event.ModelEvent.ModifyBakingResult event) {
        var ids = com.misaka10843.kuayue_neo21.content.track.RailwayTracks.BLOCKS.values().stream()
                .map(entry -> com.misaka10843.kuayue_neo21.content.PortRegistration.resource(entry.id())).collect(java.util.stream.Collectors.toSet());
        event.getModels().replaceAll((id, model) -> ids.contains(id.id()) && !id.variant().equals("inventory")
                ? new com.simibubi.create.content.trains.track.TrackModel(model) : model);
    }

    @SubscribeEvent
    public static void registerItemExtensions(net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent event) {
        com.misaka10843.kuayue_neo21.content.track.RailwayTracks.BLOCKS.values().forEach(entry ->
                event.registerBlock(new com.simibubi.create.content.trains.track.TrackBlock.RenderProperties(), entry.get()));
        var extension = new net.neoforged.neoforge.client.extensions.common.IClientItemExtensions() {
            private BogeyItemRenderer renderer;
            @Override public net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) renderer = new BogeyItemRenderer();
                return renderer;
            }
        };
        for (var entry : com.misaka10843.kuayue_neo21.content.PortRegistration.ENTRIES)
            if (entry.get() instanceof com.misaka10843.kuayue_neo21.content.bogey.VehicleBogeyBlock) event.registerItem(extension, entry.item());
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(AllBlocks.CUSTOM_RENDERED_DOOR_ENTITY.getType(), CustomRenderedDoorRenderer::new);
        event.registerBlockEntityRenderer(AllBlocks.PLATFORM_DOOR_ENTITY.getType(), PlatformDoorRenderer::new);
        event.registerBlockEntityRenderer(AllBlocks.CUSTOM_RENDERED_ENDFACE_ENTITY.getType(), CustomRenderedEndFaceRenderer::new);
        event.registerBlockEntityRenderer(AllBlocks.SINGLE_SLIDING_DOOR_ENTITY.getType(), SingleSlidingDoorRenderer::new);
        event.registerBlockEntityRenderer(AllBlocks.DOUBLE_ROTATE_DOOR_ENTITY.getType(), DoubleRotateDoorRenderer::new);
        event.registerBlockEntityRenderer(AllBlocks.CR200J_FRONT_ENTITY.getType(), CR200jFrontRenderer::new);
        event.registerBlockEntityRenderer(VehicleBogeys.ENTITY.getType(), BogeyBlockEntityRenderer::new);
        event.registerEntityRenderer(AllEntities.M1_SEAT.get(), NoopRenderer::new);
    }
}

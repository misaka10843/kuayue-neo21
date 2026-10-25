package com.misaka10843.kuayue_neo21.content.bogey;

import com.misaka10843.kuayue_neo21.content.PortRegistration;
import com.simibubi.create.AllBogeyStyles;
import com.simibubi.create.content.trains.bogey.AbstractBogeyBlock;
import com.simibubi.create.content.trains.bogey.BogeySizes;
import com.simibubi.create.content.trains.bogey.BogeySizes.BogeySize;
import com.simibubi.create.content.trains.bogey.BogeyStyle;
import com.simibubi.create.content.trains.track.TrackBlock;
import com.simibubi.create.content.trains.track.TrackMaterial.TrackType;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;

final class BogeyGroup {
    public static final TrackType METER = com.misaka10843.kuayue_neo21.content.track.PortTrackTypes.METER;
    private final ResourceLocation id;
    private final ResourceLocation cycle;
    private final boolean meter;
    private final boolean singleSided;
    private final Map<BogeySize, Supplier<? extends AbstractBogeyBlock<?>>> blocks = new LinkedHashMap<>();
    private final Map<BogeySize, Supplier<Supplier<? extends BogeyStyle.SizeRenderer>>> renderers = new LinkedHashMap<>();
    private BogeyStyle style;

    BogeyGroup(String id, boolean meter, boolean singleSided) {
        this.id = PortRegistration.resource(id);
        this.cycle = meter ? PortRegistration.resource("meter_bogeys") : AllBogeyStyles.STANDARD_CYCLE_GROUP;
        this.meter = meter;
        this.singleSided = singleSided;
    }

    void add(String blockId, String sizeId, Supplier<Supplier<? extends BogeyStyle.SizeRenderer>> renderer) {
        BogeySize size = new BogeySize(PortRegistration.resource(sizeId), 0.915f / 2f);
        BogeySizes.register(size);
        PortRegistration<VehicleBogeyBlock> block = new PortRegistration<VehicleBogeyBlock>(blockId)
                .factory(properties -> new VehicleBogeyBlock(properties, size, () -> style, meter ? METER : TrackType.STANDARD, singleSided, blockId.startsWith("tkz")))
                .strengthAndTool(2f, 3f).submit();
        blocks.put(size, block);
        renderers.put(size, renderer);
    }

    void register() {
        style = new BogeyStyle(id, cycle, Component.translatable("bogey.style." + id.getNamespace() + "." + id.getPath()),
                () -> SoundEvents.MINECART_RIDING, ParticleTypes.CRIT, ParticleTypes.POOF, new CompoundTag(), blocks, renderers);
        AllBogeyStyles.BOGEY_STYLES.put(id, style);
        AllBogeyStyles.CYCLE_GROUPS.computeIfAbsent(cycle, ignored -> new LinkedHashMap<>()).put(id, style);
    }
}

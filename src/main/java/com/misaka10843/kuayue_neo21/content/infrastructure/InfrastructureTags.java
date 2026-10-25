package com.misaka10843.kuayue_neo21.content.infrastructure;

import com.misaka10843.kuayue_neo21.content.PortRegistration;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class InfrastructureTags {
    public static final TagKey<Block> PILLAR_MIDDLE = TagKey.create(Registries.BLOCK, PortRegistration.resource("overhead_pillar_middle"));
}

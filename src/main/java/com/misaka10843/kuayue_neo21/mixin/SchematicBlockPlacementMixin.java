package com.misaka10843.kuayue_neo21.mixin;

import com.misaka10843.kuayue_neo21.content.SchematicPlacementRules;
import com.simibubi.create.foundation.utility.BlockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = BlockHelper.class, remap = false)
public abstract class SchematicBlockPlacementMixin {
    @Redirect(method = "placeSchematicBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;setPlacedBy(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;)V", remap = true))
    private static void kuayue$placeOnlyBedFoot(Block block, Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (SchematicPlacementRules.needsPlacementCallback(state)) block.setPlacedBy(level, pos, state, placer, stack);
    }
}

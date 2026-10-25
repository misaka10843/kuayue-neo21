package com.misaka10843.kuayue_neo21.content;

import com.misaka10843.kuayue_neo21.legacy.block.panels.base.CompanyTrainPanel;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.behaviour.SimpleBlockMovingInteraction;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;

public final class VehicleDoorInteraction extends SimpleBlockMovingInteraction {
    @Override
    public boolean handlePlayerInteraction(Player player, InteractionHand hand, BlockPos root, AbstractContraptionEntity entity) {
        if (player.level().isClientSide) return true;
        if (!super.handlePlayerInteraction(player, hand, root, entity)) return false;
        var contraption = entity.getContraption();
        boolean open = contraption.getBlocks().get(root).state().getValue(BlockStateProperties.OPEN);
        for (var entry : List.copyOf(contraption.getBlocks().entrySet())) {
            var info = entry.getValue();
            if (!(info.state().getBlock() instanceof CompanyTrainPanel) || !info.state().hasProperty(BlockStateProperties.OPEN) || info.nbt() == null) continue;
            var tag = info.nbt();
            BlockPos parent = entry.getKey().offset(tag.getInt("px"), tag.getInt("py"), tag.getInt("pz"));
            if (parent.equals(root)) setContraptionBlockData(entity, entry.getKey(),
                    new StructureBlockInfo(info.pos(), info.state().setValue(BlockStateProperties.OPEN, open), info.nbt()));
        }
        contraption.invalidateColliders();
        return true;
    }

    @Override
    protected BlockState handle(Player player, Contraption contraption, BlockPos pos, BlockState state) {
        boolean open = !state.getValue(BlockStateProperties.OPEN);
        playSound(player, open ? SoundEvents.IRON_DOOR_OPEN : SoundEvents.IRON_DOOR_CLOSE, 1f);
        return state.setValue(BlockStateProperties.OPEN, open);
    }

    @Override protected boolean updateColliders() { return true; }
}

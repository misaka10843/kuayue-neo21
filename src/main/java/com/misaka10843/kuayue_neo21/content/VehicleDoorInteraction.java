package com.misaka10843.kuayue_neo21.content;

import com.misaka10843.kuayue_neo21.legacy.block.panels.base.CompanyTrainPanel;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.TrainPanelProperties;
import com.misaka10843.kuayue_neo21.legacy.block.panels.end_face.TrainEndfaceBlock;
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
    public static boolean canInteract(BlockState state) {
        return state.hasProperty(BlockStateProperties.OPEN)
                && (!(state.getBlock() instanceof TrainEndfaceBlock end) || end.DOOR_TYPE != TrainPanelProperties.DoorType.NO_DOOR);
    }

    @Override
    public boolean handlePlayerInteraction(Player player, InteractionHand hand, BlockPos root, AbstractContraptionEntity entity) {
        var infoAtRoot = entity.getContraption().getBlocks().get(root);
        if (infoAtRoot == null || !canInteract(infoAtRoot.state())) return false;
        if (player.level().isClientSide) return true;
        var roots = VehicleDoorPairs.roots(root, infoAtRoot.state(), pos -> {
            var info = entity.getContraption().getBlocks().get(pos);
            return info == null ? net.minecraft.world.level.block.Blocks.AIR.defaultBlockState() : info.state();
        });
        if (!super.handlePlayerInteraction(player, hand, root, entity)) return false;
        var contraption = entity.getContraption();
        boolean open = contraption.getBlocks().get(root).state().getValue(BlockStateProperties.OPEN);
        for (BlockPos paired : roots) {
            if (paired.equals(root)) continue;
            var info = contraption.getBlocks().get(paired);
            if (info.state().getValue(BlockStateProperties.OPEN) != open)
                setContraptionBlockData(entity, paired, new StructureBlockInfo(info.pos(), info.state().setValue(BlockStateProperties.OPEN, open), info.nbt()));
        }
        for (var entry : List.copyOf(contraption.getBlocks().entrySet())) {
            var info = entry.getValue();
            if (!(info.state().getBlock() instanceof CompanyTrainPanel) || !info.state().hasProperty(BlockStateProperties.OPEN) || info.nbt() == null) continue;
            var tag = info.nbt();
            BlockPos parent = entry.getKey().offset(tag.getInt("px"), tag.getInt("py"), tag.getInt("pz"));
            if (roots.contains(parent)) setContraptionBlockData(entity, entry.getKey(),
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

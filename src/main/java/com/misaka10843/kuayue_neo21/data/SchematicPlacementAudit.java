package com.misaka10843.kuayue_neo21.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.misaka10843.kuayue_neo21.content.VehicleExtensionPlacement;
import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainPanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.CompanyTrainBlockEntity;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.CompanyTrainPanel;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.material.FluidState;

public final class SchematicPlacementAudit {
    public static Map<String, Object> run(Path project, List<String> failures) throws IOException {
        Path fixture = project.resolve(System.getProperty("kuayue.schematicFixture"));
        JsonObject data = JsonParser.parseString(Files.readString(fixture)).getAsJsonObject();
        var palette = data.getAsJsonArray("palette");
        var entries = data.getAsJsonArray("blocks");
        var original = new LinkedHashMap<BlockPos, BlockState>();
        var savedEntities = new HashMap<BlockPos, BlockEntity>();
        var missing = new LinkedHashMap<String, Integer>();
        for (var element : entries) {
            var entry = element.getAsJsonObject();
            var position = entry.getAsJsonArray("pos");
            BlockPos pos = new BlockPos(position.get(0).getAsInt(), position.get(1).getAsInt(), position.get(2).getAsInt());
            var definition = palette.get(entry.get("state").getAsInt()).getAsJsonObject();
            String name = definition.get("Name").getAsString();
            ResourceLocation id = ResourceLocation.parse(name);
            if (!BuiltInRegistries.BLOCK.containsKey(id)) {
                missing.merge(name, 1, Integer::sum);
                original.put(pos, Blocks.BARRIER.defaultBlockState());
                continue;
            }
            BlockState state = BuiltInRegistries.BLOCK.get(id).defaultBlockState();
            if (definition.has("Properties")) {
                for (var property : definition.getAsJsonObject("Properties").entrySet()) {
                    state = withProperty(state, property.getKey(), property.getValue().getAsString());
                }
            }
            original.put(pos, state);
            if (state.getBlock() instanceof CompanyTrainPanel && entry.has("nbt")) {
                var tag = entry.getAsJsonObject("nbt");
                var entity = new CompanyTrainBlockEntity(pos, state);
                entity.setParentPos(new BlockPos(tag.get("px").getAsInt(), tag.get("py").getAsInt(), tag.get("pz").getAsInt()), state.getValue(CompanyTrainPanel.FACING));
                savedEntities.put(pos, entity);
            }
        }
        int protectedOccupiedCells = 0;
        int replayedRoots = 0;
        var savedView = new PlacementView(original, savedEntities);
        for (var entry : savedEntities.entrySet()) {
            BlockPos root = ((CompanyTrainBlockEntity) entry.getValue()).getParentPos().offset(entry.getKey());
            if (!VehicleExtensionPlacement.canClaim(savedView, root, entry.getKey())) failures.add("Owned extension rejected: " + entry.getKey());
            if (VehicleExtensionPlacement.canClaim(savedView, root.above(16), entry.getKey())) failures.add("Foreign extension accepted: " + entry.getKey());
        }
        for (boolean reverse : new boolean[]{false, true}) {
            var view = new PlacementView(new HashMap<>(), new HashMap<>());
            var order = new ArrayList<>(original.entrySet());
            if (reverse) Collections.reverse(order);
            for (var entry : order) {
                view.states.put(entry.getKey(), entry.getValue());
                view.entities.remove(entry.getKey());
                if (savedEntities.containsKey(entry.getKey())) view.entities.put(entry.getKey(), savedEntities.get(entry.getKey()));
                if (entry.getValue().getBlock() instanceof TrainPanelBlock panel) {
                    replayedRoots++;
                    protectedOccupiedCells += generate(view, entry.getKey(), entry.getValue(), panel);
                }
            }
            for (var entry : original.entrySet()) {
                if (!equivalent(view.getBlockState(entry.getKey()), entry.getValue())) failures.add("Schematic block overwritten: " + entry.getKey());
                if (entry.getValue().getBlock() instanceof TrainPanelBlock panel) {
                    protectedOccupiedCells += generate(view, entry.getKey(), entry.getValue(), panel);
                }
            }
            for (var entry : original.entrySet()) {
                if (!equivalent(view.getBlockState(entry.getKey()), entry.getValue())) failures.add("Schematic block overwritten during deferred repair: " + entry.getKey());
            }
        }
        return Map.of("fixture", project.relativize(fixture).toString(), "blocks", entries.size(),
                "replayedRoots", replayedRoots, "protectedOccupiedCells", protectedOccupiedCells, "unregisteredBlocks", missing);
    }

    private static int generate(PlacementView level, BlockPos root, BlockState state, TrainPanelBlock panel) {
        int protectedCells = 0;
        for (BlockPos part : panel.getOccupiedPositions(level, root, state)) {
            if (part.equals(root)) continue;
            if (!VehicleExtensionPlacement.canClaim(level, root, part)) {
                if (!level.getBlockState(part).isAir()) protectedCells++;
                continue;
            }
            DoorHingeSide hinge = state.hasProperty(CompanyTrainPanel.HINGE) ? state.getValue(CompanyTrainPanel.HINGE) : DoorHingeSide.LEFT;
            boolean open = state.hasProperty(CompanyTrainPanel.OPEN) && state.getValue(CompanyTrainPanel.OPEN);
            BlockState extension = panel.generateCompanyState(state.getValue(TrainPanelBlock.FACING), hinge, open);
            level.states.put(part, extension);
            var entity = new CompanyTrainBlockEntity(part, extension);
            entity.setParentPos(root.subtract(part), extension.getValue(CompanyTrainPanel.FACING));
            level.entities.put(part, entity);
        }
        return protectedCells;
    }

    private static boolean equivalent(BlockState actual, BlockState expected) {
        if (actual.equals(expected)) return true;
        return actual.getBlock() instanceof CompanyTrainPanel && expected.getBlock() instanceof CompanyTrainPanel
                && actual.getBlock().getClass() == expected.getBlock().getClass() && actual.getValues().equals(expected.getValues());
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static BlockState withProperty(BlockState state, String name, String value) {
        var property = state.getBlock().getStateDefinition().getProperty(name);
        if (property == null) return state;
        var parsed = property.getValue(value);
        return parsed.isPresent() ? state.setValue((net.minecraft.world.level.block.state.properties.Property) property, (Comparable) parsed.get()) : state;
    }

    private record PlacementView(Map<BlockPos, BlockState> states, Map<BlockPos, BlockEntity> entities) implements BlockGetter {
        @Override public BlockState getBlockState(BlockPos pos) { return states.getOrDefault(pos, Blocks.AIR.defaultBlockState()); }
        @Override public BlockEntity getBlockEntity(BlockPos pos) { return entities.get(pos); }
        @Override public FluidState getFluidState(BlockPos pos) { return getBlockState(pos).getFluidState(); }
        @Override public int getHeight() { return 384; }
        @Override public int getMinBuildHeight() { return -64; }
    }
}

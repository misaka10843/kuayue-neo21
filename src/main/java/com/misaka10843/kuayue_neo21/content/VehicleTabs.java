package com.misaka10843.kuayue_neo21.content;

import com.misaka10843.kuayue_neo21.KuaYueneo21;
import com.misaka10843.kuayue_neo21.content.bogey.VehicleBogeyBlock;
import com.misaka10843.kuayue_neo21.legacy.initial.AllBlocks;
import java.util.function.Predicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class VehicleTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, KuaYueneo21.MODID);

    public static void initialize() {
        add("carriages", id -> !isLocomotive(id) && !isInterior(id), block -> !(block instanceof VehicleBogeyBlock));
        add("locomotives", VehicleTabs::isLocomotive, block -> !(block instanceof VehicleBogeyBlock));
        add("interiors", VehicleTabs::isInterior, block -> !(block instanceof VehicleBogeyBlock));
        add("bogeys", id -> true, VehicleBogeyBlock.class::isInstance);
        TABS.register("materials", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.kuayueneo21.materials"))
                .icon(() -> AllBlocks.CR_LOGO.item().getDefaultInstance())
                .displayItems((parameters, output) -> VehicleMaterials.ITEMS.values().forEach(item -> output.accept(item.get()))).build());
    }

    private static void add(String id, Predicate<String> ids, Predicate<Block> blocks) {
        TABS.register(id, () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.kuayueneo21." + id))
                .icon(() -> AllBlocks.CR_LOGO.item().getDefaultInstance())
                .displayItems((parameters, output) -> PortRegistration.ENTRIES.stream().filter(entry -> !entry.isHidden())
                        .filter(entry -> ids.test(entry.id()) && blocks.test(entry.get())).forEach(entry -> output.accept(entry.item()))).build());
    }

    private static boolean isLocomotive(String id) { return id.matches(".*(df11g?|df21|df5|hxd3d|ss3|ss8|jy290|240km).*"); }
    private static boolean isInterior(String id) { return id.matches(".*(seat|shelf|luggage|yz_table|boiling_water|flourescent|ac_outdoor|reservoir|carriage_battery|vacuum_toilet|brake_cylinder).*"); }
}

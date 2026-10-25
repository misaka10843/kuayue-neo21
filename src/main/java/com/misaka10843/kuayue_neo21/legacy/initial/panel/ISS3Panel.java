package com.misaka10843.kuayue_neo21.legacy.initial.panel;

import com.misaka10843.kuayue_neo21.KuaYueneo21;
import com.misaka10843.kuayue_neo21.legacy.block.panels.HeadBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainHingePanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.conductor.SS3CowCatcherBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.door.CustomRenderedDoorBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.HingeSlabBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.TrainSlabBlock;
import net.createmod.catnip.data.Couple;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.phys.Vec3;
import com.misaka10843.kuayue_neo21.content.PortRegistration;

public final class ISS3Panel {
    public static final PortRegistration<TrainHingePanelBlock> SS3_PANEL_BOTTOM =
            new PortRegistration<TrainHingePanelBlock>("ss3_panel_bottom").factory(TrainHingePanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainHingePanelBlock> SS3_PANEL_UPPER =
            new PortRegistration<TrainHingePanelBlock>("ss3_panel_upper").factory(TrainHingePanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainHingePanelBlock> SS3_PANEL_UPPER_WINDOWS =
            new PortRegistration<TrainHingePanelBlock>("ss3_panel_upper_windows").factory(TrainHingePanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainHingePanelBlock> SS3_PANEL_BOTTOM_AD =
            new PortRegistration<TrainHingePanelBlock>("ss3_panel_bottom_ad").factory(TrainHingePanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainHingePanelBlock> SS3_PANEL_UPPER_AD =
            new PortRegistration<TrainHingePanelBlock>("ss3_panel_upper_ad").factory(TrainHingePanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainSlabBlock> SS3_CARPORT =
            new PortRegistration<TrainSlabBlock>("ss3_carport").factory(p -> new TrainSlabBlock(p, true)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainSlabBlock> SS3_CARPORT_CENTER =
            new PortRegistration<TrainSlabBlock>("ss3_carport_center").factory(p -> new TrainSlabBlock(p, true)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<HingeSlabBlock> SS3_ROOF =
            new PortRegistration<HingeSlabBlock>("ss3_roof").factory(p -> new HingeSlabBlock(p, true)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<HingeSlabBlock> SS3_CARPORT_AD =
            new PortRegistration<HingeSlabBlock>("ss3_carport_ad").factory(p -> new HingeSlabBlock(p, true)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainSlabBlock> SS3_FLOOR =
            new PortRegistration<TrainSlabBlock>("ss3_floor").factory(p -> new TrainSlabBlock(p, false)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainSlabBlock> SS3_CARPORT_SKYLIGHT =
            new PortRegistration<TrainSlabBlock>("ss3_carport_skylight").factory(p -> new TrainSlabBlock(p, false)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<HingeSlabBlock> SS3_ROOF_VENT =
            new PortRegistration<HingeSlabBlock>("ss3_roof_vent").factory(p -> new HingeSlabBlock(p, true)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainSlabBlock> SS3_FLOOR_CENTER =
            new PortRegistration<TrainSlabBlock>("ss3_floor_center").factory(p -> new TrainSlabBlock(p, false)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainSlabBlock> SS3_TRANSFORMER =
            new PortRegistration<TrainSlabBlock>("ss3_transformer").factory(p -> new TrainSlabBlock(p, false)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<HeadBlock> SS3_HEAD =
            new PortRegistration<HeadBlock>("ss3_head").factory(HeadBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<CustomRenderedDoorBlock> SS3_DOOR =
            new PortRegistration<CustomRenderedDoorBlock>("ss3_door").factory(p -> new CustomRenderedDoorBlock(
                    p,
                    Couple.create(
                            ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "ss3/ss3_door_bottom_lh"),
                            ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "ss3/ss3_door_upper_lh")
                    ),
                    Couple.create(
                            ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "ss3/ss3_door_bottom"),
                            ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "ss3/ss3_door_upper")
                    ),
                    new Vec3(-.1875, 0, 0),
                    new Vec3(0, 0, 0),
                    RenderShape.ENTITYBLOCK_ANIMATED,
                    false
            )).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<SS3CowCatcherBlock> SS3_COWCATCHER =
            new PortRegistration<SS3CowCatcherBlock>("ss3_cowcatcher").factory(SS3CowCatcherBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static void register() {}
}

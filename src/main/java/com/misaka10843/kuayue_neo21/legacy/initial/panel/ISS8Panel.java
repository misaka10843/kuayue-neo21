package com.misaka10843.kuayue_neo21.legacy.initial.panel;

import com.misaka10843.kuayue_neo21.KuaYueneo21;
import com.misaka10843.kuayue_neo21.legacy.block.panels.FullShapeDirectionalBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.HeadBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainHingePanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainPanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.conductor.SS8CowCatcherBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.door.CustomRenderedDoorBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.HingeSlabBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.TrainSlabBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.window.TrainUnOpenableWindowBlock;
import net.createmod.catnip.data.Couple;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import com.misaka10843.kuayue_neo21.content.PortRegistration;

public final class ISS8Panel {
    public static final PortRegistration<TrainUnOpenableWindowBlock> SS8_WINDOWS_SMALL =
            new PortRegistration<TrainUnOpenableWindowBlock>("ss8_windows_small").factory(p -> new TrainUnOpenableWindowBlock(p, 1)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<SS8CowCatcherBlock> SS8_COWCATCHER =
            new PortRegistration<SS8CowCatcherBlock>("ss8_cowcatcher").factory(SS8CowCatcherBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<HingeSlabBlock> SS8_CARPORT =
            new PortRegistration<HingeSlabBlock>("ss8_carport").factory(p -> new HingeSlabBlock(p, true)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<HingeSlabBlock> SS8_CARPORT_AD =
            new PortRegistration<HingeSlabBlock>("ss8_carport_ad").factory(p -> new HingeSlabBlock(p, true)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<HingeSlabBlock> SS8_CARPORT_FAN =
            new PortRegistration<HingeSlabBlock>("ss8_carport_fan").factory(p -> new HingeSlabBlock(p, true)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<HingeSlabBlock> SS8_CARPORT_LINK =
            new PortRegistration<HingeSlabBlock>("ss8_carport_link").factory(p -> new HingeSlabBlock(p, true)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainSlabBlock> SS8_FLOOR =
            new PortRegistration<TrainSlabBlock>("ss8_floor").factory(p -> new TrainSlabBlock(p, false)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainHingePanelBlock> SS8_WINDOWS_BIG =
            new PortRegistration<TrainHingePanelBlock>("ss8_windows_big").factory(p -> new TrainHingePanelBlock(p, new Vec2(-1, 0), new Vec2(2, 1))).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainHingePanelBlock> SS8_PANEL_BOTTOM_AD =
            new PortRegistration<TrainHingePanelBlock>("ss8_panel_bottom_ad").factory(TrainHingePanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainHingePanelBlock> SS8_PANEL_UPPER_AD =
            new PortRegistration<TrainHingePanelBlock>("ss8_panel_upper_ad").factory(TrainHingePanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainHingePanelBlock> SS8_PANEL_BOTTOM =
            new PortRegistration<TrainHingePanelBlock>("ss8_panel_bottom").factory(TrainHingePanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainHingePanelBlock> SS8_PANEL_UPPER =
            new PortRegistration<TrainHingePanelBlock>("ss8_panel_upper").factory(TrainHingePanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<HeadBlock> SS8_HEAD =
            new PortRegistration<HeadBlock>("ss8_head").factory(HeadBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<FullShapeDirectionalBlock> SS8_TRANSFORMER =
            new PortRegistration<FullShapeDirectionalBlock>("ss8_transformer").factory(FullShapeDirectionalBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<HingeSlabBlock> SS8_ROOF =
            new PortRegistration<HingeSlabBlock>("ss8_roof").factory(p -> new HingeSlabBlock(p, true)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainSlabBlock> SS8_CARPORT_CENTER =
            new PortRegistration<TrainSlabBlock>("ss8_carport_center").factory(p -> new TrainSlabBlock(p, true)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<HingeSlabBlock> SS8_SANDBOX_FRONT =
            new PortRegistration<HingeSlabBlock>("ss8_sandbox_front").factory(p -> new HingeSlabBlock(p, false)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<CustomRenderedDoorBlock> SS8_DOOR =
            new PortRegistration<CustomRenderedDoorBlock>("ss8_door").factory(p -> new CustomRenderedDoorBlock(
                    p,
                    Couple.create(
                            ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "ss8/ss8_door_bottom_lh"),
                            ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "ss8/ss8_door_upper_lh")
                    ),
                    Couple.create(
                            ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "ss8/ss8_door_bottom"),
                            ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "ss8/ss8_door_upper")
                    ),
                    new Vec3(0, 0, 0),
                    new Vec3(0, 0, 0),
                    RenderShape.ENTITYBLOCK_ANIMATED,
                    false
            )).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> KM240_MONUMENT =
            new PortRegistration<TrainPanelBlock>("240km_monument").factory(TrainPanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static void register() {}
}

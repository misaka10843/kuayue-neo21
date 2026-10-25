package com.misaka10843.kuayue_neo21.legacy.initial.panel;

import com.misaka10843.kuayue_neo21.KuaYueneo21;
import com.misaka10843.kuayue_neo21.legacy.block.panels.FullShapeDirectionalBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainHingePanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainPanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.conductor.HXD3DCowCatcherBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.door.CustomRenderedDoorBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.HeightSlabBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.TrainSlabBlock;
import net.createmod.catnip.data.Couple;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import com.misaka10843.kuayue_neo21.content.PortRegistration;

public final class I3DPanel {
    public static final PortRegistration<CustomRenderedDoorBlock> DOOR_CABIN_HXD3D =
            new PortRegistration<CustomRenderedDoorBlock>("door_cabin_hxd3d").factory(p -> new CustomRenderedDoorBlock(
                    p,
                    Couple.create(
                            ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "hxd3d/door/door_bottom_hxd3d_left"),
                            ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "hxd3d/door/door_upper_hxd3d_left")
                    ),
                    Couple.create(
                            ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "hxd3d/door/door_bottom_hxd3d_right"),
                            ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "hxd3d/door/door_upper_hxd3d_right")
                    ),
                    new Vec3(-.3125, .25, 0),
                    new Vec3(0, 0, 0),
                    RenderShape.MODEL,
                    false
            )).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<HeightSlabBlock> FLOOR_HXD3D =
            new PortRegistration<HeightSlabBlock>("floor_hxd3d").factory(HeightSlabBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<FullShapeDirectionalBlock> HEAD_HXD3D =
            new PortRegistration<FullShapeDirectionalBlock>("head_hxd3d").factory(FullShapeDirectionalBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<HXD3DCowCatcherBlock> HXD3D_COWCATCHER =
            new PortRegistration<HXD3DCowCatcherBlock>("hxd3d_cowcatcher").factory(HXD3DCowCatcherBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainSlabBlock> HXD3D_CARPORT =
            new PortRegistration<TrainSlabBlock>("hxd3d_carport").factory(p -> new TrainSlabBlock(p, true)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainSlabBlock> HXD3D_CARPORT_CENTRE =
            new PortRegistration<TrainSlabBlock>("hxd3d_carport_center").factory(p -> new TrainSlabBlock(p, true)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainSlabBlock> HXD3D_UNDERBODY_EQUIP =
            new PortRegistration<TrainSlabBlock>("hxd3d_underbody_equip").factory(p -> new TrainSlabBlock(p, false)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> PANEL_HEXIE_HXD3D =
            new PortRegistration<TrainPanelBlock>("panel_hexie_hxd3d").factory(p -> new TrainPanelBlock(p, new Vec2(-1, 0), new Vec2(2, 2))).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> HXD3D_PANEL_BOTTOM =
            new PortRegistration<TrainPanelBlock>("hxd3d_panel_bottom").factory(TrainPanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> PANEL_LOGO_NUM_HXD3D =
            new PortRegistration<TrainPanelBlock>("panel_logo_num_hxd3d").factory(p -> new TrainPanelBlock(p, new Vec2(-1, 0), new Vec2(2, 2))).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> HXD3D_PANEL_MIDDLE =
            new PortRegistration<TrainPanelBlock>("hxd3d_panel_middle").factory(TrainPanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainHingePanelBlock> HXD3D_PANEL_TRANSIT =
            new PortRegistration<TrainHingePanelBlock>("hxd3d_panel_transit").factory(p -> new TrainHingePanelBlock(p, new Vec2(0, 0), new Vec2(1, 2))).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainHingePanelBlock> HXD3D_PANEL_TRANSIT_2 =
            new PortRegistration<TrainHingePanelBlock>("hxd3d_panel_transit_2").factory(TrainHingePanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static void register() {}
}

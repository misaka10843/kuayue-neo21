package com.misaka10843.kuayue_neo21.legacy.initial.panel;

import com.misaka10843.kuayue_neo21.KuaYueneo21;
import com.misaka10843.kuayue_neo21.legacy.block.panels.FullShapeDirectionalBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainHingePanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainPanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.TrainPanelProperties;
import com.misaka10843.kuayue_neo21.legacy.block.panels.carport.DF11GChimneyBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.conductor.DF11GCowCatcherBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.door.CustomRenderedDoorBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.end_face.CustomRenderedEndfaceBlock;
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

public final class I11GPanel {
    public static final PortRegistration<CustomRenderedDoorBlock> DOOR_CABIN_DF11G =
            new PortRegistration<CustomRenderedDoorBlock>("door_cabin_df11g").factory(p -> new CustomRenderedDoorBlock(
                    p,
                    Couple.create(
                            ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "df11g/door/door_cabin_bottom_df11g_right"),
                            ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "df11g/door/door_cabin_upper_df11g_right")
                    ),
                    Couple.create(
                            ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "df11g/door/door_cabin_bottom_df11g_left"),
                            ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "df11g/door/door_cabin_upper_df11g_left")
                    ),
                    new Vec3(0, 0, 0),
                    new Vec3(0, 0, 0),
                    RenderShape.ENTITYBLOCK_ANIMATED,
                    false
            )).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<CustomRenderedDoorBlock> DOOR_EQUIP_ROOM_DF11G =
            new PortRegistration<CustomRenderedDoorBlock>("door_equip_room_df11g").factory(p -> new CustomRenderedDoorBlock(
                    p,
                    Couple.create(
                            ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "df11g/door/door_equip_room_bottom_df11g_right"),
                            ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "df11g/door/door_equip_room_upper_df11g_right")
                    ),
                    Couple.create(
                            ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "df11g/door/door_equip_room_bottom_df11g_left"),
                            ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "df11g/door/door_equip_room_upper_df11g_left")
                    ),
                    new Vec3(-.1875, 0, 0),
                    new Vec3(0, 0, 0),
                    RenderShape.MODEL,
                    false
            )).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<CustomRenderedEndfaceBlock> END_FACE_DF11G =
            new PortRegistration<CustomRenderedEndfaceBlock>("end_face_df11g").factory(p -> new CustomRenderedEndfaceBlock(
                    p,
                    TrainPanelProperties.DoorType.NO_DOOR,
                    null,
                    null,
                    "df11g/df11g_end_face"
            )).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<DF11GCowCatcherBlock> COWCATCHER_DF11G =
            new PortRegistration<DF11GCowCatcherBlock>("cowcatcher_df11g").factory(DF11GCowCatcherBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<FullShapeDirectionalBlock> HEAD_DF11G_2 =
            new PortRegistration<FullShapeDirectionalBlock>("head_df11g_2").factory(FullShapeDirectionalBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<FullShapeDirectionalBlock> DF11G_TANK =
            new PortRegistration<FullShapeDirectionalBlock>("df11g_tank").factory(FullShapeDirectionalBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainSlabBlock> FLOOR_DF11G =
            new PortRegistration<TrainSlabBlock>("floor_df11g").factory(p -> new TrainSlabBlock(p, false)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainSlabBlock> DF11G_CARPORT =
            new PortRegistration<TrainSlabBlock>("df11g_carport").factory(p -> new TrainSlabBlock(p, true)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainSlabBlock> DF11G_CARPORT_CENTRE =
            new PortRegistration<TrainSlabBlock>("df11g_carport_center").factory(p -> new TrainSlabBlock(p, false)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<HingeSlabBlock> DF11G_CARPORT_CENTRE_COOLING_BLIND =
            new PortRegistration<HingeSlabBlock>("df11g_carport_center_cooling_blind").factory(p -> new HingeSlabBlock(p, false)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainSlabBlock> DF11G_CARPORT_CENTRE_FAN_BLIND =
            new PortRegistration<TrainSlabBlock>("df11g_carport_center_fan_blind").factory(p -> new TrainSlabBlock(p, false)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<HingeSlabBlock> DF11G_CARPORT_EH =
            new PortRegistration<HingeSlabBlock>("df11g_carport_eh").factory(p -> new HingeSlabBlock(p, true)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<HingeSlabBlock> DF11G_CARPORT_EH_AD =
            new PortRegistration<HingeSlabBlock>("df11g_carport_eh_ad").factory(p -> new HingeSlabBlock(p, true)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainSlabBlock> DF11G_CARPORT_EH_BLIND =
            new PortRegistration<TrainSlabBlock>("df11g_carport_eh_blind").factory(p -> new TrainSlabBlock(p, true)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainSlabBlock> DF11G_CARPORT_FILTER_BLIND =
            new PortRegistration<TrainSlabBlock>("df11g_carport_filter_blind").factory(p -> new TrainSlabBlock(p, true)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainSlabBlock> DF11G_CARPORT_KUA =
            new PortRegistration<TrainSlabBlock>("df11g_carport_kua").factory(p -> new TrainSlabBlock(p, true)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainSlabBlock> DF11G_CARPORT_YUE =
            new PortRegistration<TrainSlabBlock>("df11g_carport_yue").factory(p -> new TrainSlabBlock(p, true)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<DF11GChimneyBlock> DF11G_CHIMNEY =
            new PortRegistration<DF11GChimneyBlock>("df11g_chimney").factory(p -> new DF11GChimneyBlock(p, false)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> PANEL_KUAYUE_DF11G =
            new PortRegistration<TrainPanelBlock>("panel_kuayue_df11g").factory(p -> new TrainPanelBlock(p, new Vec2(-1, 0), new Vec2(2, 1))).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> PANEL_TOP_DF11G =
            new PortRegistration<TrainPanelBlock>("panel_top_df11g").factory(TrainPanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> PANEL_BOTTOM_DF11G =
            new PortRegistration<TrainPanelBlock>("panel_bottom_df11g").factory(TrainPanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> ROAD_SIGN_DF11G =
            new PortRegistration<TrainPanelBlock>("road_sign_df11g").factory(p -> new TrainPanelBlock(p, new Vec2(-1, 0), new Vec2(2, 1))).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> WINDOW_EQUIP_ROOM_DF11G_1 =
            new PortRegistration<TrainPanelBlock>("window_equip_room_df11g_1").factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(1, 2))).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> WINDOW_EQUIP_ROOM_DF11G_2 =
            new PortRegistration<TrainPanelBlock>("window_equip_room_df11g_2").factory(TrainPanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainHingePanelBlock> WINDOW_PR_DF11G =
            new PortRegistration<TrainHingePanelBlock>("window_pr_df11g").factory(TrainHingePanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainHingePanelBlock> LOUVER_EQUIP_ROOM_DF11G_2_TOP =
            new PortRegistration<TrainHingePanelBlock>("louver_equip_room_df11g_2_top").factory(TrainHingePanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainHingePanelBlock> LOUVER_EQUIP_ROOM_DF11G_2_BOTTOM =
            new PortRegistration<TrainHingePanelBlock>("louver_equip_room_df11g_2_bottom").factory(TrainHingePanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainHingePanelBlock> LOUVER_EQUIP_ROOM_DF11G_1 =
            new PortRegistration<TrainHingePanelBlock>("louver_equip_room_df11g_1").factory(TrainHingePanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainHingePanelBlock> DF11G_PANEL_BOTTOM_EH_BLIND =
            new PortRegistration<TrainHingePanelBlock>("df11g_panel_bottom_eh_blind").factory(TrainHingePanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainHingePanelBlock> DF11G_PANEL_UPPER_PH_BLIND =
            new PortRegistration<TrainHingePanelBlock>("df11g_panel_upper_ph_blind").factory(TrainHingePanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainHingePanelBlock> DF11G_PANEL_BOTTOM_PH_BLIND =
            new PortRegistration<TrainHingePanelBlock>("df11g_panel_bottom_ph_blind").factory(TrainHingePanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> DF11G_PANEL_BOTTOM_BLIND =
            new PortRegistration<TrainPanelBlock>("df11g_panel_bottom_blind").factory(TrainPanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> DF11G_PANEL_UPPER_BLIND =
            new PortRegistration<TrainPanelBlock>("df11g_panel_upper_blind").factory(TrainPanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainUnOpenableWindowBlock> DF11G_PANEL_BOTTOM_COOLING_BLIND =
            new PortRegistration<TrainUnOpenableWindowBlock>("df11g_panel_bottom_cooling_blind").factory(p -> new TrainUnOpenableWindowBlock(p, 2)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainUnOpenableWindowBlock> DF11G_WINDOWS_PH_EH =
            new PortRegistration<TrainUnOpenableWindowBlock>("df11g_windows_ph_eh").factory(p -> new TrainUnOpenableWindowBlock(p, 2)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainUnOpenableWindowBlock> DF11G_PANEL_UPPER_COOLING_BLIND =
            new PortRegistration<TrainUnOpenableWindowBlock>("df11g_panel_upper_cooling_blind").factory(p -> new TrainUnOpenableWindowBlock(p, 2)).strengthAndTool(1.5f, 3f).submit();

    public static void register() {}
}

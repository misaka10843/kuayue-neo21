package com.misaka10843.kuayue_neo21.legacy.initial.panel;

import com.misaka10843.kuayue_neo21.KuaYueneo21;
import com.misaka10843.kuayue_neo21.legacy.block.panels.FullShapeDirectionalBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainHingePanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainPanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.carport.DF11ChimneyBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.conductor.DF11CowCatcherBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.door.CustomRenderedDoorBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.HingeSlabBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.TrainSlabBlock;
import net.createmod.catnip.data.Couple;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import com.misaka10843.kuayue_neo21.content.PortRegistration;

public final class I11Panel {
    public static final PortRegistration<DF11CowCatcherBlock> DF11_COWCATCHER =
            new PortRegistration<DF11CowCatcherBlock>("df11_cowcatcher").factory(DF11CowCatcherBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<FullShapeDirectionalBlock> DF11_HEAD =
            new PortRegistration<FullShapeDirectionalBlock>("df11_head").factory(FullShapeDirectionalBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainSlabBlock> DF11_FLOOR =
            new PortRegistration<TrainSlabBlock>("df11_floor").factory(p -> new TrainSlabBlock(p, false)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<HingeSlabBlock> DF11_CARPORT_EQUIP_B =
            new PortRegistration<HingeSlabBlock>("df11_carport_equip_b").factory(p -> new HingeSlabBlock(p, true)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<HingeSlabBlock> DF11_CARPORT_EQUIP_A =
            new PortRegistration<HingeSlabBlock>("df11_carport_equip_a").factory(p -> new HingeSlabBlock(p, true)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<HingeSlabBlock> DF11_CARPORT_CENTER_COOLING_FAN =
            new PortRegistration<HingeSlabBlock>("df11_carport_center_cooling_fan").factory(p -> new HingeSlabBlock(p, false)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainSlabBlock> DF11_CARPORT_GENERAL =
            new PortRegistration<TrainSlabBlock>("df11_carport_general").factory(p -> new TrainSlabBlock(p, true)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainSlabBlock> DF11_CARPORT_EQUIP_BLINDS =
            new PortRegistration<TrainSlabBlock>("df11_carport_equip_blinds").factory(p -> new TrainSlabBlock(p, true)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainSlabBlock> DF11_CARPORT_LOUVER_FAN =
            new PortRegistration<TrainSlabBlock>("df11_carport_louver_fan").factory(p -> new TrainSlabBlock(p, false)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<DF11ChimneyBlock> DF11_CHIMNEY =
            new PortRegistration<DF11ChimneyBlock>("df11_chimney").factory(p -> new DF11ChimneyBlock(p, false)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<FullShapeDirectionalBlock> DF11_RESERVOIR_TANK =
            new PortRegistration<FullShapeDirectionalBlock>("df11_reservoir_tank").factory(FullShapeDirectionalBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<CustomRenderedDoorBlock> DF11_DOOR =
            new PortRegistration<CustomRenderedDoorBlock>("df11_door").factory(p -> new CustomRenderedDoorBlock(
                    p,
                    Couple.create(
                            ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "df11/door/df11_door_bottom_left"),
                            ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "df11/door/df11_door_upper_left")
                    ),
                    Couple.create(
                            ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "df11/door/df11_door_bottom_right"),
                            ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "df11/door/df11_door_upper_right")
                    ),
                    new Vec3(0, 0, 0),
                    new Vec3(0, 0, 0),
                    RenderShape.ENTITYBLOCK_ANIMATED,
                    false
            )).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> DF11_PANEL_AIR_INTAKE =
            new PortRegistration<TrainPanelBlock>("df11_panel_air_intake").factory(p -> new TrainPanelBlock(p, new Vec2(-1, 0), new Vec2(2, 1))).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainHingePanelBlock> DF11_PANEL_EQUIP_BOTTOM =
            new PortRegistration<TrainHingePanelBlock>("df11_panel_equip_bottom").factory(TrainHingePanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainHingePanelBlock> DF11_WINDOW_EQUIP_SMALL =
            new PortRegistration<TrainHingePanelBlock>("df11_window_equip_small").factory(TrainHingePanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> PANEL_BOTTOM_DF11 =
            new PortRegistration<TrainPanelBlock>("panel_bottom_df11").factory(TrainPanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> PANEL_TOP_DF11 =
            new PortRegistration<TrainPanelBlock>("panel_top_df11").factory(TrainPanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainHingePanelBlock> DF11_PANEL_EQUIP_TOP =
            new PortRegistration<TrainHingePanelBlock>("df11_panel_equip_top").factory(TrainHingePanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> DF11_PANEL_BLIND_TOP =
            new PortRegistration<TrainPanelBlock>("df11_panel_blind_top").factory(TrainPanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> DF11_PANEL_BLIND_BOTTOM =
            new PortRegistration<TrainPanelBlock>("df11_panel_blind_bottom").factory(TrainPanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> DF11_ROAD_SIGN =
            new PortRegistration<TrainPanelBlock>("df11_road_sign").factory(p -> new TrainPanelBlock(p, new Vec2(-1, 0), new Vec2(2, 1))).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> DF11_EQUIP_ROOM_WINDOW_1 =
            new PortRegistration<TrainPanelBlock>("df11_equip_room_window_1").factory(TrainPanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> DF11_EQUIP_ROOM_WINDOW_2 =
            new PortRegistration<TrainPanelBlock>("df11_equip_room_window_2").factory(TrainPanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static void register() {}
}

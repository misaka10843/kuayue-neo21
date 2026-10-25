package com.misaka10843.kuayue_neo21.legacy.initial.panel;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.CarportHingeSlabBlock;

import com.misaka10843.kuayue_neo21.KuaYueneo21;
import com.misaka10843.kuayue_neo21.legacy.block.panels.FullShapeDirectionalBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainHingePanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.TrainPanelShapes;
import com.misaka10843.kuayue_neo21.legacy.block.panels.conductor.DF21HeadBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.conductor.DF21HeadEarlyBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.door.CustomRenderedDoorBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.TrainSlabBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.window.TrainUnOpenableWindowBlock;
import net.createmod.catnip.data.Couple;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import com.misaka10843.kuayue_neo21.content.PortRegistration;

public final class I21Panel {
    public static final PortRegistration<DF21HeadBlock> HEAD_DF21 =
            new PortRegistration<DF21HeadBlock>("head_df21").factory(DF21HeadBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<DF21HeadEarlyBlock> HEAD_EARLY_DF21 =
            new PortRegistration<DF21HeadEarlyBlock>("head_early_df21").factory(DF21HeadEarlyBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<CarportHingeSlabBlock> EXHAUST_FAN_DF21 =
            new PortRegistration<CarportHingeSlabBlock>("exhaust_fan_df21").factory(p -> new CarportHingeSlabBlock(p, true)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<CarportHingeSlabBlock> DYNAMIC_CARPORT_DF21 =
            new PortRegistration<CarportHingeSlabBlock>("dynamic_carport_df21").factory(p -> new CarportHingeSlabBlock(p, true)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<CarportHingeSlabBlock> DYNAMIC_TRANSITION_CARPORT_DF21 =
            new PortRegistration<CarportHingeSlabBlock>("dynamic_transition_carport_df21").factory(p -> new CarportHingeSlabBlock(p, true)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<CarportHingeSlabBlock> DYNAMIC_RADIATOR_DF21 =
            new PortRegistration<CarportHingeSlabBlock>("dynamic_radiator_df21").factory(p -> new CarportHingeSlabBlock(p, true)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<CarportHingeSlabBlock> GENERAL_CARPORT_DF21 =
            new PortRegistration<CarportHingeSlabBlock>("general_carport_df21").factory(p -> new CarportHingeSlabBlock(p, true)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainSlabBlock> GENERAL_SLAB_DF21 =
            new PortRegistration<TrainSlabBlock>("general_slab_df21").factory(p -> new TrainSlabBlock(p, false)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<CustomRenderedDoorBlock> DOOR_CABIN_DF21 =
            new PortRegistration<CustomRenderedDoorBlock>("door_cabin_df21").factory(p -> new CustomRenderedDoorBlock(
                    p,
                    Couple.create(
                            ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "df21/door/df21_cabin_door_bottom_left"),
                            ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "df21/door/df21_cabin_door_upper_left")
                    ),
                    Couple.create(
                            ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "df21/door/df21_cabin_door_bottom_right"),
                            ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "df21/door/df21_cabin_door_upper_right")
                    ),
                    new Vec3(0, 0, 0),
                    new Vec3(0, 0, 0),
                    RenderShape.ENTITYBLOCK_ANIMATED,
                    false
            )).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<CustomRenderedDoorBlock> DOOR_EQUIP_DF21 =
            new PortRegistration<CustomRenderedDoorBlock>("door_equip_df21").factory(p -> new CustomRenderedDoorBlock(
                    p,
                    Couple.create(
                            ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "df21/door/df21_equip_door_bottom_left"),
                            ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "df21/door/df21_equip_door_upper_left")
                    ),
                    Couple.create(
                            ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "df21/door/df21_equip_door_bottom_right"),
                            ResourceLocation.fromNamespaceAndPath(KuaYueneo21.MODID, "df21/door/df21_equip_door_upper_right")
                    ),
                    new Vec3(0, 0, 0),
                    new Vec3(0, 0, 0),
                    RenderShape.ENTITYBLOCK_ANIMATED,
                    false
            )).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainUnOpenableWindowBlock> WINDOW_EQUIP_DF21 =
            new PortRegistration<TrainUnOpenableWindowBlock>("window_equip_df21").factory(p -> new TrainUnOpenableWindowBlock(p, -1, 2)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainUnOpenableWindowBlock> EQUIP_MIDDLE_EARLY_DF21 =
            new PortRegistration<TrainUnOpenableWindowBlock>("equip_middle_early_df21").factory(p -> new TrainUnOpenableWindowBlock(p, 1, 0, 2)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainHingePanelBlock> GENERAL_BOTTOM_SLAB_DF21 =
            new PortRegistration<TrainHingePanelBlock>("general_bottom_slab_df21").factory(p -> new TrainHingePanelBlock(p, new Vec2(0, 0), new Vec2(1, 1))).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<FullShapeDirectionalBlock> FUEL_TANK_DF21 =
            new PortRegistration<FullShapeDirectionalBlock>("fuel_tank_df21").factory(FullShapeDirectionalBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static void register() {}
}

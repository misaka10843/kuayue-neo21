package com.misaka10843.kuayue_neo21.legacy.initial.panel;

import com.misaka10843.kuayue_neo21.legacy.block.panels.FullShapeDirectionalBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainHingePanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.conductor.JY290CowCatcherBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.deco.JY290ACBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.door.JY290DoorBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.TrainSlabBlock;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.phys.Vec2;
import com.misaka10843.kuayue_neo21.content.PortRegistration;

public final class IJY290Panel {
    public static final PortRegistration<FullShapeDirectionalBlock> JY290_HEAD_2 =
            new PortRegistration<FullShapeDirectionalBlock>("jy290_head_2").factory(FullShapeDirectionalBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<FullShapeDirectionalBlock> JY290_HEAD_1 =
            new PortRegistration<FullShapeDirectionalBlock>("jy290_head_1").factory(FullShapeDirectionalBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<JY290CowCatcherBlock> JY290_COWCATCHER =
            new PortRegistration<JY290CowCatcherBlock>("jy290_cowcatcher").factory(JY290CowCatcherBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainSlabBlock> JY290_FLOOR =
            new PortRegistration<TrainSlabBlock>("jy290_floor").factory(p -> new TrainSlabBlock(p, false)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainSlabBlock> JY290_FLOOR_LADDER =
            new PortRegistration<TrainSlabBlock>("jy290_floor_ladder").factory(p -> new TrainSlabBlock(p, false)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainSlabBlock> JY290_CARPORT =
            new PortRegistration<TrainSlabBlock>("jy290_carport").factory(p -> new TrainSlabBlock(p, true)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<FullShapeDirectionalBlock> JY290_EQUIPMENT =
            new PortRegistration<FullShapeDirectionalBlock>("jy290_equipment").factory(FullShapeDirectionalBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainHingePanelBlock> JY290_PANEL =
            new PortRegistration<TrainHingePanelBlock>("jy290_panel").factory(p -> new TrainHingePanelBlock(p, new Vec2(-1, 0), new Vec2(1, 2))).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainHingePanelBlock> JY290_PANEL_LOGO =
            new PortRegistration<TrainHingePanelBlock>("jy290_panel_logo").factory(p -> new TrainHingePanelBlock(p, new Vec2(-1, 0), new Vec2(1, 2))).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<JY290ACBlock> JY290_AC =
            new PortRegistration<JY290ACBlock>("jy290_ac").factory(JY290ACBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainSlabBlock> JY290_VENT =
            new PortRegistration<TrainSlabBlock>("jy290_vent").factory(p -> new TrainSlabBlock(p, true)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<JY290DoorBlock> JY290_DOOR =
            new PortRegistration<JY290DoorBlock>("jy290_door").factory(p -> new JY290DoorBlock(p, new Vec2(0, 0), new Vec2(1, 1))).strengthAndTool(1.5f, 3f).submit();

    public static void register() {}
}

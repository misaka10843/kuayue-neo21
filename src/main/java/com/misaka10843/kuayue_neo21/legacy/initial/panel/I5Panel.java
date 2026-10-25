package com.misaka10843.kuayue_neo21.legacy.initial.panel;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.TrainPanelShapes;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.VariableShapeTrainDoorBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.door.TrainDoorBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.phys.shapes.VoxelShape;

import com.misaka10843.kuayue_neo21.legacy.block.panels.FullShapeDirectionalBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainHingePanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.TrainPanelBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.carport.DF5ChimneyBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.conductor.DF5CowCatcherBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.HingeSlabBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.slab.TrainSlabBlock;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.phys.Vec2;
import com.misaka10843.kuayue_neo21.content.PortRegistration;

public final class I5Panel {
    public static final PortRegistration<FullShapeDirectionalBlock> DF5_END_FACE_1 =
            new PortRegistration<FullShapeDirectionalBlock>("df5_end_face_1").factory(FullShapeDirectionalBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<FullShapeDirectionalBlock> DF5_END_FACE_2 =
            new PortRegistration<FullShapeDirectionalBlock>("df5_end_face_2").factory(FullShapeDirectionalBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<DF5CowCatcherBlock> DF5_COWCATCHER =
            new PortRegistration<DF5CowCatcherBlock>("df5_cowcatcher").factory(DF5CowCatcherBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<FullShapeDirectionalBlock> DF5_FUEL_TANK =
            new PortRegistration<FullShapeDirectionalBlock>("df5_fuel_tank").factory(FullShapeDirectionalBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainSlabBlock> DF5_FLOOR =
            new PortRegistration<TrainSlabBlock>("df5_floor").factory(p -> new TrainSlabBlock(p, false)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainSlabBlock> DF5_CENTER_FLOOR =
            new PortRegistration<TrainSlabBlock>("df5_center_floor").factory(p -> new TrainSlabBlock(p, false)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<HingeSlabBlock> DF5_TRANSIT_FLOOR =
            new PortRegistration<HingeSlabBlock>("df5_transit_floor").factory(p -> new HingeSlabBlock(p, false)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<DF5ChimneyBlock> DF5_CHIMNEY =
            new PortRegistration<DF5ChimneyBlock>("df5_chimney").factory(p -> new DF5ChimneyBlock(p, false)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<HingeSlabBlock> DF5_CARPORT_CABIN =
            new PortRegistration<HingeSlabBlock>("df5_carport_cabin").factory(p -> new HingeSlabBlock(p, false)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainSlabBlock> DF5_COOLING_FAN =
            new PortRegistration<TrainSlabBlock>("df5_cooling_fan").factory(p -> new TrainSlabBlock(p, true)).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> DF5_HANDRAIL =
            new PortRegistration<TrainPanelBlock>("df5_handrail").factory(TrainPanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> DF5_PANEL_BOTTOM =
            new PortRegistration<TrainPanelBlock>("df5_panel_bottom").factory(TrainPanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> DF5_PANEL_TOP =
            new PortRegistration<TrainPanelBlock>("df5_panel_top").factory(TrainPanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> DF5_CARPORT_1 =
            new PortRegistration<TrainPanelBlock>("df5_carport_1").factory(TrainPanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> DF5_CARPORT_2 =
            new PortRegistration<TrainPanelBlock>("df5_carport_2").factory(TrainPanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainHingePanelBlock> DF5_CARPORT_3 =
            new PortRegistration<TrainHingePanelBlock>("df5_carport_3").factory(TrainHingePanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainHingePanelBlock> DF5_CARPORT_4 =
            new PortRegistration<TrainHingePanelBlock>("df5_carport_4").factory(TrainHingePanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> DF5_CARPORT_5 =
            new PortRegistration<TrainPanelBlock>("df5_carport_5").factory(TrainPanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> DF5_CARPORT_GRILLE =
            new PortRegistration<TrainPanelBlock>("df5_carport_grille").factory(TrainPanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> DF5_AIR_INTAKE =
            new PortRegistration<TrainPanelBlock>("df5_air_intake").factory(TrainPanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> DF5_AIR_INTAKE_2 =
            new PortRegistration<TrainPanelBlock>("df5_air_intake_2").factory(TrainPanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> DF5_AIR_INTAKE_3 =
            new PortRegistration<TrainPanelBlock>("df5_air_intake_3").factory(TrainPanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> DF5_RADIATOR_GRID =
            new PortRegistration<TrainPanelBlock>("df5_radiator_grid").factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(1, 2))).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<VariableShapeTrainDoorBlock> DF5_ENGINE_ACCESS_DOOR =
            new PortRegistration<VariableShapeTrainDoorBlock>("df5_engine_access_door").factory(p -> new VariableShapeTrainDoorBlock(p,
                            new Vec2(0, 0), new Vec2(1, 1),
                            () -> (state, level, blockPos, context) ->
                                    TrainPanelShapes.rotateShape(Direction.EAST,
                                            state.getValue(TrainPanelBlock.FACING),
                                            TrainPanelShapes.DF5_ENGINE_ACCESS_DOOR_SHAPE_EAST),
                            () -> (state, level, blockPos, context) -> {
                                if (state.getValue(TrainDoorBlock.OPEN))
                                    return TrainPanelShapes.rotateShape(Direction.EAST,
                                            state.getValue(TrainPanelBlock.FACING),
                                            TrainPanelShapes.DF5_ENGINE_ACCESS_DOOR_OPEN_COLLISION_SHAPE_EAST);
                                return TrainPanelShapes.rotateShape(Direction.EAST,
                                        state.getValue(TrainPanelBlock.FACING),
                                        TrainPanelShapes.DF5_ENGINE_ACCESS_DOOR_COLLISION_SHAPE_EAST);
                            })).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<VariableShapeTrainDoorBlock> DF5_EQUIP_DOOR_1 =
            new PortRegistration<VariableShapeTrainDoorBlock>("df5_equip_door_1").factory(p -> new VariableShapeTrainDoorBlock(p,
                            new Vec2(0, 0), new Vec2(1, 1),
                            () -> (state, level, blockPos, context) -> {
                                DoorHingeSide hingeSide = state.getValue(BlockStateProperties.DOOR_HINGE);
                                VoxelShape shape = TrainPanelShapes.DF5_EQUIP_DOOR_1_SHAPE_EAST;
                                return TrainPanelShapes.rotateShape(Direction.EAST,
                                        state.getValue(TrainPanelBlock.FACING),
                                        hingeSide == DoorHingeSide.RIGHT ? shape :
                                                shape.move(0, 0, -0.375));
                            },
                            () -> (state, level, blockPos, context) -> {
                                DoorHingeSide hingeSide = state.getValue(BlockStateProperties.DOOR_HINGE);
                                VoxelShape collisionShape = TrainPanelShapes.DF5_EQUIP_DOOR_1_COLLISION_SHAPE_EAST;
                                VoxelShape openCollisionShape = TrainPanelShapes.DF5_EQUIP_DOOR_1_OPEN_COLLISION_SHAPE_EAST;
                                if (state.getValue(TrainDoorBlock.OPEN))
                                    return TrainPanelShapes.rotateShape(Direction.EAST,
                                            state.getValue(TrainPanelBlock.FACING),
                                            hingeSide == DoorHingeSide.RIGHT ? openCollisionShape :
                                                    openCollisionShape.move(0, 0, -0.375));
                                return TrainPanelShapes.rotateShape(Direction.EAST,
                                        state.getValue(TrainPanelBlock.FACING),
                                        hingeSide == DoorHingeSide.RIGHT ? collisionShape :
                                                collisionShape.move(0, 0, -0.375));
                            })).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<VariableShapeTrainDoorBlock> DF5_EQUIP_DOOR_2 =
            new PortRegistration<VariableShapeTrainDoorBlock>("df5_equip_door_2").factory(p -> new VariableShapeTrainDoorBlock(p,
                            new Vec2(0, 0), new Vec2(1, 1),
                            () -> (state, level, blockPos, context) ->
                                    TrainPanelShapes.rotateShape(Direction.EAST,
                                            state.getValue(TrainPanelBlock.FACING),
                                            TrainPanelShapes.DF5_ENGINE_ACCESS_DOOR_SHAPE_EAST),
                            () -> (state, level, blockPos, context) -> {
                                if (state.getValue(TrainDoorBlock.OPEN))
                                    return TrainPanelShapes.rotateShape(Direction.EAST,
                                            state.getValue(TrainPanelBlock.FACING),
                                            TrainPanelShapes.DF5_ENGINE_ACCESS_DOOR_OPEN_COLLISION_SHAPE_EAST);
                                return TrainPanelShapes.rotateShape(Direction.EAST,
                                        state.getValue(TrainPanelBlock.FACING),
                                        TrainPanelShapes.DF5_ENGINE_ACCESS_DOOR_COLLISION_SHAPE_EAST);
                            })).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<VariableShapeTrainDoorBlock> DF5_CABIN_DOOR =
            new PortRegistration<VariableShapeTrainDoorBlock>("df5_cabin_door").factory(p -> new VariableShapeTrainDoorBlock(p,
                            new Vec2(0, 0), new Vec2(1, 1),
                            () -> (state, level, blockPos, context) ->
                                    TrainPanelShapes.rotateShape(Direction.EAST,
                                            state.getValue(TrainPanelBlock.FACING),
                                            TrainPanelShapes.DF5_CABIN_DOOR_SHAPE_EAST),
                            () -> (state, level, blockPos, context) -> {
                                if (state.getValue(TrainDoorBlock.OPEN))
                                    return TrainPanelShapes.rotateShape(Direction.EAST,
                                            state.getValue(TrainPanelBlock.FACING),
                                            TrainPanelShapes.DF5_CABIN_DOOR_OPEN_COLLISION_SHAPE_EAST);
                                return TrainPanelShapes.rotateShape(Direction.EAST,
                                        state.getValue(TrainPanelBlock.FACING),
                                        TrainPanelShapes.DF5_CABIN_DOOR_SHAPE_EAST);
                            })).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> DF5_HEAD_END_FACE =
            new PortRegistration<TrainPanelBlock>("df5_head_end_face").factory(TrainPanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> DF5_HEAD_END_FACE_2 =
            new PortRegistration<TrainPanelBlock>("df5_head_end_face_2").factory(TrainPanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainPanelBlock> DF5_HEAD_PANEL =
            new PortRegistration<TrainPanelBlock>("df5_head_panel").factory(p -> new TrainPanelBlock(p, new Vec2(0, 0), new Vec2(1, 2))).strengthAndTool(1.5f, 3f).submit();

    public static final PortRegistration<TrainHingePanelBlock> DF5_COWCATCHER_LADDER =
            new PortRegistration<TrainHingePanelBlock>("df5_cowcatcher_ladder").factory(TrainHingePanelBlock::new).strengthAndTool(1.5f, 3f).submit();

    public static void register() {}
}

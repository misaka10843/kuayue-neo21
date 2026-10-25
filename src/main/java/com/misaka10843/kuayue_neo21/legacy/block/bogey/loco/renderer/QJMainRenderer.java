package com.misaka10843.kuayue_neo21.legacy.block.bogey.loco.renderer;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.createmod.catnip.nbt.NBTHelper;
import net.createmod.catnip.math.VecHelper;
import com.simibubi.create.infrastructure.config.AllConfigs;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;
import com.misaka10843.kuayue_neo21.client.LegacyBogeyRenderer;

public class QJMainRenderer extends LegacyBogeyRenderer {

    private static PartialModel block(String path) {
        return PartialModel.of(com.misaka10843.kuayue_neo21.content.PortRegistration.resource("block/" + path));
    }

    public static final String
            PREVIOUS_SPEED_FACTOR = "PreviousSpeedFactor",
            RECENT_SPEED_FACTOR = "RecentSpeedFactor";

    public static final PartialModel

            QJ_MAIN_DRIVE_WHEEL = block("bogey/qj/main_drive_wheel"),
            QJ_DRIVE_WHEEL_2_4 = block("bogey/qj/drive_wheel_2_4"),
            QJ_DRIVE_WHEEL_1_5 = block("bogey/qj/drive_wheel_1_5"),
            QJ_CRANK = block("bogey/qj/crank"),
            QJ_CRANK_OHTERSIDE = block("bogey/qj/crank_otherside"),
            QJ_EXPANSION_LINK = block("bogey/qj/expansion_link"),
            QJ_EXPANSION_LINK_OTHERSIDE = block("bogey/qj/expansion_link_otherside"),
            QJ_CONNECTING_ROD = block("bogey/qj/connecting_rod"),
            QJ_CONNECTING_ROD_OTHERSIDE = block("bogey/qj/connecting_rod_otherside"),
            QJ_ECCENTRIC_ROD = block("bogey/qj/eccentric_rod"),
            QJ_ECCENTRIC_ROD_OTHERSIDE = block("bogey/qj/eccentric_rod_otherside"),
            QJ_CROSSHEAD = block("bogey/qj/crosshead"),
            QJ_CROSSHEAD_OTHERSIDE = block("bogey/qj/crosshead_otherside"),
            QJ_ANCHOR_LINK = block("bogey/qj/anchor_link"),
            QJ_ANCHOR_LINK_OTHERSIDE = block("bogey/qj/anchor_link_otherside"),
            QJ_COMBINATION_LEVER = block("bogey/qj/combination_lever"),
            QJ_COMBINATION_LEVER_OTHERSIDE = block("bogey/qj/combination_lever_otherside"),
            QJ_STEAM_VALVE = block("bogey/qj/steam_valve"),
            QJ_STEAM_VALVE_OTHERSIDE = block("bogey/qj/steam_valve_otherside"),
            QJ_RADIUS_ROD = block("bogey/qj/radius_rod"),
            QJ_RADIUS_ROD_OTHERSIDE = block("bogey/qj/radius_rod_otherside"),
            QJ_OIL_PRESS = block("bogey/qj/oil_press"),
            QJ_OIL_PRESS_OTHERSIDE = block("bogey/qj/oil_press_otherside"),
            QJ_OIL_PRESS_ROD = block("bogey/qj/oil_press_rod"),
            QJ_OIL_PRESS_ROD_OTHERSIDE = block("bogey/qj/oil_press_rod_otherside"),
            QJ_REVERSING = block("bogey/qj/reversing"),
            QJ_REVERSING_BOOM = block("bogey/qj/reversing_boom"),
            QJ_REVERSING_BOOM_OTHERSIDE = block("bogey/qj/reversing_boom_otherside"),
            QJ_BOGEY_FRAME = block("bogey/qj/qj_bogey_frame");

    public static final double L1 = 0.225 * 16;
    public static final double L2 = 27.399968;
    public static final double L3 = 0.607 * 16;
    public static final double L4 = 1.9804 * 16;

    public static Carriage carriage;
    public static double lastTickWheelAngle = 0.0F;


    @Override
    public void render(
            CompoundTag bogeyData,
            float wheelAngle,
            PoseStack ms,
            int light,
            VertexConsumer vb,
            boolean inContraption) {

            

        
        float maxSpeed = AllConfigs.server().trains.poweredTrainTopSpeed.getF() / 20;
        
        boolean movingBackwards = false;

        
        
        
        Direction direction =
                bogeyData.contains(LegacyBogeyRenderer.BOGEY_ASSEMBLY_DIRECTION_KEY)
                        ? NBTHelper.readEnum(
                        bogeyData,
                        LegacyBogeyRenderer.BOGEY_ASSEMBLY_DIRECTION_KEY,
                        Direction.class)
                        : Direction.NORTH;

        

        
        boolean rotateY = false;
        
        if ((direction == Direction.EAST || direction == Direction.SOUTH) && !inContraption) {
            ms.mulPose(Axis.YP.rotationDegrees(180));
        }

        boolean inInstancedContraption = vb == null;

        
        
        BogeyModelData mainDriveWheel = getTransform(QJ_MAIN_DRIVE_WHEEL, ms, inInstancedContraption);
        BogeyModelData[] driveWheel24 = getTransform(QJ_DRIVE_WHEEL_2_4, ms, inInstancedContraption,2);
        BogeyModelData[] driveWheel15 = getTransform(QJ_DRIVE_WHEEL_1_5, ms, inInstancedContraption,2);
        BogeyModelData crank = getTransform(QJ_CRANK, ms, inInstancedContraption);
        BogeyModelData crankOtherside = getTransform(QJ_CRANK_OHTERSIDE, ms, inInstancedContraption);
        BogeyModelData expansionLink = getTransform(QJ_EXPANSION_LINK, ms, inInstancedContraption);
        BogeyModelData expansionLinkOtherside = getTransform(QJ_EXPANSION_LINK_OTHERSIDE, ms, inInstancedContraption);
        BogeyModelData connectingRod = getTransform(QJ_CONNECTING_ROD, ms, inInstancedContraption);
        BogeyModelData connectingRodOtherside = getTransform(QJ_CONNECTING_ROD_OTHERSIDE, ms, inInstancedContraption);
        BogeyModelData eccentricRod = getTransform(QJ_ECCENTRIC_ROD, ms, inInstancedContraption);
        BogeyModelData eccentricRodOtherside = getTransform(QJ_ECCENTRIC_ROD_OTHERSIDE, ms, inInstancedContraption);
        BogeyModelData crosshead = getTransform(QJ_CROSSHEAD, ms, inInstancedContraption);
        BogeyModelData crossheadOtherside = getTransform(QJ_CROSSHEAD_OTHERSIDE, ms, inInstancedContraption);
        BogeyModelData anchorLink = getTransform(QJ_ANCHOR_LINK, ms, inInstancedContraption);
        BogeyModelData anchorLinkOtherside = getTransform(QJ_ANCHOR_LINK_OTHERSIDE, ms, inInstancedContraption);

        BogeyModelData combinationLever = getTransform(QJ_COMBINATION_LEVER, ms, inInstancedContraption);
        BogeyModelData combinationLeverOtherside = getTransform(QJ_COMBINATION_LEVER_OTHERSIDE, ms, inInstancedContraption);
        BogeyModelData steamValve = getTransform(QJ_STEAM_VALVE, ms, inInstancedContraption);
        BogeyModelData steamValveOtherside = getTransform(QJ_STEAM_VALVE_OTHERSIDE, ms, inInstancedContraption);
        BogeyModelData radiusRod = getTransform(QJ_RADIUS_ROD, ms, inInstancedContraption);
        BogeyModelData radiusRodOtherside = getTransform(QJ_RADIUS_ROD_OTHERSIDE, ms, inInstancedContraption);
        BogeyModelData oilPress = getTransform(QJ_OIL_PRESS, ms, inInstancedContraption);
        BogeyModelData oilPressOtherside = getTransform(QJ_OIL_PRESS_OTHERSIDE, ms, inInstancedContraption);
        BogeyModelData oilPressRod = getTransform(QJ_OIL_PRESS_ROD, ms, inInstancedContraption);
        BogeyModelData oilPressRodOtherside = getTransform(QJ_OIL_PRESS_ROD_OTHERSIDE, ms, inInstancedContraption);
        BogeyModelData reversingBoom = getTransform(QJ_REVERSING_BOOM, ms, inInstancedContraption);
        BogeyModelData reversingBoomOtherside = getTransform(QJ_REVERSING_BOOM_OTHERSIDE, ms, inInstancedContraption);
        BogeyModelData reversing = getTransform(QJ_REVERSING, ms, inInstancedContraption);
        BogeyModelData frame = getTransform(QJ_BOGEY_FRAME, ms, inInstancedContraption);

        
        frame.render(ms, light,vb);

        
        double rWheelAngle = Math.toRadians(wheelAngle);
        double rWheelAngle2 = Math.toRadians(wheelAngle - 90);
        double rWheelAngleOtherside = Math.toRadians(wheelAngle + 90);
        double rWheelAngleOtherside2 = Math.toRadians(wheelAngle + 90 - 90);

        double LBD2 = ((L1 * L1) + (L4 * L4) - (2 * L1 * L4 * Math.cos(rWheelAngle2 - Math.toRadians(90))));
        double LBD = Math.sqrt(LBD2);
        double LBD2Otherside = ((L1 * L1) + (L4 * L4) - (2 * L1 * L4 * Math.cos(rWheelAngleOtherside2 - Math.toRadians(90))));
        double LBDOtherside = Math.sqrt(LBD2Otherside);

        double PHI1 = Math.toDegrees(Math.asin(L1 / LBD * Math.sin(rWheelAngle2)));
        double PHI2 = Math.toDegrees(Math.acos((LBD2 + L3 * L3 - L2 * L2) / (2 * LBD * L3)));
        double PHI1Otherside = Math.toDegrees(Math.asin(L1 / LBDOtherside * Math.sin(rWheelAngleOtherside2)));
        double PHI2Otherside = Math.toDegrees(Math.acos((LBD2Otherside + L3 * L3 - L2 * L2) / (2 * LBDOtherside * L3)));

        double THITA3 = PHI1 + PHI2;
        double THITA2 = Math.toDegrees(Math.asin(((L3 * Math.sin(Math.toRadians(-THITA3 - 11.4502))) + (L1 * Math.sin(rWheelAngle2 + Math.toRadians(70)))) / L2));
        double THITA3Otherside = PHI1Otherside + PHI2Otherside;
        double THITA2Otherside = Math.toDegrees(Math.asin(((L3 * Math.sin(Math.toRadians(-THITA3Otherside - 11.4502))) + (L1 * Math.sin(rWheelAngleOtherside2 + Math.toRadians(70)))) / L2));

        
        double crankRotateX = Math.toDegrees(Math.atan(-Math.cos(rWheelAngle) * 0.12)) + 6.89f;
        
        double crankX = (0.4 * Math.sin(rWheelAngle) - 3.045 + Math.sqrt(3.045 * 3.045 - 0.4 * 0.4 * Math.cos(rWheelAngle) * Math.cos(rWheelAngle)));
        double crankTranslateZ = -0.1 - (crankX);

        double crankRotateXOtherside = Math.toDegrees(Math.atan(-Math.cos(rWheelAngleOtherside) * 0.12)) + 6.89f;
        double crankXOtherside = (0.4 * Math.sin(rWheelAngleOtherside) - 3.045 + Math.sqrt(3.045 * 3.045 - 0.4 * 0.4 * Math.cos(rWheelAngleOtherside) * Math.cos(rWheelAngleOtherside)));
        double crankTranslateZOtherside = -0.1 - (crankXOtherside);

        
        double expansionLinkAngle = (THITA3 * 0.76 - 20) * 1;
        double expansionLinkAngleOtherside = (THITA3Otherside * 0.76 - 20) * 1;

        
        double connectingRodTransY = -0.38 - Math.cos(rWheelAngle + Math.toRadians(180)) * 0.38;
        double connectingRodTransZ = -0 + Math.sin(rWheelAngle + Math.toRadians(180)) * 0.38;
        double connectingRodTransYOtherside = -0.38 - Math.cos(rWheelAngleOtherside + Math.toRadians(180)) * 0.38;
        double connectingRodTransZOtherside = -0 + Math.sin(rWheelAngleOtherside + Math.toRadians(180)) * 0.38;
        double connectingRodTransYOthersideRotateY = -0.38 - Math.cos(rWheelAngle2 + Math.toRadians(180)) * 0.38;
        double connectingRodTransZOthersideRotateY = -0 + Math.sin(rWheelAngle2 + Math.toRadians(180)) * 0.38;

        
        double eccentricRodRotateX = THITA2 + 15;
        double eccentricRodTranslateY = -Math.cos(rWheelAngle2) * 0.225;
        double eccentricRodTranslateZ = -(Math.sin(rWheelAngle2) * 0.225 + 0.225);
        double eccentricRodRotateXOtherside = THITA2Otherside + 15;
        double eccentricRodTranslateYOtherside = -Math.cos(rWheelAngleOtherside2) * 0.225;
        double eccentricRodTranslateZOtherside = -(Math.sin(rWheelAngleOtherside2) * 0.225 + 0.225);

        
        double crossheadTranslateZ = crankX;
        double crossheadTranslateZOtherside = crankXOtherside;

        
        double anchorLinkRotateX = -Math.toDegrees(Math.atan(Math.abs(Math.sin(rWheelAngle - Math.toRadians(90))))) * 0.1 + 7;
        double anchorLinkTranslateZ = crankX;
        double anchorLinkRotateXOtherside = -Math.toDegrees(Math.atan(Math.abs(Math.sin(rWheelAngleOtherside - Math.toRadians(90))))) * 0.1 + 7;
        double anchorLinkTranslateZOtherside = crankXOtherside;

        

        
        mainDriveWheel.rotateY(rotateY ? 0 : 180).translate(0, 1.1, 0).rotateX(rotateY ? wheelAngle : -wheelAngle).render(ms, light, vb);
        driveWheel24[0].rotateY(rotateY ? 0 : 180).translate(0,1.1,-1.6).rotateX(rotateY ? wheelAngle : -wheelAngle).render(ms, light,vb);
        driveWheel24[1].rotateY(rotateY ? 0 : 180).translate(0,1.1,1.6).rotateX(rotateY ? wheelAngle : -wheelAngle).render(ms, light,vb);
        driveWheel15[0].rotateY(rotateY ? 0 : 180).translate(0,1.1,-3.2).rotateX(rotateY ? wheelAngle : -wheelAngle).render(ms, light,vb);
        driveWheel15[1].rotateY(rotateY ? 0 : 180).translate(0,1.1,3.2).rotateX(rotateY ? wheelAngle : -wheelAngle).render(ms, light,vb);

        
        crank.translate(rotateY ? -1.15 : 1.15, 1.13, rotateY ? (2.60 + (-crankTranslateZ)) : (-2.80 + (-crankTranslateZ)))
                .rotateY(rotateY ? 0 : 180)
                .rotateX(-crankRotateX)
                .render(ms, light,vb);
        crankOtherside.translate(rotateY ? 1.15 : -1.15, 1.13, rotateY ? (2.80 + (crankTranslateZOtherside)) : (-2.80 + (-crankTranslateZOtherside)))
                .rotateY(rotateY ? 0 : 180)
                .rotateX(rotateY ? crankRotateXOtherside - 15 : -crankRotateXOtherside)
                .render(ms, light,vb);

        
        expansionLink.translate(rotateY ? -1.32 : 1.32, 1.74, rotateY ? 1.79 : -1.79)
                .rotateY(rotateY ? 180 : 0)
                .rotateX(expansionLinkAngle)
                .render(ms, light,vb);
        expansionLinkOtherside.translate(rotateY ? 1.32 : -1.32, 1.74, rotateY ? 1.79 : -1.79)
                .rotateY(rotateY ? 180 : 0)
                .rotateX(rotateY ? -expansionLinkAngleOtherside + 40 : expansionLinkAngleOtherside)
                .render(ms, light,vb);

        
        connectingRod.translate(rotateY ? -0.96 : 0.96, 1.5 + connectingRodTransY, 0 - connectingRodTransZ)
                .rotateY(rotateY ? 180 : 0)
                .render(ms, light,vb);
        connectingRodOtherside
                .translate(rotateY ? 0.96 : -0.96,
                        rotateY ? 1.5 + connectingRodTransYOthersideRotateY : 1.5 + connectingRodTransYOtherside,
                        rotateY ? 0 - connectingRodTransZOthersideRotateY : 0 - connectingRodTransZOtherside)
                .rotateY(rotateY ? 180 : 0)
                .render(ms, light,vb);

        
        eccentricRod.translate(1.36, 1.10 + eccentricRodTranslateY, 0.23 + eccentricRodTranslateZ)
                .rotateX(eccentricRodRotateX)
                .render(ms, light,vb);
        eccentricRodOtherside.translate(-1.36, 1.10 + eccentricRodTranslateYOtherside, 0.23 + eccentricRodTranslateZOtherside)
                .rotateX(eccentricRodRotateXOtherside)
                .render(ms, light,vb);

        
        crosshead.translate(1.15, 1.13 ,-2.85 + crossheadTranslateZ)
                .render(ms, light,vb);
        crossheadOtherside.translate(-1.15, 1.13 ,-2.85 + crossheadTranslateZOtherside)
                .render(ms, light,vb);

        
        anchorLink.translate(1.30, 0.83 ,-2.85 + anchorLinkTranslateZ)
                .rotateX(anchorLinkRotateX)
                .render(ms, light,vb);
        anchorLinkOtherside.translate(-1.30, 0.83 ,-2.85 + anchorLinkTranslateZOtherside)
                .rotateX(anchorLinkRotateXOtherside)
                .render(ms, light,vb);

        
        
        if (carriage == null) {
            staticPartialRender(combinationLever, combinationLeverOtherside,
                    steamValve, steamValveOtherside,
                    radiusRod, radiusRodOtherside,
                    oilPress, oilPressOtherside,
                    oilPressRod, oilPressRodOtherside,
                    reversingBoom, reversingBoomOtherside, reversing,
                    ms, light, vb);
            return;
        }

        
        
        CarriageContraptionEntity entity = carriage.anyAvailableEntity();

        if (entity == null) {
            staticPartialRender(combinationLever, combinationLeverOtherside,
                    steamValve, steamValveOtherside,
                    radiusRod, radiusRodOtherside,
                    oilPress, oilPressOtherside,
                    oilPressRod, oilPressRodOtherside,
                    reversingBoom, reversingBoomOtherside, reversing,
                    ms, light, vb);
            return;
        }

        
        double tickXo = entity.getX();
        double tickYo = entity.getY();
        double tickZo = entity.getZ();
        float tickYaw = entity.yaw;
        Vec3 prevPositionVec = entity.getPrevPositionVec();

        double speed = 0;

        Vec3 diff = prevPositionVec.subtract(tickXo, tickYo, tickZo);
        Vec3 relativeDiff = VecHelper.rotate(diff, tickYaw, Direction.Axis.Y);
        double signum = Math.signum(relativeDiff.x);
        
        speed = Math.abs(diff.length() * signum);

        updateCompoundTagSpeedData(bogeyData,(float) speed, true);

        
        movingBackwards = signum < 0;
        

        
        
        int direct = movingBackwards ? -1 : 1;
        
        double speedRatio = Math.abs(speed / maxSpeed);

        double DTHITA = getDTHITA(Math.abs(getActualSpeedRatioLazy(bogeyData, maxSpeed)));
        

        
        double combinationLeverMoveZ = -Math.sin(rWheelAngle-Math.PI / 2) * 2 * direct * DTHITA / 16;
        double combinationLeverMoveZOtherside = -Math.sin(rWheelAngle) * 2 * direct * DTHITA / 16;

        double angelCombinationLever = (-Math.toDegrees(Math.atan((crankX / 1)/ (0.94) ))
                -1 + Math.sin(rWheelAngle + Math.PI / 2) * 8 * direct * DTHITA);

        double angelCombinationLeverOtherside = (-Math.toDegrees(Math.atan((crankXOtherside / 1)/ (0.94) ))
                -1 + Math.sin(rWheelAngleOtherside + Math.PI / 2) * 8 * direct * DTHITA);

        
        double steamValveTranslateZ = -(Math.sin(rWheelAngle * direct - Math.toRadians(120)) * 2 * DTHITA * direct) / 16;
        double steamValveTranslateZOtherside = -(Math.sin(rWheelAngleOtherside * direct - Math.toRadians(120)) * 2 * DTHITA * direct) / 16;

        
        double radiusRodRotateX = direct * DTHITA * 12 + Math.sin(rWheelAngle + Math.toRadians(80)) * 1 * DTHITA;
        double radiusRodTranslateZ = (Math.sin(rWheelAngle + Math.toRadians(90)) * 2 * direct * DTHITA) / 16;
        double radiusRodRotateXOtherside = direct * DTHITA * 12 + Math.sin(rWheelAngleOtherside + Math.toRadians(80)) * 1 * DTHITA;
        double radiusRodTranslateZOtherside = (Math.sin(rWheelAngleOtherside + Math.toRadians(90)) * 2 * direct * DTHITA) / 16;

        
        double t1 = Math.sin(rWheelAngle) * Math.toRadians(15) - Math.toRadians(103);
        double t1Otherside = Math.sin(rWheelAngleOtherside) * Math.toRadians(15) - Math.toRadians(103);

        double oilPressRodRotateX = Math.cos(rWheelAngle - Math.toRadians(90)) * 15 + 10 + DTHITA
                * (Math.sin(rWheelAngle - Math.toRadians(99)) * 45 - 4) * direct;
        double oilPressRodRotateXOtherside = Math.cos(rWheelAngleOtherside - Math.toRadians(90)) * 15 + 10 + DTHITA
                * (Math.sin(rWheelAngleOtherside - Math.toRadians(99)) * 45 - 4) * direct;

        double oilPressRodTranslateY = (-6.1 - Math.sin(t1) * 6) / 16;
        double oilPressRodTranslateYOtherside = (-6.1 - Math.sin(t1Otherside) * 6) / 16;

        double oilPressRodTranslateZ = -(1.5 + Math.cos(t1) * 6 + DTHITA * Math.sin(rWheelAngle - Math.toRadians(94))
                * 2.4 * direct) / 16;
        double oilPressRodTranslateZOtherside = -(1.5 + Math.cos(t1Otherside) * 6 + DTHITA * Math.sin(rWheelAngleOtherside - Math.toRadians(94))
                * 2.4 * direct) / 16;

        
        double oilPressRotateX = -Math.sin(rWheelAngle) * 5 * (1 - DTHITA) + 7 + (-Math.sin(rWheelAngle * 2) * 7
                + direct * Math.sin(rWheelAngle + Math.toRadians(120)) * 7) * direct * DTHITA
                + (Math.exp(direct * DTHITA) - 1) * Math.sin(rWheelAngle + Math.toRadians(220)) * 10;

        double oilPressRotateXOtherside = -Math.sin(rWheelAngleOtherside) * 5 * (1 - DTHITA) + 7 + (-Math.sin(rWheelAngleOtherside * 2) * 7
                + direct * Math.sin(rWheelAngleOtherside + Math.toRadians(120)) * 7) * direct * DTHITA
                + (Math.exp(direct * DTHITA) - 1) * Math.sin(rWheelAngleOtherside + Math.toRadians(220)) * 10;

        
        double reversingBoomRotateX = direct * (expansionLinkAngle * 0.7 - 16) * DTHITA;
        double reversingBoomRotateXOtherside = direct * (expansionLinkAngleOtherside * 0.7 - 16) * DTHITA;

        double reversingBoomTranslateY = (-direct * DTHITA * 1 + Math.pow(-direct * DTHITA, 3) * 3
                - Math.sqrt((Math.exp(-direct * DTHITA +1) - 1)) * 0.3) / 16;
        double reversingBoomTranslateYOtherside = reversingBoomTranslateY;

        double reversingBoomTranslateZ = (-Math.pow(-direct * DTHITA, 2) * 1.4) / 16;
        double reversingBoomTranslateZOtherside = reversingBoomTranslateZ;

        
        double reversingRotateX = -direct * DTHITA * 25 + Math.pow(-direct * DTHITA, 3) * 11;

        
        combinationLever.translate(1.30, 1.75 ,-3.33 + combinationLeverMoveZ)
                .rotateX(angelCombinationLever)
                .render(ms, light,vb);
        combinationLeverOtherside.translate(-1.30, 1.75 ,-3.33 + combinationLeverMoveZOtherside)
                .rotateX(angelCombinationLeverOtherside)
                .render(ms, light,vb);

        
        steamValve.translate(1.30, 1.62, -3.92 + steamValveTranslateZ)
                .render(ms, light,vb);
        steamValveOtherside.translate(-1.30, 1.62, -3.92 + steamValveTranslateZOtherside)
                .render(ms, light,vb);

        
        radiusRod.translate(1.31, 1.80, -3.38 + radiusRodTranslateZ)
                .rotateX(radiusRodRotateX)
                .render(ms, light,vb);
        radiusRodOtherside.translate(-1.31, 1.80, -3.38 + radiusRodTranslateZOtherside)
                .rotateX(radiusRodRotateXOtherside)
                .render(ms, light,vb);

        
        oilPressRod.translate(1.29, 1.99 + oilPressRodTranslateY, -3.22 + oilPressRodTranslateZ)
                .rotateX(oilPressRodRotateX)
                .render(ms, light,vb);
        oilPressRodOtherside.translate(-1.29, 1.99 + oilPressRodTranslateYOtherside, -3.22 + oilPressRodTranslateZOtherside)
                .rotateX(oilPressRodRotateXOtherside)
                .render(ms, light,vb);

        
        oilPress.translate(1.29, 2.16, -3.42)
                .rotateX(oilPressRotateX)
                .render(ms, light,vb);
        oilPressOtherside.translate(-1.29, 2.16, -3.42)
                .rotateX(oilPressRotateXOtherside)
                .render(ms, light,vb);

        
        reversingBoom.translate(1.18, 2.20 + reversingBoomTranslateY, -2.13 + reversingBoomTranslateZ)
                .rotateX(reversingBoomRotateX)
                .render(ms, light,vb);
        reversingBoomOtherside.translate(-1.18, 2.20 + reversingBoomTranslateYOtherside, -2.13 + reversingBoomTranslateZOtherside)
                .rotateX(reversingBoomRotateXOtherside)
                .render(ms, light,vb);

        
        reversing.translate(0, 2.15, -1.83)
                .rotateX(reversingRotateX)
                .render(ms, light,vb);

        
    }

    public static double tanh(double x){
        return (Math.exp(x) - Math.exp(-x)) / (Math.exp(x) + Math.exp(-x));
    }

    public static double getDTHITA(double x){

        if (x >= 0 && x < 0.1){
            return Math.sin(2 * Math.PI * (5 * x - 0.25)) / 2 + 0.5;
        } else if (x >= 0.1 && x < 0.5){
            return 1;
        } else if (x >= 0.5 && x < 0.667){
            return Math.sin(2 * Math.PI * (3 * x - 0.25)) / 4 + 0.75;
        } else if (x > 0.667){
            return 0.5;
        } else {
            return x;
        }
    }

    
    public static void updateCompoundTagSpeedData(CompoundTag nbt, float recent_speed, boolean animate_direction) {
        if(nbt.getFloat(RECENT_SPEED_FACTOR) != recent_speed){
            nbt.putInt("counter", 0);
            nbt.putFloat(PREVIOUS_SPEED_FACTOR, nbt.getFloat(RECENT_SPEED_FACTOR));
            nbt.putFloat(RECENT_SPEED_FACTOR, recent_speed);
        }
    }

    public static float getActualSpeedRatioLazy(CompoundTag nbt, float max_speed) {
        if(!nbt.contains("recent")) {
            nbt.putFloat("recent", nbt.getFloat(PREVIOUS_SPEED_FACTOR));
            return nbt.getFloat(PREVIOUS_SPEED_FACTOR) / max_speed;
        } else {
            if((Math.abs(nbt.getFloat("recent")) - Math.abs(nbt.getFloat(RECENT_SPEED_FACTOR)))
                    * Math.signum(nbt.getFloat("recent") - nbt.getFloat(RECENT_SPEED_FACTOR)) > 0.01f) {
                nbt.putFloat("recent", nbt.getFloat("recent") +
                        (nbt.getFloat(RECENT_SPEED_FACTOR) - nbt.getFloat(PREVIOUS_SPEED_FACTOR)) * .1f);
                return nbt.getFloat("recent") / max_speed;
            } else return nbt.getFloat(RECENT_SPEED_FACTOR)/max_speed;
        }
    }

    
    public static void staticPartialRender(LegacyBogeyRenderer.BogeyModelData combinationLever, LegacyBogeyRenderer.BogeyModelData combinationLeverOtherside,
                                           LegacyBogeyRenderer.BogeyModelData steamValve, LegacyBogeyRenderer.BogeyModelData steamValveOtherside,
                                           LegacyBogeyRenderer.BogeyModelData radiusRod, LegacyBogeyRenderer.BogeyModelData radiusRodOtherside,
                                           LegacyBogeyRenderer.BogeyModelData oilPress, LegacyBogeyRenderer.BogeyModelData oilPressOtherside,
                                           LegacyBogeyRenderer.BogeyModelData oilPressRod, LegacyBogeyRenderer.BogeyModelData oilPressRodOtherside,
                                           LegacyBogeyRenderer.BogeyModelData reversingBoom, LegacyBogeyRenderer.BogeyModelData reversingBoomOtherside,
                                           LegacyBogeyRenderer.BogeyModelData reversing,
                                           PoseStack ms, int light, VertexConsumer vb){

        
        combinationLever.translate(1.30, 1.75 ,-3.33)
                .render(ms, light,vb);
        combinationLeverOtherside.translate(-1.30, 1.75 ,-3.33 - 0.005)
                .rotateX(-25.95)
                .render(ms, light,vb);
        
        steamValve.translate(1.30, 1.62, -3.92)
                .render(ms, light,vb);
        steamValveOtherside.translate(-1.30, 1.62, -3.92)
                .render(ms, light,vb);
        
        radiusRod.translate(1.31, 1.80, -3.38)
                .render(ms, light,vb);
        radiusRodOtherside.translate(-1.31, 1.80, -3.38)
                .render(ms, light,vb);
        
        oilPressRod.translate(1.29, 1.99 - 0.01, -3.22 - 0.01)
                .rotateX(10)
                .render(ms, light,vb);
        oilPressRodOtherside.translate(-1.29, 1.99, -3.22 - 0.1)
                .rotateX(25)
                .render(ms, light,vb);
        
        oilPress.translate(1.29, 2.16, -3.42)
                .rotateX(7)
                .render(ms, light,vb);
        oilPressOtherside.translate(-1.29, 2.16, -3.42)
                .rotateX(2)
                .render(ms, light,vb);
        
        reversingBoom.translate(1.18, 2.20 - 0.02, -2.13)
                .render(ms, light,vb);
        reversingBoomOtherside.translate(-1.18, 2.20 - 0.02, -2.13)
                .render(ms, light,vb);
        
        reversing.translate(0, 2.15, -1.83)
                .render(ms, light,vb);
    }
    public static class Andesite extends QJMainRenderer {
        @Override
        public void render(
                CompoundTag bogeyData,
                float wheelAngle,
                PoseStack ms,
                int light,
                VertexConsumer vb,
                boolean inContraption) {
            ms.pushPose();
            ms.scale(1.2F, 1, 1);
            super.render(bogeyData, wheelAngle, ms, light, vb, inContraption);
            ms.popPose();
        }
    }
}


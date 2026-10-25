package com.misaka10843.kuayue_neo21.legacy.initial;

import com.misaka10843.kuayue_neo21.content.PortEntityType;
import com.misaka10843.kuayue_neo21.content.PortRegistration;
import com.misaka10843.kuayue_neo21.legacy.block.panels.base.*;
import com.misaka10843.kuayue_neo21.legacy.block.panels.block_entity.*;
import com.misaka10843.kuayue_neo21.legacy.block.panels.carport.DF11GChimneyBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.cr200j.CR200jFrontBlock;
import com.misaka10843.kuayue_neo21.legacy.block.panels.door.*;
import com.misaka10843.kuayue_neo21.legacy.block.panels.end_face.*;
import com.misaka10843.kuayue_neo21.legacy.block.seat.*;
import com.misaka10843.kuayue_neo21.legacy.initial.panel.*;
import net.minecraft.world.level.block.Block;

public final class AllBlocks {
    public static final PortRegistration<Block> CR_LOGO = new PortRegistration<Block>("cr_logo").factory(Block::new).submit();
    public static final PortRegistration<CompanyTrainPanel> COMPANY_TRAIN_PANEL = new PortRegistration<CompanyTrainPanel>("company_panel_block").factory(CompanyTrainPanel::new).hidden().submit();
    public static final PortRegistration<CompanyTrainDoor> COMPANY_TRAIN_DOOR = new PortRegistration<CompanyTrainDoor>("company_panel_door").factory(CompanyTrainDoor::new).hidden().submit();
    public static final PortRegistration<CompanyTrainDoor.Sliding> COMPANY_SLIDING_DOOR = new PortRegistration<CompanyTrainDoor.Sliding>("company_panel_sliding_door").factory(CompanyTrainDoor.Sliding::new).hidden().submit();
    public static final PortRegistration<CompanyTrainSlab> COMPANY_FLOOR = new PortRegistration<CompanyTrainSlab>("company_panel_floor").factory(p -> new CompanyTrainSlab(p, false)).hidden().submit();
    public static final PortRegistration<CompanyTrainSlab> COMPANY_CARPORT = new PortRegistration<CompanyTrainSlab>("company_panel_carport").factory(p -> new CompanyTrainSlab(p, true)).hidden().submit();

    public static final PortEntityType<CompanyTrainBlockEntity> COMPANY_TRAIN_BLOCK_ENTITY = new PortEntityType<>("company_panel", CompanyTrainBlockEntity::new, CompanyTrainPanel.class::isInstance);
    public static final PortEntityType<CustomRenderedDoorEntity> CUSTOM_RENDERED_DOOR_ENTITY = new PortEntityType<>("custom_rendered_door_entity", CustomRenderedDoorEntity::new, CustomRenderedDoorBlock.class::isInstance);
    public static final PortEntityType<CustomRenderedEndfaceEntity> CUSTOM_RENDERED_ENDFACE_ENTITY = new PortEntityType<>("custom_rendered_endface_entity", CustomRenderedEndfaceEntity::new, b -> b instanceof CustomRenderedEndfaceBlock && !(b instanceof MeterCarriageEndFaceBlock) && !(b instanceof DoubleRotateDoorBlock));
    public static final PortEntityType<SingleSlidingDoorEntity> SINGLE_SLIDING_DOOR_ENTITY = new PortEntityType<>("single_sliding_door", SingleSlidingDoorEntity::new, MeterCarriageEndFaceBlock.class::isInstance);
    public static final PortEntityType<DoubleRotateDoorEntity> DOUBLE_ROTATE_DOOR_ENTITY = new PortEntityType<>("double_rotate_door", DoubleRotateDoorEntity::new, DoubleRotateDoorBlock.class::isInstance);
    public static final PortEntityType<DF11GChimneyEntity> DF11G_CHIMNEY_ENTITY = new PortEntityType<>("chimney", DF11GChimneyEntity::new, DF11GChimneyBlock.class::isInstance);
    public static final PortEntityType<SeatBlockEntity> SEAT_BLOCK_ENTITY = new PortEntityType<>("seat", SeatBlockEntity::new, YZSeatBlock.class::isInstance);
    public static final PortEntityType<CR200jFrontBlockEntity> CR200J_FRONT_ENTITY = new PortEntityType<>("cr200j_front", CR200jFrontBlockEntity::new, CR200jFrontBlock.class::isInstance);

    public static void initialize() {
        Class<?>[] families = {C22Panel.class, C25BPanel.class, C25GPanel.class, C25KPanel.class, C25Panel.class, C25TPanel.class, C25ZPanel.class, CFreightPanel.class, CM1Panel.class, CR200JPanel.class, I11GPanel.class, I11Panel.class, I21Panel.class, I3DPanel.class, I5Panel.class, IJY290Panel.class, ISS3Panel.class, ISS8Panel.class, AllDecoBlocks.class};
        for (Class<?> family : families) {
            try {
                Class.forName(family.getName(), true, family.getClassLoader());
            } catch (ClassNotFoundException error) {
                throw new IllegalStateException("Cannot initialize vehicle family " + family.getName(), error);
            }
        }
    }
}

package com.misaka10843.kuayue_neo21.legacy.initial;

import com.misaka10843.kuayue_neo21.content.PortRegistration;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;

public final class ClientInit {
    public static final PartialModel CR200J_HEAD_COUPLER_FAIRING_LEFT = model("coupler_fairing_lh");
    public static final PartialModel CR200J_HEAD_COUPLER_FAIRING_RIGHT = model("coupler_fairing");
    public static final PartialModel CR200J_HEAD_WIND_SHIELD_1 = model("wind_shield_1");
    public static final PartialModel CR200J_HEAD_WIND_SHIELD_2 = model("wind_shield_2");

    private static PartialModel model(String path) {
        return PartialModel.of(PortRegistration.resource("block/carriage/carriage_marshalled_cr200j/head/" + path));
    }
}

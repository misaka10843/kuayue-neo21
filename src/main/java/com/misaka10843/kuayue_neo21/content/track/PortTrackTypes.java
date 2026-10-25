package com.misaka10843.kuayue_neo21.content.track;

import com.misaka10843.kuayue_neo21.content.PortRegistration;
import com.simibubi.create.content.trains.track.TrackMaterial.TrackType;

public final class PortTrackTypes {
    public static final TrackType METER = new TrackType(PortRegistration.resource("meter"), PortTrackBlock::new);
}

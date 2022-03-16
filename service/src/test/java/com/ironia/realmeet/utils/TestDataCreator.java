package com.ironia.realmeet.utils;

import static com.ironia.realmeet.utils.TestConstants.*;

import com.ironia.realmeet.api.model.CreateRoomDTO;
import com.ironia.realmeet.domain.entity.Room;

public final class TestDataCreator {

    private TestDataCreator() {}

    public static Room.Builder newRoomBuilder() {
        return Room.newBuilder().name(DEFAULT_ROOM_NAME).seats(DEFAULT_ROOM_SEATS);
    }

    public static CreateRoomDTO newCreateRoomDTO() {
        return new CreateRoomDTO().name(DEFAULT_ROOM_NAME).seats(DEFAULT_ROOM_SEATS);
    }
}

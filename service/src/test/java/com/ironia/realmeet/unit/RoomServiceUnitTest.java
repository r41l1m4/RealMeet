package com.ironia.realmeet.unit;

import static com.ironia.realmeet.utils.MapperUtils.roomMapper;
import static com.ironia.realmeet.utils.TestConstants.DEFAULT_ROOM_ID;
import static com.ironia.realmeet.utils.TestDataCreator.newCreateRoomDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ironia.realmeet.core.BaseUnitTest;
import com.ironia.realmeet.domain.repository.RoomRepository;
import com.ironia.realmeet.exception.RoomNotFoundException;
import com.ironia.realmeet.service.RoomService;
import com.ironia.realmeet.utils.TestDataCreator;
import com.ironia.realmeet.validator.RoomValidator;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

class RoomServiceUnitTest extends BaseUnitTest {
    private RoomService victim;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private RoomValidator roomValidator;

    @BeforeEach
    void setupEach() {
        victim = new RoomService(roomRepository, roomValidator, roomMapper());
    }

    @Test
    void testGetRoomSuccess() {
        var room = TestDataCreator.newRoomBuilder().id(DEFAULT_ROOM_ID).build();
        when(roomRepository.findByIdAndActive(DEFAULT_ROOM_ID, true)).thenReturn(Optional.of(room));

        var dto = victim.getRoom(DEFAULT_ROOM_ID);

        assertEquals(room.getId(), dto.getId());
        assertEquals(room.getName(), dto.getName());
        assertEquals(room.getSeats(), dto.getSeats());
    }

    @Test
    void testGetRoomNotFound() {
        when(roomRepository.findByIdAndActive(DEFAULT_ROOM_ID, true)).thenReturn(Optional.empty());
        assertThrows(RoomNotFoundException.class, () -> victim.getRoom(DEFAULT_ROOM_ID));
    }

    @Test
    void testCreateRoomSuccess() {
        var createRoomDTO = newCreateRoomDTO();
        var roomDTO = victim.createRoom(createRoomDTO);

        assertEquals(createRoomDTO.getName(), roomDTO.getName());
        assertEquals(createRoomDTO.getSeats(), roomDTO.getSeats());

        Mockito.verify(roomRepository).save(any());
    }
}

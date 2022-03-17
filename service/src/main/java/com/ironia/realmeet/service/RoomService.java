package com.ironia.realmeet.service;

import static java.util.Objects.requireNonNull;

import com.ironia.realmeet.api.model.CreateRoomDTO;
import com.ironia.realmeet.api.model.RoomDTO;
import com.ironia.realmeet.domain.entity.Room;
import com.ironia.realmeet.domain.repository.RoomRepository;
import com.ironia.realmeet.exception.RoomNotFoundException;
import com.ironia.realmeet.mapper.RoomMapper;
import com.ironia.realmeet.validator.RoomValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomValidator roomValidator;
    private final RoomMapper roomMapper;

    public RoomService(RoomRepository roomRepository, RoomValidator roomValidator, RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.roomValidator = roomValidator;
        this.roomMapper = roomMapper;
    }

    public RoomDTO getRoom(Long id) {
        Room room = getActiveRoomOrThrow(id);

        return roomMapper.fromEntityToDto(room);
    }

    public RoomDTO createRoom(CreateRoomDTO createRoomDTO) {
        roomValidator.validate(createRoomDTO);
        var room = roomMapper.fromCreateRoomDtoToEntity(createRoomDTO);
        roomRepository.save(room);
        return roomMapper.fromEntityToDto(room);
    }

    @Transactional
    public void deleteRoom(Long roomId) {
        getActiveRoomOrThrow(roomId);
        roomRepository.deactivate(roomId);
    }

    private Room getActiveRoomOrThrow(Long id) {
        requireNonNull(id);
        return roomRepository
            .findByIdAndActive(id, true)
            .orElseThrow(() -> new RoomNotFoundException("Room " + id + " inexistente"));
    }
}

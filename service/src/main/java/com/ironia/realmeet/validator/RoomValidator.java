package com.ironia.realmeet.validator;

import static com.ironia.realmeet.validator.ValidatorConstants.*;
import static com.ironia.realmeet.validator.ValidatorUtils.*;

import com.ironia.realmeet.api.model.CreateRoomDTO;
import com.ironia.realmeet.domain.repository.RoomRepository;
import com.ironia.realmeet.exception.InvalidRequestException;
import org.springframework.stereotype.Component;

@Component
public class RoomValidator {

    private final RoomRepository roomRepository;

    public RoomValidator(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    public void validate(CreateRoomDTO createRoomDTO) {
        var validationErrors = new ValidationErrors();

        //Room name
        validateRequired(createRoomDTO.getName(), ROOM_NAME, validationErrors);
        validateMaxLength(createRoomDTO.getName(), ROOM_NAME, ROOM_NAME_MAX_LENGTH, validationErrors);

        //Room seats
        validateRequired(createRoomDTO.getSeats(), ROOM_SEATS, validationErrors);
        validateMinValue(createRoomDTO.getSeats(), ROOM_SEATS, ROOM_SEATS_MIN_VALUE, validationErrors);
        validateMaxValue(createRoomDTO.getSeats(), ROOM_SEATS, ROOM_SEATS_MAX_VALUE, validationErrors);

        throwOnError(validationErrors);

        validateNameDuplicate(createRoomDTO.getName());
    }

    public void validateNameDuplicate(String name) {
        roomRepository
                .findByNameAndActive(name, true)
                .ifPresent(__ -> {
                    throw new InvalidRequestException(new ValidationError(ROOM_NAME, ROOM_NAME + DUPLICATE));
                });
    }
}

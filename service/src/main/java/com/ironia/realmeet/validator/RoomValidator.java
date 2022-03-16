package com.ironia.realmeet.validator;

import static com.ironia.realmeet.validator.ValidatorConstants.*;
import static com.ironia.realmeet.validator.ValidatorUtils.*;

import com.ironia.realmeet.api.model.CreateRoomDTO;
import com.ironia.realmeet.domain.repository.RoomRepository;
import org.springframework.stereotype.Component;

@Component
public class RoomValidator {
    private final RoomRepository roomRepository;

    public RoomValidator(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void validate(CreateRoomDTO createRoomDTO) {
        var validationErrors = new ValidationErrors();

        if (
            validateName(createRoomDTO.getName(), validationErrors) &&
            validateSeats(createRoomDTO.getSeats(), validationErrors)
        ) {
            validateNameDuplicate(createRoomDTO.getName(), validationErrors);
        }

        throwOnError(validationErrors);
    }

    private boolean validateName(String name, ValidationErrors validationErrors) {
        return (
            validateRequired(name, ROOM_NAME, validationErrors) &&
            validateMaxLength(name, ROOM_NAME, ROOM_NAME_MAX_LENGTH, validationErrors)
        );
    }

    private boolean validateSeats(Integer numSeats, ValidationErrors validationErrors) {
        return (
            validateRequired(numSeats, ROOM_SEATS, validationErrors) &&
            validateMinValue(numSeats, ROOM_SEATS, ROOM_SEATS_MIN_VALUE, validationErrors) &&
            validateMaxValue(numSeats, ROOM_SEATS, ROOM_SEATS_MAX_VALUE, validationErrors)
        );
    }

    public void validateNameDuplicate(String name, ValidationErrors validationErrors) {
        roomRepository
            .findByNameAndActive(name, true)
            .ifPresent(__ -> validationErrors.add(new ValidationError(ROOM_NAME, ROOM_NAME + DUPLICATE)));
    }
}

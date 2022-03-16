package com.ironia.realmeet.unit;

import static com.ironia.realmeet.utils.TestDataCreator.newCreateRoomDTO;
import static com.ironia.realmeet.validator.ValidatorConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ironia.realmeet.core.BaseUnitTest;
import com.ironia.realmeet.exception.InvalidRequestException;
import com.ironia.realmeet.validator.RoomValidator;
import com.ironia.realmeet.validator.ValidationError;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RoomValidatorUnitTest extends BaseUnitTest {

    private RoomValidator victim;

    @BeforeEach
    public void setEach() {
        victim = new RoomValidator();
    }

    @Test

    void testValidateWhenRoomIsValid() {
        victim.validate(newCreateRoomDTO());
    }

    @Test
    void testValidateWhenRoomNameIsMissing() {
        var exception = assertThrows(InvalidRequestException.class, () -> victim.validate(newCreateRoomDTO().name(null)));
        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(new ValidationError(ROOM_NAME,ROOM_NAME + MISSING), exception.getValidationErrors().getError(0));
    }

    @Test
    void testValidateWhenRoomNameExceedsLength() {
        var exception = assertThrows(InvalidRequestException.class, () -> victim.validate(newCreateRoomDTO().name(StringUtils.rightPad("Room", ROOM_NAME_MAX_LENGTH + 1, "x"))));
        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(new ValidationError(ROOM_NAME,ROOM_NAME + EXCEEDS_MAX_LENGTH), exception.getValidationErrors().getError(0));
    }

    @Test
    void testValidateWhenRoomSeatsAreMissing() {
        var exception = assertThrows(InvalidRequestException.class, () -> victim.validate(newCreateRoomDTO().seats(null)));
        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(new ValidationError(ROOM_SEATS,ROOM_SEATS + MISSING), exception.getValidationErrors().getError(0));
    }

    @Test
    void testValidateWhenRoomSeatsAreLessThanMinValue() {
        var exception = assertThrows(InvalidRequestException.class, () -> victim.validate(newCreateRoomDTO().seats(ROOM_SEATS_MIN_VALUE - 1)));
        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(new ValidationError(ROOM_SEATS,ROOM_SEATS + BELOW_MIN_VALUE), exception.getValidationErrors().getError(0));
    }

    @Test
    void testValidateWhenRoomSeatsAreGreaterThanMinValue() {
        var exception = assertThrows(InvalidRequestException.class, () -> victim.validate(newCreateRoomDTO().seats(ROOM_SEATS_MAX_VALUE + 1)));
        assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        assertEquals(new ValidationError(ROOM_SEATS,ROOM_SEATS + EXCEEDS_MAX_VALUE), exception.getValidationErrors().getError(0));
    }
}
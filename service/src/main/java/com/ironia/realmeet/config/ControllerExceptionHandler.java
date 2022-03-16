package com.ironia.realmeet.config;

import static com.ironia.realmeet.util.ResponseEntityUtils.notFound;

import com.ironia.realmeet.api.model.ResponseError;
import com.ironia.realmeet.exception.InvalidRequestException;
import com.ironia.realmeet.exception.RoomNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(Exception exception) {
        return notFound();
    }

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public List<ResponseError> handleInvalidRequestException(InvalidRequestException exception) {
        return exception
                .getValidationErrors().stream()
                .map(e -> new ResponseError().field(e.getField()).errorCode(e.getErrorCode()))
                .collect(Collectors.toList());
    }
}

package com.aniket.placementcell.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice   // Works for both @RestController and @Controller
public class GlobalExceptionHandler {

    // 🔹 Handle AlreadyPresentException (duplicate CRN or Email)
    @ExceptionHandler(AlreadyPresentException.class)
    public ResponseEntity<APIError> handleAlreadyPresentException(AlreadyPresentException ex) {
        APIError error = new APIError(
                HttpStatus.CONFLICT.value(), // 409
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    // 🔹 Handle all other unexpected exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIError> handleGenericException(Exception ex) {
        APIError error = new APIError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Something went wrong! " + ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package vladimirov.nikolay.CurrencyGateway.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vladimirov.nikolay.CurrencyGateway.DTOs.ErrorResponse;
import vladimirov.nikolay.CurrencyGateway.exceptions.DuplicateRequestIdException;
import vladimirov.nikolay.CurrencyGateway.exceptions.UnhandledCommandException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateRequestIdException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateRequest(DuplicateRequestIdException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 400);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UnhandledCommandException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateRequest(UnhandledCommandException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 400);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 500);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

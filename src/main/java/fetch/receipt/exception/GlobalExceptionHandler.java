package fetch.receipt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {

    //handles 404 error when a receipt ID is not found.
    @ExceptionHandler(ReceiptNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> handleReceiptNotFoundException(ReceiptNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "No receipt found for that ID.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    //handles 404 error for invalid receipt input.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "The receipt is invalid. Please verify input.");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

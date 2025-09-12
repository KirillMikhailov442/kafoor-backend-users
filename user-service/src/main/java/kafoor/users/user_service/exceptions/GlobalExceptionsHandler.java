package kafoor.users.user_service.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleException(BaseException ex, HttpServletRequest request){
        Map<String, String> responseBody = new HashMap<>(3);

        responseBody.put("timestamp", ex.getDate().toString());
        responseBody.put("message", ex.getMessage());
        responseBody.put("path", request.getServletPath());

        return new ResponseEntity<>(responseBody, ex.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleExceptionValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, Object> responseBody = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        responseBody.put("timestamp", new Date());
        responseBody.put("errors", errors);
        responseBody.put("path", request.getServletPath());

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

}
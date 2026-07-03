package homies.com.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex){

        Map<String,Object> response = new HashMap<>();

        response.put("success",false);
        response.put("message",ex.getMessage());
        response.put("time", LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequest(BadRequestException ex){

        Map<String,Object> response = new HashMap<>();

        response.put("success",false);
        response.put("message",ex.getMessage());
        response.put("time",LocalDateTime.now());

        return ResponseEntity.badRequest().body(response);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex){

        Map<String,Object> response = new HashMap<>();

        response.put("success",false);
        response.put("message","Internal Server Error");
        response.put("error",ex.getMessage());
        response.put("time",LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);

    }

}
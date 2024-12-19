package africa.semicolon.election_management_system.handlers;

import africa.semicolon.election_management_system.dtos.responses.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static java.time.LocalDateTime.now;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .requestTime(now())
                .success(false)
                .error("Bad request")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
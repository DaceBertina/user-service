package yourestack.client.handlers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(ConversionFailedException.class)
    protected ResponseEntity<ErrorModel> handleConflict(ConversionFailedException ex, HttpServletRequest request) {
        ErrorModel errorModel = new ErrorModel(LocalDate.now(), HttpStatus.BAD_REQUEST,
                "Bad request", "Invalid status or date", request.getRequestURI());
        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ErrorModel> handleConflict(ConstraintViolationException ex, HttpServletRequest request) {
        ErrorModel errorModel = new ErrorModel(LocalDate.now(), HttpStatus.BAD_REQUEST,
                "Bad request", ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    protected ResponseEntity<ErrorModel> handleConflict(HttpClientErrorException ex, HttpServletRequest request) {
        ErrorModel errorModel = new ErrorModel(LocalDate.now(), HttpStatus.CONFLICT,
                "Conflict", ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(errorModel, HttpStatus.CONFLICT);
    }

}

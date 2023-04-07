package recipes.controller;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class handles exceptions thrown by the controllers
 * @author Ahmed Hosny
 * @version 1.0
 * @since 2023-04-07
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    /**
     * when trying to save an invalid recipe
     *
     * @return 400 (Bad Request)
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    ResponseEntity<Object> exceptionHandler(ValidationException ignored) {
        return ResponseEntity.badRequest().build();
    }


    /**
     * when ConstraintViolationException is thrown
     *
     * @return 400 (Bad Request)
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<Object> exceptionHandler(ConstraintViolationException ignored) {
        return ResponseEntity.badRequest().build();
    }

    /**
     * when a user tries to register with an existing email
     *
     * @return 400 (Bad Request)
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserExistsException.class)
    ResponseEntity<Object> exceptionHandler(UserExistsException ignored) {
        return ResponseEntity.badRequest().build();
    }
}
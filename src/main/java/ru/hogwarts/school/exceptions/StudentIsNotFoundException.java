package ru.hogwarts.school.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class StudentIsNotFoundException extends RuntimeException{
    public StudentIsNotFoundException() {
    }

    public StudentIsNotFoundException(String message) {
        super(message);
    }

    public StudentIsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentIsNotFoundException(Throwable cause) {
        super(cause);
    }

    public StudentIsNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

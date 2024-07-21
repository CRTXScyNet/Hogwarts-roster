package ru.hogwarts.school.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class FacultyIsNotFoundException extends RuntimeException{
    public FacultyIsNotFoundException() {
    }

    public FacultyIsNotFoundException(String message) {
        super(message);
    }

    public FacultyIsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FacultyIsNotFoundException(Throwable cause) {
        super(cause);
    }

    public FacultyIsNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

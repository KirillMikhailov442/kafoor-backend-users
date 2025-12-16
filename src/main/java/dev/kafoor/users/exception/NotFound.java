package dev.kafoor.users.exception;

import org.springframework.http.HttpStatus;

public class NotFound extends BaseException {
    public NotFound(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}

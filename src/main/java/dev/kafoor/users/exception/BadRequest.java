package dev.kafoor.users.exception;

import org.springframework.http.HttpStatus;

public class BadRequest extends BaseException {
    public BadRequest(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}

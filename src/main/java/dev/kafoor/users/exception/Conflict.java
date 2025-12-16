package dev.kafoor.users.exception;

import org.springframework.http.HttpStatus;

public class Conflict extends BaseException {
    public Conflict(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}

package kafoor.users.user_service.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class BadRequest extends BaseException {
    public BadRequest(String message) {
        super(message, HttpStatus.BAD_REQUEST, new Date());
    }
}

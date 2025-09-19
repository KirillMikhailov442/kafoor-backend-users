package kafoor.users.user_service.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class NotFound extends BaseException {
    public NotFound(String message) {
        super(message, HttpStatus.NOT_FOUND, new Date());
    }
}

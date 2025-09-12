package kafoor.users.user_service.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
public class BaseException extends RuntimeException {
    private final HttpStatus status;
    private final Date date;
    public BaseException(String message, HttpStatus status, Date date) {
        super(message);
        this.status = status;
        this.date = date;
    }
}

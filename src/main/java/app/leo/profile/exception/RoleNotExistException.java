package app.leo.profile.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RoleNotExistException extends HttpException {

    public RoleNotExistException() {
    }

    public RoleNotExistException(String message) {
        super(message);
    }

    public RoleNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleNotExistException(Throwable cause) {
        super(cause);
    }

    public RoleNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

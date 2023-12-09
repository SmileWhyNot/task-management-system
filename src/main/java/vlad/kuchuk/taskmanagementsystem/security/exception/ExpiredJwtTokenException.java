package vlad.kuchuk.taskmanagementsystem.security.exception;

public class ExpiredJwtTokenException extends RuntimeException {
    public ExpiredJwtTokenException(String message) {
        super(message);
    }
}

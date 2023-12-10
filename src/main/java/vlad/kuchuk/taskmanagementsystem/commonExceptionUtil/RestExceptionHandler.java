package vlad.kuchuk.taskmanagementsystem.commonExceptionUtil;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vlad.kuchuk.taskmanagementsystem.security.exception.RefreshTokenException;
import vlad.kuchuk.taskmanagementsystem.tasks.exception.TaskNotFoundException;
import vlad.kuchuk.taskmanagementsystem.tasks.exception.TaskOperationException;
import vlad.kuchuk.taskmanagementsystem.tasks.exceptions.CreateCommentException;
import vlad.kuchuk.taskmanagementsystem.user.exception.NoSuchUserException;
import vlad.kuchuk.taskmanagementsystem.user.exception.UserOperationException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String UNAUTHORIZED = "UNAUTHORIZED";
    private static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
    private static final String BAD_REQUEST = "BAD_REQUEST";

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentialsException(BadCredentialsException ex) {
        ApiError apiError = new ApiError(UNAUTHORIZED, "INVALID_CREDENTIAL_DATA");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body(apiError);
    }

    @ExceptionHandler(RefreshTokenException.class)
    public ResponseEntity<ApiError> handleRefreshTokenException(RefreshTokenException ex) {
        ApiError apiError = new ApiError(UNAUTHORIZED, "INVALID_REFRESH_TOKEN");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body(apiError);
    }

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<ApiError> handleNoSuchUserException(NoSuchUserException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST, "USER_NOT_FOUND: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(apiError);
    }

    @ExceptionHandler(UserOperationException.class)
    public ResponseEntity<ApiError> handleUserOperationException(UserOperationException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST, "USER_OPERATION: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(apiError);
    }

    @ExceptionHandler(TaskOperationException.class)
    public ResponseEntity<ApiError> handleTaskOperationException(TaskOperationException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST, "TASK_OPERATION: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(apiError);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ApiError> handleTaskNotFoundException(TaskNotFoundException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST, "TASK_NOT_FOUND: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(apiError);
    }

    @ExceptionHandler(CreateCommentException.class)
    public ResponseEntity<ApiError> handleCreateCommentException(CreateCommentException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST, "CREATE_COMMENT: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(apiError);
    }

    @Nullable
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        ApiError apiError = new ApiError(BAD_REQUEST, "NOT_PASSED_VALIDATION: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(apiError);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> constraintViolationException(ConstraintViolationException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST, "NOT_PASSED_VALIDATION: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(apiError);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request) {
        String requestUri = ((ServletWebRequest) request).getRequest()
                                                         .getRequestURI();
        ApiError apiError = new ApiError(INTERNAL_SERVER_ERROR,
                "Unhandled exception: " + ex.getMessage() + "cause: " + ex.getCause() + "\nrequestUri: " + requestUri);
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

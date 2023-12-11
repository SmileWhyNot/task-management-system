package vlad.kuchuk.taskmanagementsystem.commonExceptionUtil;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vlad.kuchuk.taskmanagementsystem.comments.exception.CreateCommentException;
import vlad.kuchuk.taskmanagementsystem.security.exception.RefreshTokenException;
import vlad.kuchuk.taskmanagementsystem.tasks.exception.TaskNotFoundException;
import vlad.kuchuk.taskmanagementsystem.tasks.exception.TaskOperationException;
import vlad.kuchuk.taskmanagementsystem.user.exception.NoSuchUserException;
import vlad.kuchuk.taskmanagementsystem.user.exception.UserOperationException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String UNAUTHORIZED = "UNAUTHORIZED";
    private static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
    private static final String BAD_REQUEST = "BAD_REQUEST";

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError handleBadCredentialsException(BadCredentialsException ex) {
        return new ApiError(UNAUTHORIZED, "INVALID_CREDENTIAL_DATA");
    }

    @ExceptionHandler(RefreshTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError handleRefreshTokenException(RefreshTokenException ex) {
        return new ApiError(UNAUTHORIZED, "INVALID_REFRESH_TOKEN");
    }

    @ExceptionHandler(NoSuchUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleNoSuchUserException(NoSuchUserException ex) {
        return new ApiError(BAD_REQUEST, "USER_NOT_FOUND: " + ex.getMessage());
    }

    @ExceptionHandler(UserOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleUserOperationException(UserOperationException ex) {
        return new ApiError(BAD_REQUEST, "USER_OPERATION: " + ex.getMessage());
    }

    @ExceptionHandler(TaskOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleTaskOperationException(TaskOperationException ex) {
        return new ApiError(BAD_REQUEST, "TASK_OPERATION: " + ex.getMessage());
    }

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleTaskNotFoundException(TaskNotFoundException ex) {
        return new ApiError(BAD_REQUEST, "TASK_NOT_FOUND: " + ex.getMessage());
    }

    @ExceptionHandler(CreateCommentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleCreateCommentException(CreateCommentException ex) {
        return new ApiError(BAD_REQUEST, "CREATE_COMMENT: " + ex.getMessage());
    }

    @Nullable
    @Override
    @ApiResponse(
            responseCode = "400",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            }
    )
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        ApiError apiError = new ApiError(BAD_REQUEST, "NOT_PASSED_VALIDATION: " + ex.getMessage());
        return ResponseEntity.badRequest()
                             .body(apiError);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError constraintViolationException(ConstraintViolationException ex) {
        return new ApiError(BAD_REQUEST, "NOT_PASSED_VALIDATION: " + ex.getMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleOtherExceptions(Exception ex, WebRequest request) {
        String requestUri = ((ServletWebRequest) request).getRequest()
                                                         .getRequestURI();
        return new ApiError(INTERNAL_SERVER_ERROR,
                            "Unhandled exception: " + ex.getMessage() + "cause: " + ex.getCause() +
                            "\nrequestUri: " + requestUri);
    }
}
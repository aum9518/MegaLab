package megalab.exception;

import megalab.exception.response.ExceptionResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.List;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleNumberFormatException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(errors.toString())

                .className(e.getClass().getSimpleName())
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNotFound(NotFoundException e) {
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(e.getMessage())
                .className(e.getClass().getSimpleName())
                .build();
    }


    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleNotFound(BadRequestException e) {
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .className(e.getClass().getSimpleName())
                .build();
    }

    @ExceptionHandler(BadCredentialException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handleBadCredential(BadCredentialException e) {
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.FORBIDDEN)

                .message(e.getMessage())
                .className(e.getClass().getSimpleName())
                .build();
    }
    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleBadCredential(AlreadyExistException e) {
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.CONFLICT)
                .message(e.getMessage())
                .className(e.getClass().getSimpleName())
                .build();
    }
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionResponse handleAccessDenied(AccessDeniedException e){
        return ExceptionResponse
                .builder()
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .message(e.getMessage())
                .className(e.getClass().getSimpleName())
                .build();

    }

    @ExceptionHandler(SQLFeatureNotSupportedException.class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public ExceptionResponse handleAccessDenied(SQLFeatureNotSupportedException e) {
        return ExceptionResponse
                .builder()
                .httpStatus(HttpStatus.NOT_IMPLEMENTED)
                .message(e.getMessage())
                .className(e.getClass().getSimpleName())
                .build();
    }
}

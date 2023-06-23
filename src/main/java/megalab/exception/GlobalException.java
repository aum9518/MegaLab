package megalab.exception;

import megalab.exception.response.ExceptionResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        return ExceptionResponse
                .builder()
                .status(HttpStatus.CONFLICT)
                .message(errors.toString())
                .className(e.getClass().getSimpleName())
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNotFound(NotFoundException e) {
        return ExceptionResponse
                .builder()
                .status(HttpStatus.NOT_FOUND)
                .message(e.getMessage())
                .className(e.getClass().getSimpleName())
                .build();
    }
}

package megalab.exception.response;

import lombok.Builder;
import org.springframework.http.HttpStatus;
@Builder
public record ExceptionResponse (
        HttpStatus status,
        String message,
        String className
) {
}

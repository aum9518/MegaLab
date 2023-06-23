package megalab.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleResponse {
    private HttpStatus status;
    private String message;
}

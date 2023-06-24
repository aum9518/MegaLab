package megalab.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
public record CommentRequest(String text) {
    public CommentRequest {
    }
}

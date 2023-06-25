package megalab.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
@Getter
@Setter
@NoArgsConstructor
@Builder
public class CommentResponse {
    private Long id;
    private String text;
    private ZonedDateTime updatedAt;

    public CommentResponse(Long id, String text, ZonedDateTime updatedAt) {
        this.id = id;
        this.text = text;
        this.updatedAt = updatedAt;
    }


}

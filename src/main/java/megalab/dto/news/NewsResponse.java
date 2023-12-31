package megalab.dto.news;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import megalab.dto.comment.CommentPagination;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor

public class NewsResponse {
    private Long id;
    private String name;
    private String image;
    private String description;
    private String text;
    private ZonedDateTime createdAt;
    private CommentPagination comments;
    @Builder
    public NewsResponse(Long id, String name, String image, String description, String text, ZonedDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.text = text;
        this.createdAt = createdAt;
    }
}

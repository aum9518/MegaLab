package megalab.dto.news;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@Builder
public class AllNewsResponse {
    private Long id;
    private String name;
    private String image;
    private String description;
    private String createdAt;

    public AllNewsResponse(Long id, String name, String image, String description, String createdAt) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.createdAt = createdAt;
    }
}

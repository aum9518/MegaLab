package megalab.dto.favorite;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class FavoriteResponse {
    private String userName;
    private String newsName;
    private ZonedDateTime createdAt;

    public FavoriteResponse(String userName, String newsName, ZonedDateTime createdAt) {
        this.userName = userName;
        this.newsName = newsName;
        this.createdAt = createdAt;
    }
}

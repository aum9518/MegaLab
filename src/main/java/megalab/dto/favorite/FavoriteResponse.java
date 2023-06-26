package megalab.dto.favorite;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor

public class FavoriteResponse {
    private String userName;
    private String newsName;
    private String createdAt;

    @Builder
    public FavoriteResponse(String userName, String newsName, ZonedDateTime created) {
        this.userName = userName;
        this.newsName = newsName;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.createdAt = dateTimeFormatter.format(created);
    }
}

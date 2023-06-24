package megalab.dto.favorite;

import lombok.Builder;

@Builder
public record FavoriteRequest(
        Long newsId) {
    public FavoriteRequest {
    }
}

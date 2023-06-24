package megalab.dto.favorite;

import lombok.Builder;

@Builder
public record FavoriteRequest(Long userId,
                              Long newsId) {
    public FavoriteRequest {
    }
}

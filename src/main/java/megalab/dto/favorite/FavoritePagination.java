package megalab.dto.favorite;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class FavoritePagination {
    private List<FavoriteResponse> favoriteResponses;
    private int currentPage;
    private int pageSize;

    public FavoritePagination(List<FavoriteResponse> favoriteResponses, int currentPage, int pageSize) {
        this.favoriteResponses = favoriteResponses;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }
}

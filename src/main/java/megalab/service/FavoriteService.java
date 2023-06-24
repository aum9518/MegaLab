package megalab.service;

import megalab.dto.SimpleResponse;
import megalab.dto.favorite.FavoritePagination;
import megalab.dto.favorite.FavoriteRequest;
import megalab.dto.favorite.FavoriteResponse;

public interface FavoriteService {
    SimpleResponse favoriteUserToNews(Long userId,Long newsId,FavoriteRequest favoriteRequest);
    FavoritePagination getAllFavorite( int currentPage, int pageSize);
    FavoriteResponse getByIdFavorite(Long id);
    SimpleResponse updateFavorite(Long userId, Long NewsId, FavoriteRequest favoriteRequest);
    SimpleResponse deleteFavorite(Long id);
}

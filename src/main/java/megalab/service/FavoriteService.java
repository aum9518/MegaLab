package megalab.service;

import megalab.dto.SimpleResponse;
import megalab.dto.favorite.FavoritePagination;
import megalab.dto.favorite.FavoriteRequest;
import megalab.dto.favorite.FavoriteResponse;

public interface FavoriteService {
    SimpleResponse saveFavorite(FavoriteRequest favoriteRequest);
    FavoritePagination getAllFavorite( int currentPage, int pageSize);
    FavoriteResponse getByIdFavorite(Long id);
    SimpleResponse updateFavorite(Long id, FavoriteRequest favoriteRequest);
    SimpleResponse deleteFavorite(Long id);
}

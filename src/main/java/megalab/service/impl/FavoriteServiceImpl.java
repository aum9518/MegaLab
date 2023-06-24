package megalab.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import megalab.dto.SimpleResponse;
import megalab.dto.favorite.FavoritePagination;
import megalab.dto.favorite.FavoriteRequest;
import megalab.dto.favorite.FavoriteResponse;
import megalab.service.FavoriteService;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
//    private final FavoriteService favoriteService;
    @Override
    public SimpleResponse saveFavorite(FavoriteRequest favoriteRequest) {
        return null;
    }

    @Override
    public FavoritePagination getAllFavorite(int currentPage, int pageSize) {
        return null;
    }

    @Override
    public FavoriteResponse getByIdFavorite(Long id) {
        return null;
    }

    @Override
    public SimpleResponse updateFavorite(Long id, FavoriteRequest favoriteRequest) {
        return null;
    }

    @Override
    public SimpleResponse deleteFavorite(Long id) {
        return null;
    }
}

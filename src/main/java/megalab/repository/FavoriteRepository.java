package megalab.repository;

import megalab.dto.SimpleResponse;
import megalab.dto.favorite.FavoriteRequest;
import megalab.dto.favorite.FavoriteResponse;
import megalab.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<FavoriteResponse> findByIdFavorite(Long id);
    @Query("update Favorite f set f.news = :newNews where f.user.id = :userId and f.news.id = :NewsId")
    SimpleResponse updateFavoriteNews(@Param("userId") Long userId, @Param("NewsId") Long NewsId,FavoriteRequest favoriteRequest);
}
package megalab.repository;

import megalab.dto.favorite.FavoriteResponse;
import megalab.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<FavoriteResponse> findByIdFavorite(Long id);
}
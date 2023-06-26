package megalab.repository;

import megalab.dto.favorite.FavoriteResponse;
import megalab.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    @Query("SELECT NEW megalab.dto.favorite.FavoriteResponse(CONCAT(u.firstName, ' ', u.lastName),n.name,f.createDate) FROM Favorite f JOIN f.user u JOIN f.news n WHERE u.id = ?1")
    Page<FavoriteResponse> findByIdFavorite(Long userId, Pageable pageable);

    @Query("SELECT NEW megalab.dto.favorite.FavoriteResponse(CONCAT(u.firstName, ' ', u.lastName),n.name,f.createDate) FROM Favorite f JOIN f.user u JOIN f.news n WHERE f.id = ?1 AND u.id = ?2")
    Optional<FavoriteResponse> getFavoriteIdAndUserId(Long id,Long userId);

    Optional<Favorite> getFavoriteByIdAndUserId(Long id, Long userId);

    boolean existsByIdAndUserId(Long id,Long userId);

    boolean existsByUserIdAndNewsId(Long userId, Long newsId);


}
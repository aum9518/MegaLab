package megalab.repository;

import megalab.dto.news.NewsResponse;
import megalab.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {
    @Query("SELECT NEW megalab.dto.news.NewsResponse(n.id,n.name,n.image,n.description,n.text,n.createDate) FROM News n WHERE n.id = ?1")
    Optional<NewsResponse> getNewsId(Long id);
}
package megalab.repository;

import megalab.dto.comment.CommentResponse;
import megalab.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT NEW megalab.dto.comment.CommentResponse(c.id,c.text,c.createDate,c.updatedDate) FROM Comment c JOIN c.news n where n.id =: newsId order by c.createDate desc ")
Page<CommentResponse> getAllComment(Long newsId, Pageable pageable);
@Query("SELECT NEW megalab.dto.comment.CommentResponse(c.id,c.text,c.createDate,c.updatedDate) FROM Comment c where c.id=:id")
   Optional <CommentResponse> getByIdComment(Long id);
@Query(" SELECT c FROM Comment c JOIN c.news n JOIN c.user u WHERE c.id = ?1 AND n.id= ?2 AND u.id= ?3")
Optional<Comment> getCommentIdAndNews(Long id, Long newsId,Long userId);
}
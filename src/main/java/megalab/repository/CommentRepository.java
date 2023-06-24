package megalab.repository;

import megalab.dto.comment.CommentResponse;
import megalab.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT NEW megalab.dto.comment.CommentResponse(c.id,c.text,c.createDate,c.updatedDate) FROM Comment c order by c.createDate desc ")
Page<CommentResponse> getAllComment(Pageable pageable);
@Query("SELECT NEW megalab.dto.comment.CommentResponse(c.id,c.text,c.createDate,c.updatedDate) FROM Comment c where c.id=:id")
   Optional <CommentResponse> getByIdComment(Long id);
}
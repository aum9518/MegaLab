package megalab.repository;

import megalab.dto.comment.CommentPagination;
import megalab.dto.comment.CommentResponse;
import megalab.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT NEW megalab.dto.comment.CommentResponse(c.id,c.text,c.updatedDate) FROM Comment c WHERE c.id =?1")
    Optional<CommentResponse> getByIdComment(Long id);

    @Query("SELECT c FROM Comment c JOIN c.news n JOIN c.user u WHERE c.id = :id AND n.id = :newsId AND u.id = :userId")
    Optional<Comment> getCommentIdAndNews( Long id,  Long newsId, Long userId);

    boolean existsByIdAndUserId(Long id, Long userId);

    @Query("SELECT NEW megalab.dto.comment.CommentResponse (c.id,c.text,c.updatedDate) FROM Comment c JOIN c.news n WHERE n.id = ?1")
    Page<CommentResponse> getAllByNewsId(Long newsId, Pageable pageable);

}
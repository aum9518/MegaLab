package megalab.service;

import megalab.dto.SimpleResponse;
import megalab.dto.comment.CommentPagination;
import megalab.dto.comment.CommentRequest;
import megalab.dto.comment.CommentResponse;

public interface CommentService {
    SimpleResponse saveComment(Long userId, Long newsId, CommentRequest commentRequest);
    CommentResponse save2Comment(Long comId,Long userId, Long newsId, CommentRequest commentRequest);
    CommentPagination getAllComment(Long newsId, int currentPage, int pageSize);
    CommentResponse getByIdComment(Long id);
    SimpleResponse updateComment(Long id, Long newsId, CommentRequest commentRequest);
    SimpleResponse deleteComment(Long id);


}

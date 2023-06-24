package megalab.service;

import megalab.dto.SimpleResponse;
import megalab.dto.comment.CommentPagination;
import megalab.dto.comment.CommentRequest;
import megalab.dto.comment.CommentResponse;

public interface CommentService {
    SimpleResponse saveComment(CommentRequest commentRequest);
    CommentPagination getAllComment(Long newsId, int currentPage, int pageSize);
    CommentResponse getByIdComment(Long id);
    SimpleResponse updateComment(Long id, CommentRequest commentRequest);
    SimpleResponse deleteComment(Long id);

}

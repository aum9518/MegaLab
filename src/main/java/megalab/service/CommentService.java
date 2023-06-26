package megalab.service;

import megalab.dto.SimpleResponse;
import megalab.dto.comment.CommentPagination;
import megalab.dto.comment.CommentRequest;
import megalab.dto.comment.CommentResponse;

import java.util.List;

public interface CommentService {
    SimpleResponse saveComment( Long newsId, CommentRequest commentRequest);
    SimpleResponse save2Comment(Long comId, Long newsId, CommentRequest commentRequest);
    CommentPagination getAllComment(Long newsId, int currentPage, int pageSize);
    List<CommentResponse> getByIdComment(Long id);
    SimpleResponse updateComment(Long id, Long newsId, CommentRequest commentRequest);
    SimpleResponse deleteComment(Long id);


}

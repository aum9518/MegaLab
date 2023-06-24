package megalab.service;

import megalab.dto.SimpleResponse;
import megalab.dto.comment.CommentPagination;
import megalab.dto.comment.CommentRequest;
import megalab.dto.comment.CommentResponse;

import java.beans.SimpleBeanInfo;

public interface CommentService {
    SimpleResponse saveComment(CommentRequest commentRequest);
    CommentPagination getAllComment(int currentPage, int pageSize);
    CommentResponse getByIdComment(Long id);
    SimpleResponse updateComment(Long id, CommentRequest commentRequest);
    SimpleResponse deleteComment(Long id);

}

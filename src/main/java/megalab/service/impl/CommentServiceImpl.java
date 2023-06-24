package megalab.service.impl;

import lombok.RequiredArgsConstructor;
import megalab.dto.SimpleResponse;
import megalab.dto.category.CategoryPagination;
import megalab.dto.category.CategoryResponse;
import megalab.dto.comment.CommentPagination;
import megalab.dto.comment.CommentRequest;
import megalab.dto.comment.CommentResponse;
import megalab.entity.Comment;
import megalab.exception.AlreadyExistException;
import megalab.exception.NotFoundException;
import megalab.repository.CommentRepository;
import megalab.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public SimpleResponse saveComment(CommentRequest commentRequest) {
        Comment comment = new Comment();
        comment.setText(commentRequest.text());
        comment.setCreateDate(ZonedDateTime.now());
        comment.setUpdatedDate(ZonedDateTime.now());
        commentRepository.save(comment);
        return SimpleResponse.builder()
                .status(HttpStatus.ACCEPTED)
                .message("Successfully added")
                .build();
    }

    @Override
    public CommentPagination getAllComment(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<CommentResponse> allComment = commentRepository.getAllComment(pageable);
        return CommentPagination.builder()
                .commentPagination(allComment.getContent())
                .currentPage(allComment.getNumber() + 1)
                .pageSize(allComment.getTotalPages())
                .build();
    }

    @Override
    public CommentResponse getByIdComment(Long id) {
        return commentRepository.getByIdComment(id)
                .orElseThrow(() -> new NotFoundException("Comment with id: " + id + "is not fount"));
    }

    @Override
    public SimpleResponse updateComment(Long id, CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment with id: " + id + "is not fount"));
        comment.setText(commentRequest.text());
        comment.setUpdatedDate(ZonedDateTime.now());
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Successfully updated")
                .build();
    }

    @Override
    public SimpleResponse deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment with id: " + id + "is not fount"));
        commentRepository.delete(comment);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Successfully deleted")
                .build();
    }
}

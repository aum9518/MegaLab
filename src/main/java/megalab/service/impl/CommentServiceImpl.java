package megalab.service.impl;

import lombok.RequiredArgsConstructor;
import megalab.dto.SimpleResponse;
import megalab.dto.comment.CommentPagination;
import megalab.dto.comment.CommentRequest;
import megalab.dto.comment.CommentResponse;
import megalab.entity.Comment;
import megalab.entity.News;
import megalab.entity.User;
import megalab.exception.NotFoundException;
import megalab.repository.CommentRepository;
import megalab.repository.NewsRepository;
import megalab.repository.UserRepository;
import megalab.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final NewsRepository newsRepository;

    @Override
    public SimpleResponse saveComment(Long userId, Long newsId, CommentRequest commentRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id: " + userId + "is not fount"));
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new NotFoundException("News with id: " + newsId + "is not fount"));
        Comment comment = new Comment();
        comment.setText(commentRequest.text());
        comment.setCreateDate(ZonedDateTime.now());
        comment.setUpdatedDate(ZonedDateTime.now());
        comment.setUser(user);
        user.setComments(List.of(comment));
        comment.setNews(news);
        news.setComments(List.of(comment));
        commentRepository.save(comment);
        userRepository.save(user);
        newsRepository.save(news);
        return SimpleResponse.builder()
                .status(HttpStatus.ACCEPTED)
                .message("Successfully added")
                .build();
    }

    @Override
    public CommentResponse save2Comment(Long comId, Long userId, Long newsId, CommentRequest commentRequest) {
        List<Comment>comments = new ArrayList<>();
        User user1 = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id: " + userId + "is not fount"));
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new NotFoundException("News with id: " + newsId + "is not fount"));
        Comment comment1 = commentRepository.findById(comId).orElseThrow(() -> new NotFoundException("Comment with id: " + comId + "is not fount"));
        comment1.setComments(comments);
        comment1.setText(commentRequest.text());
        comment1.setCreateDate(ZonedDateTime.now());
        comment1.setUpdatedDate(ZonedDateTime.now());
        comment1.setUser(user1);
        user1.setComments(List.of(comment1));
        comment1.setNews(news);
        news.setComments(List.of(comment1));
        commentRepository.save(comment1);
        userRepository.save(user1);
        newsRepository.save(news);
        return CommentResponse.builder()
                .text(commentRequest.text())
                .build();
    }

    @Override
    public CommentPagination getAllComment(Long newsId, int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<CommentResponse> allComment = commentRepository.getAllComment(newsId, pageable);
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
    public SimpleResponse updateComment(Long id, Long newsId, CommentRequest commentRequest) {
        String nickName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByUserInfoNickName(nickName).orElseThrow(() -> new NotFoundException("Comment with nickName: " + nickName + "is not fount"));
        Comment comment = commentRepository.getCommentIdAndNews(id, newsId, user.getId()).orElseThrow(() -> new NotFoundException("Comment with id: " + id + "is not fount"));
        comment.setText(commentRequest.text() == null || commentRequest.text().isBlank() || comment.getText().equalsIgnoreCase(commentRequest.text()) ? comment.getText() : commentRequest.text());
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

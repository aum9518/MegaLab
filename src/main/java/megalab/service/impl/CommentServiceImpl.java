package megalab.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final NewsRepository newsRepository;

    @Override
    public SimpleResponse saveComment(Long newsId, CommentRequest commentRequest) {
        String nickName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByUserInfoNickName(nickName).orElseThrow(() -> {
            log.error("Comment with nickName: " + nickName + "is not fount");
            return new NotFoundException("Comment with nickName: " + nickName + "is not fount");
        });
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> {
                    log.error("News with id: " + newsId + "is not fount");
                    return new NotFoundException("News with id: " + newsId + "is not fount");
                });
        Comment comment = Comment.builder()
                .text(commentRequest.text())
                .createDate(ZonedDateTime.now())
                .updatedDate(ZonedDateTime.now())
                .user(user)
                .news(news)
                .build();
        user.getComments().add(comment);
        news.getComments().add(comment);
        commentRepository.save(comment);
        return SimpleResponse.builder()
                .status(HttpStatus.ACCEPTED)
                .message("Successfully added")
                .build();
    }

    @Override
    public CommentResponse save2Comment(Long comId, Long newsId, CommentRequest commentRequest) {
        String nickName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user1 = userRepository.getUserByUserInfoNickName(nickName).orElseThrow(() -> {
            log.error("Comment with nickName: " + nickName + "is not fount");
            return new NotFoundException("Comment with nickName: " + nickName + "is not fount");
        });
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> {
                    log.error("News with id: " + newsId + "is not fount");
                    return new NotFoundException("News with id: " + newsId + "is not fount");
                });
        Comment comment1 = commentRepository.findById(comId).orElseThrow(() -> {
            log.error("Comment with id: " + comId + "is not fount");
            return new NotFoundException("Comment with id: " + comId + "is not fount");
        });
        Comment comment = Comment.builder()
                .text(commentRequest.text())
                .createDate(ZonedDateTime.now())
                .updatedDate(ZonedDateTime.now())
                .news(news)
                .user(user1)
                .build();
        comment1.getComments().add(comment);
        commentRepository.save(comment);
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
                .pageSize(allComment.getSize())
                .build();
    }

    @Override
    public CommentResponse getByIdComment(Long id) {
        return commentRepository.getByIdComment(id)
                .orElseThrow(() -> {
                    log.error("Comment with id: " + id + "is not fount");
                    return new NotFoundException("Comment with id: " + id + "is not fount");
                });
    }

    @Override
    public SimpleResponse updateComment(Long id, Long newsId, CommentRequest commentRequest) {
        String nickName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByUserInfoNickName(nickName).orElseThrow(() -> {
            log.error("Comment with nickName: " + nickName + "is not fount");
            return new NotFoundException("Comment with nickName: " + nickName + "is not fount");
        });
        Comment comment = commentRepository.getCommentIdAndNews(id, newsId, user.getId()).orElseThrow(() -> {
            log.error("Comment with id: " + id + "is not fount");
            return new NotFoundException("Comment with id: " + id + "is not fount");
        });
        comment.setText(commentRequest.text() == null || commentRequest.text().isBlank() || comment.getText().equalsIgnoreCase(commentRequest.text()) ? comment.getText() : commentRequest.text());

        comment.setUpdatedDate(ZonedDateTime.now());
        commentRepository.save(comment);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Successfully updated")
                .build();
    }

    @Override
    public SimpleResponse deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> {
            log.error("Comment with id: " + id + "is not fount");
            return new NotFoundException("Comment with id: " + id + "is not fount");
        });
        commentRepository.delete(comment);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Successfully deleted")
                .build();
    }
}

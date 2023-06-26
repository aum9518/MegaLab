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
import megalab.enums.Role;
import megalab.exception.BadRequestException;
import megalab.exception.NotFoundException;
import megalab.repository.CommentRepository;
import megalab.repository.NewsRepository;
import megalab.repository.UserRepository;
import megalab.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final NewsRepository newsRepository;
    private final JdbcTemplate jdbcTemplate;

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
    public SimpleResponse save2Comment(Long comId, Long newsId, CommentRequest commentRequest) {
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
        return SimpleResponse
                .builder()
                .status(HttpStatus.OK)
                .message("Comment saved")
                .build();
    }

    @Override
    public CommentPagination getAllComment(Long newsId, int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        String query = "SELECT c.id, c.text, c.updated_date " +
                "FROM comments c " +
                "LEFT JOIN comments cs ON c.id = cs.comment_id " +
                "WHERE c.comment_id IS NULL OR c.comment_id IS NOT NULL";

        List<CommentResponse> mainComments = jdbcTemplate
                .query(
                        query,
                        new Object[]{},
                        (rs, rowNum) -> new CommentResponse (
                                rs.getLong("id"),
                                rs.getString("text"),
                                rs.getDate("updated_date"))
                        );

        for (CommentResponse c : mainComments) {
            String query1 = "SELECT cs.id,cs.text,cs.updated_date FROM comments c  JOIN comments cs on c.id = cs.comment_id WHERE c.id = ?";

            List<CommentResponse> responseComments = jdbcTemplate
                    .query(
                            query1,
                            new Object[]{c.getId()},
                            (rs, rowNum) -> new CommentResponse(
                                    rs.getLong("id"),
                                    rs.getString("text"),
                                    rs.getDate("updated_date"))
                    );
            c.setComments(responseComments);
            log.info("Get all comment and comment response");
            return CommentPagination.builder()
                    .commentPagination(mainComments)
                    .currentPage(offset)
                    .pageSize(pageSize)
                    .build();
        }
       return null;
    }

    @Override
    public List<CommentResponse> getByIdComment(Long id) {

        String query = "SELECT c.id, c.text, c.updated_date " +
                "FROM comments c " +
                "WHERE c.id = ?";

        List<CommentResponse> mainComments = jdbcTemplate
                .query(
                        query,
                        new Object[]{id},
                        (rs, rowNum) -> new CommentResponse (
                                rs.getLong("id"),
                                rs.getString("text"),
                                rs.getDate("updated_date"))
                );

        for (CommentResponse c : mainComments) {
            String query1 = "SELECT cs.id,cs.text,cs.updated_date FROM comments c  " +
                    "JOIN comments cs on c.id = cs.comment_id WHERE cs.comment_id = ?";

            List<CommentResponse> responseComments = jdbcTemplate
                    .query(
                            query1,
                            new Object[]{id},
                            (rs, rowNum) -> new CommentResponse(
                                    rs.getLong("id"),
                                    rs.getString("text"),
                                    rs.getDate("updated_date"))
                    );
            c.setComments(responseComments);
            return mainComments;
        }
        return null;
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
        String nickName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByUserInfoNickName(nickName).orElseThrow(() -> {
            log.error("Comment with nickName: " + nickName + "is not fount");
            return new NotFoundException("Comment with nickName: " + nickName + "is not fount");
        });
        Comment comment = commentRepository.findById(id).orElseThrow(() -> {
            log.error("Comment with id: " + id + "is not fount");
            return new NotFoundException("Comment with id: " + id + "is not fount");
        });
        if (user.getUserInfo().getRole() == Role.ADMIN){
            log.info("Comment with id: %s successfully deleted".formatted(id));
            commentRepository.delete(comment);
        }else if (commentRepository.existsByIdAndUserId(id,user.getId())){
            log.info("Comment with id: %s successfully deleted".formatted(id));
            commentRepository.delete(comment);
        }else {
            log.error("No access");
            throw new BadRequestException("No access");
        }
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Successfully deleted")
                .build();
    }
}

package megalab.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import megalab.dto.SimpleResponse;
import megalab.dto.news.AllNewsResponse;
import megalab.dto.news.NewsPagination;
import megalab.dto.news.NewsRequest;
import megalab.dto.news.NewsResponse;
import megalab.entity.Category;
import megalab.entity.News;
import megalab.entity.User;
import megalab.exception.NotFoundException;
import megalab.repository.CategoryRepository;
import megalab.repository.NewsRepository;
import megalab.repository.UserRepository;
import megalab.service.CommentService;
import megalab.service.NewsService;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final JdbcTemplate jdbcTemplate;
    private final CommentService commentService;

    @Override
    public SimpleResponse saveNews(NewsRequest newsRequest) {
        String nickName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = getUserByNickname(nickName);
        News news = buildNewsFromRequest(newsRequest, user);
        newsRepository.save(news);
        log.info("News with name: {} successfully saved", news.getName());
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("News with name: %s successfully saved".formatted(news.getName()))
                .build();
    }

    private User getUserByNickname(String nickName) {
        return userRepository.getUserByUserInfoNickName(nickName)
                .orElseThrow(() -> {
                    log.error("User with nickname: {} not found", nickName);
                    return new NotFoundException("User with nickname: %s not found".formatted(nickName));
                });
    }

    private Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    log.error("Category with id: {} not found", categoryId);
                    return new NotFoundException("Category with id: %s not found".formatted(categoryId));
                });
    }

    private News getNewsByIdAndUserId(Long id, Long userId) {
        return newsRepository.getNewsId(id, userId)
                .orElseThrow(() -> {
                    log.error("News with id: {} not found", id);
                    return new NotFoundException("News with id: %s not found".formatted(id));
                });
    }

    private News buildNewsFromRequest(NewsRequest newsRequest, User user) {
        return News.builder()
                .name(newsRequest.name())
                .description(newsRequest.description())
                .image(newsRequest.image())
                .text(newsRequest.text())
                .createDate(ZonedDateTime.now())
                .categories(newsRequest.categoryIds().stream()
                        .map(this::getCategoryById)
                        .collect(Collectors.toList()))
                .user(user)
                .build();
    }

    private void updateNewsFromRequest(News news, NewsRequest newsRequest) {
        if (newsRequest.name() != null && !newsRequest.name().isBlank() && !news.getName().equalsIgnoreCase(newsRequest.name())) {
            news.setName(newsRequest.name());
        }
        if (newsRequest.description() != null && !newsRequest.description().isBlank() && !news.getDescription().equalsIgnoreCase(newsRequest.description())) {
            news.setDescription(newsRequest.description());
        }
        if (newsRequest.image() != null && !newsRequest.image().isBlank() && !news.getImage().equalsIgnoreCase(newsRequest.image())) {
            news.setImage(newsRequest.image());
        }
        if (newsRequest.text() != null && !newsRequest.text().isBlank() && !news.getText().equalsIgnoreCase(newsRequest.text())) {
            news.setText(newsRequest.text());
        }
        news.setCategories(newsRequest.categoryIds().stream()
                .map(this::getCategoryById)
                .collect(Collectors.toList()));
    }

    @Override
    public SimpleResponse updateNews(Long id, NewsRequest newsRequest) {
        String nickName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = getUserByNickname(nickName);
        News news = getNewsByIdAndUserId(id, user.getId());
        updateNewsFromRequest(news, newsRequest);
        newsRepository.save(news);
        log.info("News with id: {} successfully updated", id);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("News with id: %s successfully updated".formatted(id))
                .build();
    }

    @Override
    public NewsPagination getAllNews(int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        String query = "SELECT n.id,n.name,n.image,n.description,n.create_date FROM news n  LIMIT ? OFFSET ?";
        List<AllNewsResponse> list = jdbcTemplate
                .query(
                        query,
                        (rs, rowNum) -> new AllNewsResponse(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getString("image"),
                                rs.getString("description"),
                                rs.getString("create_date")),
                        new Object[]{pageSize, offset},
                        new ArrayList<>()

                );
        log.info("Get all news");
        return NewsPagination
                .builder()
                .newsResponses(list)
                .currentPage(currentPage)
                .pageSize(pageSize)
                .build();
    }

    @Override
    public NewsResponse getByIdNews(Long id, int currentPage, int pageSize) {
        NewsResponse newsResponse = newsRepository.getNewsId(id).orElseThrow(() -> {
            log.error("News with id: %s not found".formatted(id));
            return new NotFoundException("News with id: %s not found".formatted(id));
        });
        newsResponse.setComments(commentService.getAllComment(id, currentPage, pageSize));
        return newsResponse;
    }


    @Override
    public SimpleResponse deleteNews(Long id) {
        News news = newsRepository.findById(id).orElseThrow(() -> {
            log.error("News with id: %s not found".formatted(id));
            return new NotFoundException("News with id: %s not found".formatted(id));
        });
        newsRepository.delete(news);
        log.info("News with id: %s successfully deleted".formatted(id));
        return SimpleResponse
                .builder()
                .status(HttpStatus.OK)
                .message("News with id: %s successfully deleted".formatted(id))
                .build();
    }

    @Override
    public NewsPagination searchNews(String word, int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        String query = "SELECT n.id, n.name, n.image, n.description, n.create_date" +
                " FROM news n " +
                " JOIN news_categories nc ON n.id = nc.news_id " +
                " JOIN categories c ON nc.categories_id = c.id " +
                " WHERE n.name ILIKE CONCAT('%', ?, '%') OR n.description ILIKE CONCAT('%', ?, '%') OR n.text ILIKE CONCAT('%', ?, '%') OR c.name ILIKE CONCAT('%', ?, '%') " +
                " LIMIT ? OFFSET ?";

        List<AllNewsResponse> list = jdbcTemplate
                .query(
                        query,

                        new Object[]{word, word, word, word, pageSize, offset},
                        (rs, rowNum) -> new AllNewsResponse(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getString("image"),
                                rs.getString("description"),
                                rs.getString("create_date"))
                );
        log.info("Search news get all");
        return NewsPagination
                .builder()
                .newsResponses(list)
                .currentPage(currentPage)
                .pageSize(pageSize)
                .build();
    }

    @Override
    public NewsPagination getNewsByUserId(int currentPage, int pageSize) {
        String nickName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByUserInfoNickName(nickName).orElseThrow(() -> {
            log.error("User with nickname: %s not found".formatted(nickName));
            return new NotFoundException("User with nickname: %s not found".formatted(nickName));
        });
        int offset = (currentPage - 1) * pageSize;
        String query = "SELECT n.id, n.name, n.image, n.description, n.create_date FROM news n " +
                "JOIN users u on n.user_id = u.id WHERE u.id = ? LIMIT ? OFFSET ?";
        List<AllNewsResponse> allNewsResponses = jdbcTemplate
                .query(
                        query,
                        new Object[]{user.getId(), pageSize, offset},
                        (rs, rowNum) -> new AllNewsResponse(
                                rs.getLong(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5))
                );
        log.info("My news get all");
        return NewsPagination
                .builder()
                .newsResponses(allNewsResponses)
                .currentPage(currentPage)
                .pageSize(pageSize)
                .build();
    }
}

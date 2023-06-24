package megalab.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
import java.util.List;
import java.util.stream.Collectors;

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
        User user = userRepository.getUserByUserInfoNickName(nickName).orElseThrow(() -> new NotFoundException("User with nickName: %s not found".formatted(nickName)));
        News news = News
                .builder()
                .name(newsRequest.name())
                .description(newsRequest.description())
                .image(newsRequest.image())
                .text(newsRequest.text())
                .createDate(ZonedDateTime.now())
                .build();
        List<Category> categories = newsRequest.categoryIds().stream()
                .map(l -> categoryRepository.findById(l)
                        .orElseThrow(() -> new NotFoundException("Category with id: %s not found".formatted(l))))
                .collect(Collectors.toList());
        news.setCategories(categories);
        news.setUser(user);
        newsRepository.save(news);
        return SimpleResponse
                .builder()
                .status(HttpStatus.OK)
                .message("News with name: %s successfully saved".formatted(news.getName()))
                .build();
    }

    @Override
    public NewsPagination getAllNews(int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        String query = "SELECT n.id,n.name,n.image,n.description,n.create_date FROM news n  LIMIT ? OFFSET ?";
        List<AllNewsResponse> list = jdbcTemplate
                .query(
                        query,
                        new Object[]{pageSize, offset},
                        (rs, rowNum) -> new AllNewsResponse(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getString("image"),
                                rs.getString("description"),
                                rs.getString("create_date"))
                );
        return NewsPagination
                .builder()
                .newsResponses(list)
                .currentPage(currentPage)
                .pageSize(pageSize)
                .build();
    }

    @Override
    public NewsResponse getByIdNews(Long id, int currentPage, int pageSize) {
        NewsResponse newsResponse = newsRepository.getNewsId(id).orElseThrow(() -> new NotFoundException("News with id: %s not found".formatted(id)));
        newsResponse.setComments(commentService.getAllComment(id, currentPage, pageSize));
        return newsResponse;
    }

    @Override
    public SimpleResponse updateNews(Long id, NewsRequest newsRequest) {
        String nickName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByUserInfoNickName(nickName).orElseThrow(() -> new NotFoundException("User with nickname: %s not found".formatted(nickName)));
        News news = newsRepository.getNewsId(id, user.getId()).orElseThrow(() -> new NotFoundException("News with id: %s not found".formatted(id)));
        news.setName(newsRequest.name() == null || newsRequest.name().isBlank() || news.getName().equalsIgnoreCase(newsRequest.name()) ? news.getName() : newsRequest.name());
        news.setDescription(newsRequest.description() == null || newsRequest.description().isBlank() || news.getDescription().equalsIgnoreCase(newsRequest.description()) ? news.getDescription() : newsRequest.description());
        news.setImage(newsRequest.image() == null || newsRequest.image().isBlank() || news.getImage().equalsIgnoreCase(newsRequest.image()) ? news.getImage() : newsRequest.image());
        news.setText(newsRequest.text() == null || newsRequest.text().isBlank() || news.getText().equalsIgnoreCase(newsRequest.text()) ? news.getText() : newsRequest.text());
        List<Category> categories = newsRequest.categoryIds().stream()
                .map(l -> categoryRepository.findById(l)
                        .orElseThrow(() -> new NotFoundException("Category with id: %s not found".formatted(l))))
                .collect(Collectors.toList());
        news.setCategories(categories);
        newsRepository.save(news);
        return SimpleResponse
                .builder()
                .status(HttpStatus.OK)
                .message("News with id: %s successfully updated".formatted(id))
                .build();
    }

    @Override
    public SimpleResponse deleteNews(Long id) {
        News news = newsRepository.findById(id).orElseThrow(() -> new NotFoundException("News with id: %s not found".formatted(id)));
        newsRepository.delete(news);
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
                        new Object[]{word,word,word,word,pageSize, offset},
                        (rs, rowNum) -> new AllNewsResponse(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getString("image"),
                                rs.getString("description"),
                                rs.getString("create_date"))
                );
        return NewsPagination
                .builder()
                .newsResponses(list)
                .currentPage(currentPage)
                .pageSize(pageSize)
                .build();
    }

    @Override
    public NewsPagination getNewsByUserId( int currentPage, int pageSize) {
        String nickName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByUserInfoNickName(nickName).orElseThrow(() -> new NotFoundException("User with nickname: %s not found".formatted(nickName)));
        int offset = (currentPage - 1) * pageSize;
        String query = "SELECT n.id, n.name, n.image, n.description, n.create_date FROM news n " +
                "JOIN users u on n.user_id = u.id WHERE u.id = ? LIMIT ? OFFSET ?";
        List<AllNewsResponse> allNewsResponses = jdbcTemplate
                .query(query,
                        new Object[]{user.getId(),pageSize,offset},
                        (rs, rowNum) -> new AllNewsResponse(
                                rs.getLong(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5)
                        ));
        return NewsPagination
                .builder()
                .newsResponses(allNewsResponses)
                .currentPage(currentPage)
                .pageSize(pageSize)
                .build();
    }
}

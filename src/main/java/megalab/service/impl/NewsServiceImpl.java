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
//    private final CommentService commentService;

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
//        newsResponse.setComments(commentService.getAllComment(currentPage,pageSize));
        return null;
    }

    @Override
    public SimpleResponse updateNews(Long id, NewsRequest newsRequest) {
        return null;
    }

    @Override
    public SimpleResponse deleteNews(Long id) {
        return null;
    }
}

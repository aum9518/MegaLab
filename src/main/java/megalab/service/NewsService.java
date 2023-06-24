package megalab.service;

import megalab.dto.SimpleResponse;
import megalab.dto.news.NewsPagination;
import megalab.dto.news.NewsRequest;
import megalab.dto.news.NewsResponse;

public interface NewsService {
    SimpleResponse saveNews(NewsRequest newsRequest);

    NewsPagination getAllNews(int currentPage, int pageSize);

    NewsResponse getByIdNews(Long id,int currentPage, int pageSize);

    SimpleResponse updateNews(Long id, NewsRequest newsRequest);

    SimpleResponse deleteNews(Long id);
}

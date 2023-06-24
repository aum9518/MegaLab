package megalab.dto.news;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class NewsPagination {
    private List<AllNewsResponse> newsResponses;
    private int currentPage;
    private int pageSize;

    public NewsPagination(List<AllNewsResponse> newsResponses, int currentPage, int pageSize) {
        this.newsResponses = newsResponses;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }
}

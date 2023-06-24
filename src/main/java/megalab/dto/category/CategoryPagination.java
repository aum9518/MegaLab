package megalab.dto.category;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class CategoryPagination {
    List<CategoryResponse> categoryResponses;
    int currentPage;
    int pageSize;

    public CategoryPagination(List<CategoryResponse> categoryResponses, int currentPage, int pageSize) {
        this.categoryResponses = categoryResponses;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }
}

package megalab.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class CommentPagination {
    List<CommentPagination> commentPagination;
    int currentPage;
    int pageSize;

    public CommentPagination(List<CommentPagination> commentPagination, int currentPage, int pageSize) {
        this.commentPagination = commentPagination;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }
}

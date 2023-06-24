package megalab.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class UserPagination {
    private List<UserResponse> userResponses;
    private int currentPage;
    private int pageSize;

    public UserPagination(List<UserResponse> userResponses, int currentPage, int pageSize) {
        this.userResponses = userResponses;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }
}

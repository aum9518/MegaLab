package megalab.dto.userInfo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class UserInfoPagination {
    private List<UserInfoResponse> userInfoResponses;
    private int currentPage;
    private int pageSize;

    public UserInfoPagination(List<UserInfoResponse> userInfoResponses, int currentPage, int pageSize) {
        this.userInfoResponses = userInfoResponses;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }
}

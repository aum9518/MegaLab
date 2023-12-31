package megalab.service;

import megalab.dto.SimpleResponse;
import megalab.dto.user.UserPagination;
import megalab.dto.user.UserRequest;
import megalab.dto.user.UserResponse;


public interface UserService {
    UserPagination getAllUsers(int currentPage, int pageSize);

    SimpleResponse saveUser(UserRequest userRequest);
    SimpleResponse update(Long userId,UserRequest userRequest);
    UserResponse getUserById(Long id);
    SimpleResponse deleteUser(Long id);
    UserPagination searchUser(String word, int currentPage, int pageSize);
    SimpleResponse blockOrUnblock(String block,Long id);
    UserPagination filterByBlock(boolean isBlock, int currentPage, int pageSize);

}

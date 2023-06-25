package megalab.api;

import lombok.RequiredArgsConstructor;
import megalab.dto.SimpleResponse;
import megalab.dto.user.UserPagination;
import megalab.dto.user.UserRequest;
import megalab.dto.user.UserResponse;
import megalab.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserApi {
    private final UserService userService;

    @PreAuthorize("hasAnyAuthority('ADMIN','JOURNALIST','READER')")
    @GetMapping
    public UserPagination getAllUsers(@RequestParam int currentPage, @RequestParam int pageSize){
        return userService.getAllUsers(currentPage, pageSize);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse saveUser(@RequestBody UserRequest userRequest){
        return userService.saveUser(userRequest);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','JOURNALIST')")
    @PutMapping("/{userId}")
    public SimpleResponse updateUser(@PathVariable Long userId,@RequestBody UserRequest userRequest){
        return userService.update(userId, userRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','JOURNALIST','READER')")
    @GetMapping("/{userId}")
    public UserResponse getUserById(@PathVariable Long userId){
        return userService.getUserById(userId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','JOURNALIST','READER')")
    @DeleteMapping("/{userId}")
    public SimpleResponse delete(@PathVariable Long userId){
        return userService.deleteUser(userId);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/search")
    public UserPagination search(@RequestParam String word,@RequestParam int currentPage,@RequestParam int pageSize){
        return userService.searchUser(word, currentPage, pageSize);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/block")
    public SimpleResponse blockOrUnblock(@RequestParam String word,@RequestParam Long id){
        return userService.blockOrUnblock(word, id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(("/filter"))
    public UserPagination filterByBlock(@RequestParam boolean isBlock, @RequestParam int currentPage ,@RequestParam int pageSize){
        return userService.filterByBlock(isBlock, currentPage, pageSize);
    }


}

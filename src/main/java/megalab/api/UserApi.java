package megalab.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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
    @Operation(summary = "Get all user", description = "token")
    public UserPagination getAllUsers(@RequestParam int currentPage, @RequestParam int pageSize){
        return userService.getAllUsers(currentPage, pageSize);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @Operation(summary = "save user", description = "token")
    public SimpleResponse saveUser(@RequestBody @Valid UserRequest userRequest){
        return userService.saveUser(userRequest);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','JOURNALIST')")
    @PutMapping("/{userId}")
    @Operation(summary = "update user", description = "token")
    public SimpleResponse updateUser(@PathVariable Long userId,@RequestBody @Valid UserRequest userRequest){
        return userService.update(userId, userRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','JOURNALIST','READER')")
    @GetMapping("/{userId}")
    @Operation(summary = "Get user by id", description = "token")
    public UserResponse getUserById(@PathVariable Long userId){
        return userService.getUserById(userId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','JOURNALIST','READER')")
    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete user", description = "token")
    public SimpleResponse delete(@PathVariable Long userId){
        return userService.deleteUser(userId);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/search")
    @Operation(summary = "Search users", description = "token")
    public UserPagination search(@RequestParam String word,@RequestParam int currentPage,@RequestParam int pageSize){
        return userService.searchUser(word, currentPage, pageSize);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/block")
    @Operation(summary = "Block or unBlock user", description = "token")
    public SimpleResponse blockOrUnblock(@RequestParam String word,@RequestParam Long id){
        return userService.blockOrUnblock(word, id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(("/filter"))
    @Operation(summary = "Filter by block", description = "token")
    public UserPagination filterByBlock(@RequestParam boolean isBlock, @RequestParam int currentPage ,@RequestParam int pageSize){
        return userService.filterByBlock(isBlock, currentPage, pageSize);
    }


}

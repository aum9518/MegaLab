package megalab.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import megalab.dto.SimpleResponse;
import megalab.dto.comment.CommentPagination;
import megalab.dto.comment.CommentRequest;
import megalab.dto.comment.CommentResponse;
import megalab.service.CommentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentApi {
    private final CommentService commentService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN,USER,JOURNALIST')")
    @Operation(summary = "save comment", description = "token")
    public SimpleResponse saveComment(@RequestBody CommentRequest commentRequest) {
        return commentService.saveComment(commentRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN,USER,JOURNALIST')")
    @GetMapping("/{newsId}")
    @Operation(summary = "get all comment", description = "token")
    public CommentPagination getAllComment(@PathVariable Long newsId,@RequestParam int currentPage, @RequestParam int pageSize) {
        return commentService.getAllComment(newsId,currentPage, pageSize);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN,USER,JOURNALIST')")
    @Operation(summary = "get by id", description = "token")
    public CommentResponse getByIdComment(@PathVariable Long id){
        return commentService.getByIdComment(id);
    }

    public SimpleResponse updateComment(Long id, CommentRequest commentRequest){
        return commentService.updateComment(id,commentRequest);
    }
}

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
    public CommentPagination getAllComment(@PathVariable Long newsId, @RequestParam int currentPage, @RequestParam int pageSize) {
        return commentService.getAllComment(newsId, currentPage, pageSize);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN,USER,JOURNALIST')")
    @Operation(summary = "get by id", description = "token")
    public CommentResponse getByIdComment(@PathVariable Long id) {
        return commentService.getByIdComment(id);
    }

    @PutMapping("/update/{id}/{newsId}")
    @PreAuthorize("hasAnyAuthority('ADMIN,USER,JOURNALIST')")
    @Operation(summary = "update comment", description = "token")
    public SimpleResponse updateComment(@PathVariable Long id, @PathVariable Long newsId, CommentRequest commentRequest) {
        return commentService.updateComment(id, newsId, commentRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN,USER,JOURNALIST')")
    @Operation(summary = "delete comment", description = "token")
    public SimpleResponse deleteComment(@PathVariable Long id) {
        return commentService.deleteComment(id);
    }
}

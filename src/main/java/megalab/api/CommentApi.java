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

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentApi {
    private final CommentService commentService;
    @PostMapping("/save/{newsId}")
    @PreAuthorize("permitAll()")
    @Operation(summary = "save comment", description = "token")
    public SimpleResponse saveComment( @PathVariable Long newsId, @RequestBody CommentRequest commentRequest) {
        return commentService.saveComment( newsId, commentRequest);
    }
    @PostMapping("/save/{comId}")
    @PreAuthorize("permitAll()")
    @Operation(summary = "save comment", description = "token")
    public SimpleResponse save2Comment(@PathVariable Long comId, @RequestBody CommentRequest commentRequest) {
        return commentService.save2Comment(comId, commentRequest);
    }
    @PreAuthorize("permitAll()")
    @GetMapping("/getAll/{newsId}")
    @Operation(summary = "get all comment", description = "token")
    public CommentPagination getAllComment(@PathVariable Long newsId, @RequestParam int currentPage, @RequestParam int pageSize) {
        return commentService.getAllComment(newsId, currentPage, pageSize);
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    @Operation(summary = "get by id", description = "token")
    public List<CommentResponse> getByIdComment(@PathVariable Long id) {
        return commentService.getByIdComment(id);
    }

    @PutMapping("/update/{id}/{newsId}")
    @PreAuthorize("permitAll()")
    @Operation(summary = "update comment", description = "token")
    public SimpleResponse updateComment(@PathVariable Long id, @PathVariable Long newsId, CommentRequest commentRequest) {
        return commentService.updateComment(id, newsId, commentRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("permitAll()")
    @Operation(summary = "delete comment", description = "token")
    public SimpleResponse deleteComment(@PathVariable Long id) {
        return commentService.deleteComment(id);
    }
}

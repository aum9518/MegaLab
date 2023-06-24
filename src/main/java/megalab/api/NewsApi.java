package megalab.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import megalab.dto.SimpleResponse;
import megalab.dto.news.NewsPagination;
import megalab.dto.news.NewsRequest;
import megalab.dto.news.NewsResponse;
import megalab.service.NewsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsApi {
    private final NewsService newsService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','JOURNALIST')")
    @Operation(summary = "Save News", description = "Post Method")
    public SimpleResponse saveNews (@RequestBody NewsRequest newsRequest){
        return newsService.saveNews(newsRequest);
    }
    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    @Operation(summary = "Get by id",description = "ID")
    public NewsResponse getNewsById(@PathVariable Long id, @RequestParam(defaultValue = "1") int currentPage,@RequestParam(defaultValue = "6")int size){
        return newsService.getByIdNews(id, currentPage, size);
    }
    @GetMapping
    @Operation(summary = "Get all ",description = "pagination")
    @PreAuthorize("permitAll()")
    public NewsPagination getAllNews (@RequestParam(defaultValue = "1") int currentPage,@RequestParam(defaultValue = "6")int size){
        return newsService.getAllNews(currentPage, size);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('JOURNALIST')")
    @Operation(summary = "Update")
    public SimpleResponse updateNews (@PathVariable Long id,@RequestBody NewsRequest newsRequest){
        return newsService.updateNews(id, newsRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "Delete")
    public SimpleResponse deleteNews(@PathVariable Long id){
        return newsService.deleteNews(id);
    }

    @GetMapping("/search")
    @PreAuthorize("permitAll()")
    @Operation(summary = "Search")
    public NewsPagination searchNews (@RequestParam String word,@RequestParam(defaultValue = "1") int currentPage,@RequestParam(defaultValue = "6")int size ){
        return newsService.searchNews(word, currentPage, size);
    }

    @GetMapping("/my/news")
    @PreAuthorize("permitAll()")
    @Operation(summary = "My news")
    public NewsPagination myNews(@RequestParam(defaultValue = "1") int currentPage,@RequestParam(defaultValue = "6")int size ){
        return newsService.getNewsByUserId(currentPage, size);
    }
}

package megalab.api;

import lombok.RequiredArgsConstructor;
import megalab.dto.SimpleResponse;
import megalab.dto.favorite.FavoritePagination;
import megalab.dto.favorite.FavoriteRequest;
import megalab.dto.favorite.FavoriteResponse;
import megalab.service.FavoriteService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorite")
@RequiredArgsConstructor
public class FavoriteApi {
    private final FavoriteService favoriteService ;
    @PostMapping("/{userId}/{newsId}")
    public SimpleResponse favoriteUserToNews(@PathVariable Long userId,@PathVariable Long newsId,@RequestBody FavoriteRequest favoriteRequest){
        return favoriteService.favoriteUserToNews(userId,newsId,favoriteRequest);
    }
    @GetMapping("/getAll")
    public FavoritePagination getAllFavorite(@RequestParam int page,
                                             @RequestParam int size){
        return favoriteService.getAllFavorite(page,size);
    }
    @GetMapping("/getById/{id}")
    public FavoriteResponse getById(@PathVariable Long id){
        return favoriteService.getByIdFavorite(id);
    }
    @PutMapping("/update/{userId}/{newsId}")
    public SimpleResponse update(@PathVariable Long userId, @PathVariable Long newsId,@RequestBody FavoriteRequest favoriteRequest){
        return favoriteService.updateFavorite(userId,newsId,favoriteRequest);
    }
    @DeleteMapping("/deleted/{id}")
    public SimpleResponse deleted(@PathVariable Long id){
        return favoriteService.deleteFavorite(id);
    }
}

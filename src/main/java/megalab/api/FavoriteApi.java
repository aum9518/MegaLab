package megalab.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import megalab.dto.SimpleResponse;
import megalab.dto.favorite.FavoritePagination;
import megalab.dto.favorite.FavoriteRequest;
import megalab.dto.favorite.FavoriteResponse;
import megalab.service.FavoriteService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorite")
@RequiredArgsConstructor
public class FavoriteApi {
    private final FavoriteService favoriteService;

    @PostMapping
    @PreAuthorize("permitAll()")
    @Operation(summary = "Save favorite")
    public SimpleResponse saveFavorite(@RequestBody FavoriteRequest favoriteRequest) {
        return favoriteService.saveFavorite(favoriteRequest );
    }

    @GetMapping("/getAll")
    @PreAuthorize("permitAll()")
    @Operation(summary = "Get all")
    public FavoritePagination getAllFavorite(@RequestParam int page,
                                             @RequestParam int size) {
        return favoriteService.getAllFavorite(page, size);
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("permitAll()")
    @Operation(summary = "Get by id")
    public FavoriteResponse getById(@PathVariable Long id) {
        return favoriteService.getByIdFavorite(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("permitAll()")
    @Operation(summary = "Update")
    public SimpleResponse update(@PathVariable Long id, @RequestBody FavoriteRequest favoriteRequest) {
        return favoriteService.updateFavorite(id, favoriteRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("permitAll()")
    @Operation(summary = "Delete")
    public SimpleResponse deleted(@PathVariable Long id) {
        return favoriteService.deleteFavorite(id);
    }
}

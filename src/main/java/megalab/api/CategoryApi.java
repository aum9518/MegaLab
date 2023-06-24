package megalab.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import megalab.dto.SimpleResponse;
import megalab.dto.category.CategoryPagination;
import megalab.dto.category.CategoryRequest;
import megalab.dto.category.CategoryResponse;
import megalab.service.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryApi {
    private final CategoryService categoryServices;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "save Category", description = "save")
    public SimpleResponse save(@RequestBody CategoryRequest request) {
        return categoryServices.saveCategory(request);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER','JOURNALIST')")
    @GetMapping
    @Operation(summary = "get all Category", description = "get all ")
    public CategoryPagination getAllCategories(@RequestParam int currentPage, @RequestParam int pageSize) {
        return categoryServices.getAllCategory(currentPage, pageSize);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("delete/{id}")
    @Operation(summary = "delete category", description = "delete")
    public SimpleResponse delete(@PathVariable Long id) {
        return categoryServices.deleteCategory(id);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','JOURNALIST')")
    @Operation(summary = "get by id Category", description = "get by id")
    public CategoryResponse getById(@PathVariable Long id) {
        return categoryServices.getByIdCategory(id);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "update Category", description = "update")
    public SimpleResponse update(@PathVariable Long id, @RequestBody CategoryRequest request) {
        return categoryServices.updateCategory(id, request);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER','JOURNALIST')")
    @GetMapping("/search")
    @Operation(summary = "search Category", description = "update")
    public CategoryPagination search(@RequestParam String word, @RequestParam int currentPage, @RequestParam int pageSize) {
        return categoryServices.searchByName(word, currentPage, pageSize);
    }
}


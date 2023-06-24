package megalab.repository;

import megalab.dto.category.CategoryPagination;
import megalab.dto.category.CategoryResponse;
import megalab.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
    @Query("SELECT NEW megalab.dto.category.CategoryResponse(c.id,c.name) from Category c order by c.id,c.name asc ")
    Page<CategoryResponse> getAllCategory(Pageable pageable);

   Optional <CategoryResponse> getByIdCategory(Long id);
}
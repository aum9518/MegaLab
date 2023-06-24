package megalab.service;

import megalab.dto.SimpleResponse;
import megalab.dto.category.CategoryPagination;
import megalab.dto.category.CategoryRequest;
import megalab.dto.category.CategoryResponse;

public interface CategoryService {
    SimpleResponse saveCategory(CategoryRequest categoryRequest);
    CategoryPagination getAllCategory( int currentPage,int pageSize);
    CategoryResponse getByIdCategory(Long id);
    SimpleResponse updateCategory(Long id, CategoryRequest categoryRequest);
    SimpleResponse deleteCategory(Long id);
   CategoryPagination searchByName(String word, int currentPage,int pageSize);
}

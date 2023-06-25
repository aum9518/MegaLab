package megalab.service.impl;

import lombok.RequiredArgsConstructor;
import megalab.dto.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import megalab.dto.category.CategoryPagination;
import megalab.dto.category.CategoryRequest;
import megalab.dto.category.CategoryResponse;
import megalab.entity.Category;
import megalab.exception.AlreadyExistException;
import megalab.exception.NotFoundException;
import megalab.repository.CategoryRepository;
import megalab.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public SimpleResponse saveCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        if (categoryRepository.existsByName(categoryRequest.name())) {
            log.error("there is such a name: %s annot be added".formatted(categoryRequest.name()));
            throw new AlreadyExistException("there is such a category!!!");
        }

        category.setName(categoryRequest.name());
        categoryRepository.save(category);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Успешно")
                .build();
    }

    @Override
    public CategoryPagination getAllCategory(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<CategoryResponse> allCategories = categoryRepository.getAllCategory(pageable);
        return CategoryPagination.builder()
                .categoryResponses(allCategories.getContent())
                .currentPage(allCategories.getNumber() + 1)
                .pageSize(allCategories.getTotalPages())
                .build();
    }

    @Override
    public CategoryResponse getByIdCategory(Long id) {
        return categoryRepository.getByIdCategory(id)
                .orElseThrow(() -> {
                    log.error("Category with id: " + id + "is not found!");
                    return new NotFoundException("Category with id: " + id + "is not found!");
                });
    }

    @Override
    public SimpleResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> {
            log.error("Category with id: " + id + "is not found");
            return new NotFoundException(String.format("Category with id: " + id + "is not found"));
        });
        category.setName(categoryRequest.name());
        categoryRepository.save(category);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Успешно")
                .build();
    }

    @Override
    public SimpleResponse deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> {
            log.error("Category with id: " + id + "is not found");
            return new NotFoundException(String.format("Category with id: " + id + "is not found"));
        });
        categoryRepository.delete(category);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Успешно")
                .build();
    }

    @Override
    public CategoryPagination searchByName(String word, int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<CategoryResponse> allCategories = categoryRepository.searchCategoryByName(word, pageable);
        return CategoryPagination.builder()
                .categoryResponses(allCategories.getContent())
                .currentPage(allCategories.getNumber() + 1)
                .pageSize(allCategories.getSize())
                .build();
    }
}

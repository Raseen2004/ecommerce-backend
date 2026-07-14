package dev.raseen2004.e_commerce_learning.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.raseen2004.e_commerce_learning.dto.request.CategoryRequest;
import dev.raseen2004.e_commerce_learning.dto.response.CategoryResponse;
import dev.raseen2004.e_commerce_learning.entity.Category;
import dev.raseen2004.e_commerce_learning.exception.ResourceNotFoundException;
import dev.raseen2004.e_commerce_learning.mapper.CategoryMapper;
import dev.raseen2004.e_commerce_learning.repository.CategoryRepository;
import dev.raseen2004.e_commerce_learning.service.CategoryService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = CategoryMapper.toEntity(request);

        Category savedCategory = categoryRepository.save(category);

        return CategoryMapper.toResponse(savedCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long id) {

        Category category = findCategory(id);

        return CategoryMapper.toResponse(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toResponse)
                .toList();
    }

    @Override
    public CategoryResponse updateCategory(
            Long id,
            CategoryRequest request
    ) {
        Category category = findCategory(id);

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        Category savedCategory = categoryRepository.save(category);

        return CategoryMapper.toResponse(savedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = findCategory(id);

        categoryRepository.delete(category);
    }

    private Category findCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Category not found with id: " + id
                ));
    }
}

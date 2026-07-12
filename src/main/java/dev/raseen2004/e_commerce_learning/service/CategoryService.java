package dev.raseen2004.e_commerce_learning.service;

import java.util.List;

import dev.raseen2004.e_commerce_learning.dto.request.CategoryRequest;
import dev.raseen2004.e_commerce_learning.dto.response.CategoryResponse;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse getCategoryById(Long id);

    List<CategoryResponse> getAllCategories();

    CategoryResponse updateCategory(Long id, CategoryRequest request);

    void deleteCategory(Long id);
}

package dev.raseen2004.e_commerce_learning.mapper;

import dev.raseen2004.e_commerce_learning.dto.request.CategoryRequest;
import dev.raseen2004.e_commerce_learning.dto.response.CategoryResponse;
import dev.raseen2004.e_commerce_learning.entity.Category;

public class CategoryMapper {

    public static Category toEntity(CategoryRequest request) {
        return Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public static CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}
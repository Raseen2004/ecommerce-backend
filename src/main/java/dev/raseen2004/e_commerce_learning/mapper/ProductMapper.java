package dev.raseen2004.e_commerce_learning.mapper;

import dev.raseen2004.e_commerce_learning.dto.request.ProductRequest;
import dev.raseen2004.e_commerce_learning.dto.response.ProductResponse;
import dev.raseen2004.e_commerce_learning.entity.Product;

public class ProductMapper {

    public static Product toEntity(ProductRequest request) {

        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .imageUrl(request.getImageUrl())
                .build();
    }

    public static ProductResponse toResponse(Product product) {

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .imageUrl(product.getImageUrl())
                .categoryName(product.getCategory().getName())
                .build();
    }
}
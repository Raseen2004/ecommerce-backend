package dev.raseen2004.e_commerce_learning.service;

import java.util.List;

import dev.raseen2004.e_commerce_learning.dto.request.ProductRequest;
import dev.raseen2004.e_commerce_learning.dto.response.ProductResponse;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);

    ProductResponse getProductById(Long id);

    List<ProductResponse> getAllProducts();

    List<ProductResponse> getProductsByCategory(Long categoryId);

    ProductResponse updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);
}

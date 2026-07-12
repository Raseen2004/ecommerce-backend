package dev.raseen2004.e_commerce_learning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.raseen2004.e_commerce_learning.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    
}

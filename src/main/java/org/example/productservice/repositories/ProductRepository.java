package org.example.productservice.repositories;

import org.example.productservice.models.Category;
import org.example.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);
    List<Product> findByTitleContaining(String name);

    List<Product> findByTitleAndDescription(String title, String description);

    List<Product> findByCategory(Category category);

    List<Product> findByPriceBetween(Double startRange, Double endRange);

    Product save(Product product);
}

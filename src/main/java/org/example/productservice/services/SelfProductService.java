package org.example.productservice.services;

import org.example.productservice.exceptions.ProductNotFoundException;
import org.example.productservice.models.Category;
import org.example.productservice.models.Product;
import org.example.productservice.repositories.CategoryRepository;
import org.example.productservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("selfProductService")
public class SelfProductService implements ProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.productRepository=productRepository;
        this.categoryRepository=categoryRepository;
    }

    @Override
    public Product getSingleProduct(Long id) throws ProductNotFoundException {

        Optional<Product> productOptional=productRepository.findById(id);

        if(productOptional.isEmpty()){
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }
        Product product = productOptional.get();

        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public Product addNewProduct(Product product) {

        Optional<Category> categoryOptional = categoryRepository.findByName(product.getCategory().getName());

        if(categoryOptional.isEmpty())
        {
            product.setCategory(categoryRepository.save(product.getCategory()));
        }
        else {
            product.setCategory(categoryOptional.get());
        }

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) {

        Optional<Product> productOptional = productRepository.findById(id);

        if(productOptional.isEmpty()) {
            throw new RuntimeException();
        }

        Product savedProduct = productOptional.get();

        if(product.getTitle()!=null)
        {
            savedProduct.setTitle(product.getTitle());
        }
        if(product.getDescription()!=null)
        {
            savedProduct.setDescription(product.getDescription());
        }
        if(product.getPrice()!=null)
        {
            savedProduct.setPrice(product.getPrice());
        }

        return productRepository.save(savedProduct);
    }
}

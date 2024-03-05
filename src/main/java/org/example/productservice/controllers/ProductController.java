package org.example.productservice.controllers;

import org.example.productservice.exceptions.ProductNotFoundException;
import org.example.productservice.models.Product;
import org.example.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(@Qualifier("selfProductService") ProductService productService)
    {
        this.productService=productService;
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable ("id") Long id) throws ProductNotFoundException
    {
        return productService.getSingleProduct(id);
    }

    @GetMapping()
    public List<Product> getAllProducts()
    {

        return productService.getAllProducts();
    }

    @PostMapping()
    public Product addProduct(@RequestBody Product product){

        return productService.addNewProduct(product);
    }
    @PatchMapping("/{id}")
    public Product updateProduct(@PathVariable("id") Long id, @RequestBody Product product) throws ProductNotFoundException
    {
        return productService.updateProduct(id, product);
    }
}

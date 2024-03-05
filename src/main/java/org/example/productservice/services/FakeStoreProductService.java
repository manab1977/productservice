package org.example.productservice.services;

import org.example.productservice.dtos.FakeStoreProductDto;
import org.example.productservice.models.Category;
import org.example.productservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service("fakeProductService")
public class FakeStoreProductService implements ProductService{
    private RestTemplate restTemplate;

    @Autowired
    public FakeStoreProductService(RestTemplate restTemplate)
    {
        this.restTemplate=restTemplate;
    }
    @Override
    public Product getSingleProduct(Long id) {
        FakeStoreProductDto fakeStoreProductDto= restTemplate.getForObject("https://fakestoreapi.com/products/" + id,
                FakeStoreProductDto.class);
        return ConvertFakeStoreProductToProduct(fakeStoreProductDto);
    }

    @Override
    public List<Product> getAllProducts() {
        String fakeURL="https://fakestoreapi.com/products";

        ResponseEntity<FakeStoreProductDto[]> response
                = restTemplate.getForEntity(fakeURL, FakeStoreProductDto[].class);

        List<Product> products = new ArrayList<Product>();
        FakeStoreProductDto[] fakeproducts = response.getBody();

        for (FakeStoreProductDto fakeproduct : fakeproducts) {
            products.add(ConvertFakeStoreProductToProduct(fakeproduct));
        }
        return products;
    }

    @Override
    public Product addNewProduct(Product product) {
        String fakeURL="https://fakestoreapi.com/products";
        FakeStoreProductDto fakeproduct = new FakeStoreProductDto();
        fakeproduct.setTitle(product.getTitle());
        fakeproduct.setDescription(product.getDescription());
        fakeproduct.setPrice(product.getPrice());

        FakeStoreProductDto fakeStoreProductDto = restTemplate.postForObject(fakeURL, fakeproduct,
                FakeStoreProductDto.class);

        return ConvertFakeStoreProductToProduct(fakeStoreProductDto);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        return null;
    }

    private Product ConvertFakeStoreProductToProduct(FakeStoreProductDto fakeStoreProductDto)
    {
        Product product = new Product();

        product.setId(fakeStoreProductDto.getId());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setImageUrl(fakeStoreProductDto.getImage());
        product.setCategory(new Category());
        product.getCategory().setName(fakeStoreProductDto.getCategory());

        return product;
    }
}

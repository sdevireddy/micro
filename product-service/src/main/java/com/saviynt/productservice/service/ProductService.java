package com.saviynt.productservice.service;

import com.saviynt.productservice.dto.ProductRequest;
import com.saviynt.productservice.dto.ProductResponse;
import com.saviynt.productservice.model.Product;
import com.saviynt.productservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    public Product createProduct(ProductRequest productRequest) {
        Product product = Product.builder().name(productRequest.getName())
                .description(productRequest.getDescription()).price(productRequest.getPrice()).build();
        productRepository.save(product);
        return product;
    }

    public List<ProductResponse> getAllProducts() {
        return  productRepository.findAll().stream().map(product -> mapToProductResponse(product)).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        ProductResponse productResponse = ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice()).build();
        return productResponse;
    }
}

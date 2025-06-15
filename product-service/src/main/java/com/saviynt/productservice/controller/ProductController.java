package com.saviynt.productservice.controller;

import com.saviynt.productservice.dto.ProductRequest;
import com.saviynt.productservice.dto.ProductResponse;
import com.saviynt.productservice.model.Product;
import com.saviynt.productservice.repository.ProductRepository;
import com.saviynt.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/product")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody ProductRequest productRequest) {
        ProductRequest product = ProductRequest.builder()
                                            .name(productRequest.getName())
                                            .description(productRequest.getDescription())
                                            .price(productRequest.getPrice()).build();
        Product saveProduct = productService.createProduct(product);
        return saveProduct;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProduct() {
            return productService.getAllProducts();
    }
}

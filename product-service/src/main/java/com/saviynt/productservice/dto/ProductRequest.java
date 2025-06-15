package com.saviynt.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Data
@Builder
public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
}

package com.saviynt.inventoryservice.service;

import com.saviynt.inventoryservice.model.Inventory;
import com.saviynt.inventoryservice.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional
    public List<InventoryResponse> isInStrock(List<String> skuCode) {
        List<Inventory>  skuCodes = inventoryRepository.findBySkuCodeIn(skuCode);
        return skuCodes.stream().map(code -> {
            return  InventoryResponse.builder()
                    .skuCode(code.getSkuCode()).isInStock(code.getQuantity()>0)
                    .build();
        }).toList();
    }
}

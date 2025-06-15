package com.saviynt.inventoryservice.controller;

import com.saviynt.inventoryservice.service.InventoryResponse;
import com.saviynt.inventoryservice.service.InventoryService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@Slf4j
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
        List<InventoryResponse> response =  inventoryService.isInStrock(skuCode);
        log.debug("Response {}", response.toString());
        return response;

    }
}

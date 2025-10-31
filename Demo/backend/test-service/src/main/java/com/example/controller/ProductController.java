package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class ProductController {

    // Mock data for products
    private final List<Map<String, Object>> products = Arrays.asList(
        Map.of("id", 1, "name", "Product1", "price", 10.0),
        Map.of("id", 2, "name", "Product2", "price", 20.0)
    );

    @GetMapping("/admin/products")
    public ResponseEntity<List<Map<String, Object>>> getAdminProducts() {
        return ResponseEntity.ok(products);
    }

    @GetMapping("/buyer/products")
    public ResponseEntity<List<Map<String, Object>>> getBuyerProducts() {
        return ResponseEntity.ok(products);
    }

    @PostMapping("/seller/products")
    public ResponseEntity<Map<String, Object>> createSellerProduct(@RequestBody Map<String, Object> product) {
        return ResponseEntity.ok(product); // Mock: return input product
    }

    @GetMapping("/common/products/{id}")
    public ResponseEntity<Map<String, Object>> getCommonProduct() {
        return ResponseEntity.ok(products.get(0)); // Mock: return first product
    }
}
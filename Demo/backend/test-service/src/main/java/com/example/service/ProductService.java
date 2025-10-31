package com.example.service;

import com.example.entities.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final List<Product> products = new ArrayList<>();
    private long nextId = 1;

    public ProductService() {
        // Mock data
        products.add(new Product(nextId++, "Product1", 10.0));
        products.add(new Product(nextId++, "Product2", 20.0));
    }

    public List<Product> getAll() {
        return new ArrayList<>(products);
    }

    public Optional<Product> getById(Long id) {
        return products.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public Product create(Product product) {
        product.setId(nextId++);
        products.add(product);
        return product;
    }

    public Optional<Product> update(Long id, Product updatedProduct) {
        return getById(id).map(p -> {
            p.setName(updatedProduct.getName());
            p.setPrice(updatedProduct.getPrice());
            return p;
        });
    }

    public boolean delete(Long id) {
        return products.removeIf(p -> p.getId().equals(id));
    }
}
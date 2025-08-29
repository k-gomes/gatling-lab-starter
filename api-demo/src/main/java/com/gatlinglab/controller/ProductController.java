package com.gatlinglab.controller;

import com.gatlinglab.domain.Product;
import com.gatlinglab.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository repository;

    // GET rapide : 20 produits
    @GetMapping
    public List<Product> allProducts() {
        return repository.findAll(PageRequest.of(0, 20)).getContent();
    }

    // GET par ID
    @GetMapping("/{id}")
    public Product oneProduct(@PathVariable("id") Long id) {
        return repository.findById(id).orElseThrow();
    }

    // GET par catégorie
    @GetMapping("/search")
    public List<Product> searchByCategory(@RequestParam("category") String category) {
        return repository.findByCategory(category);
    }

    // GET par préfixe de name
    @GetMapping("/search-by-name")
    public List<Product> searchByNamePrefix(@RequestParam("prefix") String prefix,
                                            @RequestParam(name="size", required=false, defaultValue = "50") int size) {
        return repository.findByNameStartingWith(prefix, PageRequest.of(0, size));
    }
}

package com.decentkids.service;

import com.decentkids.dto.ProductDto.*;
import com.decentkids.entity.Product;
import com.decentkids.exception.ResourceNotFoundException;
import com.decentkids.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Page<Response> getAllProducts(Pageable pageable) {
        return productRepository.findByActiveTrue(pageable).map(this::toResponse);
    }

    public Response getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return toResponse(product);
    }

    public Page<Response> searchProducts(String query, Pageable pageable) {
        return productRepository.searchProducts(query, pageable).map(this::toResponse);
    }

    public List<Response> getByCategory(Product.Category category) {
        return productRepository.findByCategoryAndActiveTrue(category)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<Response> getByAgeGroup(Product.AgeGroup ageGroup) {
        return productRepository.findByAgeGroupAndActiveTrue(ageGroup)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public Response createProduct(CreateRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .imageUrl(request.getImageUrl())
                .category(request.getCategory())
                .ageGroup(request.getAgeGroup())
                .sizes(request.getSizes())
                .build();

        return toResponse(productRepository.save(product));
    }

    @Transactional
    public Response updateProduct(Long id, UpdateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        if (request.getName() != null) product.setName(request.getName());
        if (request.getDescription() != null) product.setDescription(request.getDescription());
        if (request.getPrice() != null) product.setPrice(request.getPrice());
        if (request.getStock() != null) product.setStock(request.getStock());
        if (request.getImageUrl() != null) product.setImageUrl(request.getImageUrl());
        if (request.getCategory() != null) product.setCategory(request.getCategory());
        if (request.getAgeGroup() != null) product.setAgeGroup(request.getAgeGroup());
        if (request.getSizes() != null) product.setSizes(request.getSizes());
        if (request.getActive() != null) product.setActive(request.getActive());

        return toResponse(productRepository.save(product));
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        product.setActive(false);
        productRepository.save(product);
    }

    private Response toResponse(Product p) {
        return Response.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .price(p.getPrice())
                .stock(p.getStock())
                .imageUrl(p.getImageUrl())
                .category(p.getCategory())
                .ageGroup(p.getAgeGroup())
                .sizes(p.getSizes())
                .active(p.isActive())
                .createdAt(p.getCreatedAt())
                .build();
    }
}

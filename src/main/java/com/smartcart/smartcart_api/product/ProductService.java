package com.smartcart.smartcart_api.product;

import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Component 
public class ProductService  {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional 
    public ProductResponse create(ProductRequest request) {
        String sku = request.sku().trim();

        if(repository.existsBySku(sku)) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "A product with this sku already exists"
            );
        }
        Product product = new Product(
            request.name().trim(),
            request.description().trim(),
            request.price(),
            sku,
            request.stockQuantity()
        );

        return ProductResponse.from(repository.save(product));
    }
     @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return repository.findAll(
                Sort.by(Sort.Direction.DESC, "createdAt"))
            .stream()
            .map(ProductResponse::from)
            .toList();
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(UUID id) {
        Product product = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Product not found"
            ));

        return ProductResponse.from(product);
    }


}

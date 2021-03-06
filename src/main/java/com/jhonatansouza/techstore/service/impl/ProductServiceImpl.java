package com.jhonatansouza.techstore.service.impl;

import com.jhonatansouza.techstore.model.ProductItem;
import com.jhonatansouza.techstore.repository.ProductRepository;
import com.jhonatansouza.techstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Mono<ProductItem> createItem(ProductItem productItem) {
        productItem.setUuid(UUID.randomUUID().toString());
        return this.productRepository.save(productItem);
    }

    @Override
    public Mono<Optional<ProductItem>> findById(String id) {
        return this.productRepository.findById(id);
    }

    @Override
    public Flux<ProductItem> getAll() throws ExecutionException, InterruptedException {
        return this.productRepository.getAll();
    }

    @Override
    public Mono<ProductItem> update(ProductItem productItem) {
        return this.productRepository.findById(productItem.getUuid())
                .flatMap(productItemReceiver -> this.productRepository.update(productItem));
    }

    @Override
    public Mono<Void> delete(ProductItem productItem) {
        return this.productRepository.findById(productItem.getUuid())
                .flatMap(productItem1 -> this.productRepository.delete(productItem));
    }
}

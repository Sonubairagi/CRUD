package com.CRUD.CRUD.controllers.impl;

import com.CRUD.CRUD.controllers.ProductController;
import com.CRUD.CRUD.dto.ProductDto;
import com.CRUD.CRUD.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@Slf4j
public class ProductControllerImpl implements ProductController {

    private ProductService productService;

    public ProductControllerImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {

        log.debug("ProductControllerImpl::createProduct method execution has started with request ProductDto Object : {}", productDto);

        ProductDto product = this.productService.createProduct(productDto);

        log.debug("ProductControllerImpl::createProduct method execution has ended with response : {}", product);

        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @Override
    @GetMapping("/{id}")
    @Cacheable(value = "getProductCache")
    public ResponseEntity<ProductDto> getProduct(@PathVariable long id) {

        log.debug("ProductController::getProduct method execution has started with request : {}", id);

        ProductDto product = this.productService.getProduct(id);

        log.debug("ProductController::getProduct method execution has ended with response : {}", product);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @Override
    @GetMapping
    @Cacheable(value = "getProductsCache")
    public ResponseEntity<List<ProductDto>> getProducts() {

        log.info("ProductController::getProducts method execution has started");

        List<ProductDto> products = this.productService.getProducts();

        log.debug("ProductController::getProducts method execution has ended with response : {}", products);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable long id) {

        log.debug("ProductController::updateProduct method execution has been started with request : {}", productDto," & ", id);

        ProductDto productDto1 = this.productService.updateProduct(productDto, id);

        log.debug("ProductController::updateProduct method execution has been ended with response : {}", productDto1);

        return new ResponseEntity<>(productDto1, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {

        log.debug("ProductController::deleteProduct method execution has been started with response id : {}", id);

        this.productService.deleteProduct(id);

        log.info("ProductController::deleteProduct method execution has been ended");

        return new ResponseEntity<>("product has been deleted successfully",HttpStatus.OK);
    }
}

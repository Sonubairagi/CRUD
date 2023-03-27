package com.CRUD.CRUD.controllers;

import java.util.List;

import com.CRUD.CRUD.dto.ProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

public interface ProductController {

    ResponseEntity<ProductDto> createProduct(ProductDto productDto);

    ResponseEntity<ProductDto> getProduct(long id);

    ResponseEntity<List<ProductDto>> getProducts();

    ResponseEntity<ProductDto> updateProduct(ProductDto productDto, long id);

    ResponseEntity<String> deleteProduct(long id);
}

package com.CRUD.CRUD.services;

import java.util.List;

import com.CRUD.CRUD.dto.ProductDto;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);

    ProductDto getProduct(long id);

    List<ProductDto> getProducts();

    ProductDto updateProduct(ProductDto productDto, long id);

    void deleteProduct(long id);
}

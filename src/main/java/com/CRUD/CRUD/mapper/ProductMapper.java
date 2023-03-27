package com.CRUD.CRUD.mapper;

import com.CRUD.CRUD.dao.ProductRepository;
import com.CRUD.CRUD.dto.ProductDto;
import com.CRUD.CRUD.entities.Product;

public class ProductMapper {


    public static Product mapToProduct(ProductDto productDto){

        Product product = new Product();
        product.setName(productDto.getName());
        return product;

    }

    public static ProductDto mapToProductDto(Product product){

        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        return productDto;

    }

}

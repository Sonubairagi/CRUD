package com.CRUD.CRUD.services.impl;

import com.CRUD.CRUD.dao.ProductRepository;
import com.CRUD.CRUD.dto.ProductDto;
import com.CRUD.CRUD.entities.Product;
import com.CRUD.CRUD.exception.ResourceNotFoundException;
import com.CRUD.CRUD.mapper.ProductMapper;
import com.CRUD.CRUD.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {

        log.debug("ProductServiceImpl::createProduct method execution has started with request Object : {}", productDto);

        Product product = ProductMapper.mapToProduct(productDto);
        log.info("product in service layer after mapping {}", product);
        Product savedProduct = this.productRepository.save(product);

        log.debug("ProductServiceImpl::createProduct method execution has ended with response : {}", savedProduct);

        return ProductMapper.mapToProductDto(savedProduct);
    }

    @Override
    public ProductDto getProduct(long id) {

        log.debug("ProductServiceImpl::getProduct method execution has started with request id : {}", id);

        Product product = this.productRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("resource not fournd with id :"+id));

        log.info("ProductServiceImpl::getProduct method execution has ended with response :{}", product);

        return ProductMapper.mapToProductDto(product);
    }

    @Override
    public List<ProductDto> getProducts() {

        log.info("ProductServiceImpl::getProducts method execution has Started");

        List<Product> allProduct = this.productRepository.findAll();

        log.debug("ProductServiceImpl::getProduct method execution has ended with response :{}", allProduct);

        return allProduct.stream().map(p->ProductMapper.mapToProductDto(p)).collect(Collectors.toList());
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, long id) {

        log.debug("ProductServiceImpl::updateProduct method execution has been started with request : {}", productDto," & ", id);

        Product product = this.productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("product not found with id :" + id));
        product.setName(productDto.getName());
        log.debug("after updating product : {}", product);
        Product saveProduct = this.productRepository.save(product);

        log.debug("ProductServiceImpl::updateProduct method execution has been ended with response : {}", saveProduct);

        return ProductMapper.mapToProductDto(saveProduct);
    }

    @Override
    public void deleteProduct(long id) {

        log.debug("ProductServiceImpl::deleteProduct method execution has been started with request : {}", id);

        Product product = this.productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("product not found with id :" + id));
        this.productRepository.delete(product);

        log.info("ProductService::deleteProduct method execution has been ended");
    }
}

package com.CRUD.CRUD;

import com.CRUD.CRUD.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Product, Long> {
}

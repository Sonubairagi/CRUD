package com.CRUD.CRUD;

import java.util.List;

import com.CRUD.CRUD.dto.ProductDto;
import com.CRUD.CRUD.entities.Product;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CrudApplicationTests {

	@LocalServerPort
	private int port;

	private static RestTemplate restTemplate;

	private String baseUrl = "http://localhost";

	@Autowired
	private TestRepository h2Repository;

	@BeforeAll
	public static void init(){

		restTemplate = new RestTemplate();

	}

	@BeforeEach
	public void setUp(){

		baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/product");

	}

	@Test
	@Sql(statements = "INSERT INTO products(id,name) values(10,'neno')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "TRUNCATE TABLE products", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void createProductTest(){

		ProductDto productDto = new ProductDto(20, "alto");
		ProductDto productDto1 = this.restTemplate.postForObject(baseUrl, productDto, ProductDto.class);
		Assert.assertEquals("alto", productDto1.getName());
		Assert.assertEquals(2, h2Repository.findAll().size());
	}

	@Test
	@Sql(statements = "INSERT INTO products(id,name) VALUES(20,'bike')",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "DELETE FROM products WHERE name = 'bike'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void getProductsTest(){

		List<ProductDto> products = this.restTemplate.getForObject(baseUrl, List.class);
		Assert.assertEquals(1, h2Repository.findAll().size());
		Assert.assertEquals(1, products.size());

	}

	@Test
	@Sql(statements = "INSERT INTO products(id,name) values(30,'flight')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "DELETE FROM products WHERE id = 30", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void getProductTest(){

		ProductDto productDto = this.restTemplate.getForObject(baseUrl+"/{id}", ProductDto.class, 30);
		assertAll(
				()->assertNotNull(productDto),
				()->assertEquals(30, productDto.getId()),
				()->assertEquals("flight", productDto.getName()),
				()->assertEquals(1, h2Repository.findAll().size())
		);

	}

	@Test
	@Sql(statements = "INSERT INTO products(id,name) values(40, 'laptop')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "DELETE FROM products WHERE id = 40", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void updateProductTest(){

		ProductDto productDto = new ProductDto(40, "dekstop");

		this.restTemplate.put(baseUrl+"/{id}", productDto, 40);
		Product product = this.h2Repository.findById(40l).get();

		assertAll(

				()->assertNotNull(product),
				()->assertEquals(40, product.getId()),
				()->assertEquals("dekstop", product.getName()),
				()->assertEquals(1, h2Repository.findAll().size())
		);

	}

	@Test
	@Sql(statements = "INSERT INTO products(id, name) values(50, 'table')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	public void deleteProductTest(){

		assertEquals(1,h2Repository.findAll().size());
		assertEquals("table", h2Repository.findById(50l).get().getName());
		this.restTemplate.delete(baseUrl+"/{id}", 50);
		assertEquals(0, h2Repository.findAll().size());

	}

}

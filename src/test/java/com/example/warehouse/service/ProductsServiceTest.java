package com.example.warehouse.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.warehouse.model.Article;
import com.example.warehouse.model.AvailableProduct;
import com.example.warehouse.model.Product;
import com.example.warehouse.model.ProductPart;
import com.example.warehouse.repository.ArticleRepository;
import com.example.warehouse.repository.ProductRepository;
import com.example.warehouse.service.ProductService.NotEnoughStockException;
import com.example.warehouse.service.ProductService.UnknownProductException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

class ProductsServiceTest {
	
	private final ProductRepository productRepository = mock(ProductRepository.class);
	private final ArticleRepository articleRepository = mock(ArticleRepository.class);
	
	private final ProductService sut = new ProductService(new ObjectMapper(), productRepository,
			articleRepository);
	
	private final static ProductPart leg4 = new ProductPart(1, 4);
	private final static ProductPart seat1 = new ProductPart(2, 1);
	
	@Test
	void testNotEnoughInventoryForAvailableProduct() {
		when(productRepository.findAll())
				.thenReturn(Collections.singletonList(
						new Product(1, "chair", Arrays.asList(leg4, seat1))
				));
		
		when(articleRepository.findById(1)).thenReturn(
				new Article(1, "leg", 3)
		);
		when(articleRepository.findById(2)).thenReturn(
				new Article(2, "seat", 2)
		);
		
		List<AvailableProduct> actual = sut.getAvailableProducts();
		assertEquals(Arrays.asList(
				new AvailableProduct(1, "chair", 0)
		), actual);
	}
	
	@Test
	void testTwoAvailableProducts() {
		ProductPart back1 = new ProductPart(3, 1);
		ProductPart top1 = new ProductPart(4, 1);
		when(productRepository.findAll())
				.thenReturn(Arrays.asList(
						new Product(1, "chair", Arrays.asList(leg4, seat1, back1)),
						new Product(2, "table", Arrays.asList(leg4, top1))
				));
		
		when(articleRepository.findById(1)).thenReturn(
				new Article(1, "leg", 4)
		);
		when(articleRepository.findById(2)).thenReturn(
				new Article(2, "seat", 2)
		);
		when(articleRepository.findById(3)).thenReturn(
				new Article(3, "back", 2)
		);
		when(articleRepository.findById(4)).thenReturn(
				new Article(4, "top", 1)
		);
		
		List<AvailableProduct> actual = sut.getAvailableProducts();
		assertEquals(Arrays.asList(
				new AvailableProduct(1, "chair", 1),
				new AvailableProduct(2, "table", 1)
		), actual);
	}
	
	@Test
	void parseProductList() throws IOException {
		String payload = "{\n"
				+ "  \"products\": [\n"
				+ "    {\n"
				+ "      \"name\": \"Dining Chair\",\n"
				+ "      \"contain_articles\": [\n"
				+ "        {\n"
				+ "          \"art_id\": \"1\",\n"
				+ "          \"amount_of\": \"4\"\n"
				+ "        },\n"
				+ "        {\n"
				+ "          \"art_id\": \"2\",\n"
				+ "          \"amount_of\": \"8\"\n"
				+ "        },\n"
				+ "        {\n"
				+ "          \"art_id\": \"3\",\n"
				+ "          \"amount_of\": \"1\"\n"
				+ "        }\n"
				+ "      ]\n"
				+ "    },\n"
				+ "    {\n"
				+ "      \"name\": \"Dinning Table\",\n"
				+ "      \"contain_articles\": [\n"
				+ "        {\n"
				+ "          \"art_id\": \"1\",\n"
				+ "          \"amount_of\": \"4\"\n"
				+ "        },\n"
				+ "        {\n"
				+ "          \"art_id\": \"2\",\n"
				+ "          \"amount_of\": \"8\"\n"
				+ "        },\n"
				+ "        {\n"
				+ "          \"art_id\": \"4\",\n"
				+ "          \"amount_of\": \"1\"\n"
				+ "        }\n"
				+ "      ]\n"
				+ "    }\n"
				+ "  ]\n"
				+ "}\n";
		MultipartFile file = new MockMultipartFile("name", payload.getBytes());
		List<Product> actual = sut.parse(file);
		
		assertEquals(Arrays.asList(
				new Product("Dining Chair", Arrays.asList(
						new ProductPart(1, 4),
						new ProductPart(2, 8),
						new ProductPart(3, 1)
				)),
				new Product("Dinning Table", Arrays.asList(
						new ProductPart(1, 4),
						new ProductPart(2, 8),
						new ProductPart(4, 1)
				))), actual);
	}
	
	@Test
	void testSellProductUpdateRemainingStock() {
		when(productRepository.findById(1)).thenReturn(
				Optional.of(new Product(1, "chair",
						Arrays.asList(leg4, seat1)
				))
		);
		
		when(articleRepository.findById(1))
				.thenReturn(new Article(1, "leg", 7));
		when(articleRepository.findById(2))
				.thenReturn(new Article(2, "seat", 2));
		
		sut.sellProduct(1, 1);
		
		verify(articleRepository).save(new Article(1,"leg", 3));
		verify(articleRepository).save(new Article(2,"seat", 1));
	}
	
	@Test
	void testSellProductUnknownProductIdException() {
		when(productRepository.findById(1)).thenReturn(Optional.empty());
		assertThrows(UnknownProductException.class, () -> sut.sellProduct(1, 1));
	}
	
	@Test
	void testSellProductNotEnoughStockException() {
		when(productRepository.findById(1)).thenReturn(
				Optional.of(new Product(1, "chair",
						Collections.singletonList(leg4)
				))
		);
		when(articleRepository.findById(1))
				.thenReturn(new Article(1, "leg", 3));
		assertThrows(NotEnoughStockException.class, () -> sut.sellProduct(1, 1));
	}
}

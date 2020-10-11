package com.example.warehouse.service;

import com.example.warehouse.model.Article;
import com.example.warehouse.model.AvailableProduct;
import com.example.warehouse.model.Product;
import com.example.warehouse.model.ProductPart;
import com.example.warehouse.model.Products;
import com.example.warehouse.repository.ArticleRepository;
import com.example.warehouse.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ProductService {
	
	private final ObjectMapper mapper;
	private final ProductRepository productRepository;
	private final ArticleRepository articleRepository;
	
	public ProductService(ObjectMapper mapper,
			ProductRepository productRepository,
			ArticleRepository articleRepository) {
		this.mapper = mapper;
		this.productRepository = productRepository;
		this.articleRepository = articleRepository;
	}
	
	public void store(MultipartFile file) throws IOException {
		productRepository.saveAll(parse(file));
	}
	
	public List<Product> parse(MultipartFile file) throws IOException {
		return mapper.readValue(file.getBytes(), Products.class).getProductList();
	}
	
	public List<AvailableProduct> getAvailableProducts() {
		List<AvailableProduct> result = new LinkedList<>();
		productRepository.findAll().forEach(
				product -> result.add(getAvailability(product))
		);
		return result;
	}
	
	private AvailableProduct getAvailability(Product product) {
		List<ProductPart> partList = product.getProductPartList();
		int maxAvailableProductCount = Integer.MAX_VALUE;
		for (ProductPart part : partList) {
			Article article = articleRepository.findById(part.getItemid());
			int availability = article.getStock() / part.getAmount();
			maxAvailableProductCount = Math.min(maxAvailableProductCount, availability);
		}
		return new AvailableProduct(product.getProductId(), product.getName(),
				maxAvailableProductCount);
	}
	
	public void sellProduct(int productId, int quantity) {
		Optional<Product> productOrNone = productRepository.findById(productId);
		if (productOrNone.isEmpty()) {
			throw new UnknownProductException();
		} else {
			Product product = productOrNone.get();
			AvailableProduct availableProduct = getAvailability(product);
			if (availableProduct.getQuantity() < quantity) {
				throw new NotEnoughStockException();
			}
			for (ProductPart part : product.getProductPartList()) {
				Article article = articleRepository.findById(part.getItemid());
				int newQuantity = article.getStock() - part.getAmount() * quantity;
				article.setStock(newQuantity);
				articleRepository.save(article);
			}
		}
	}
	
	public static class NotEnoughStockException extends RuntimeException {
		
		public NotEnoughStockException() {
			super();
		}
	}
	
	public static class UnknownProductException extends RuntimeException {
		
		public UnknownProductException() {
			super();
		}
	}
}

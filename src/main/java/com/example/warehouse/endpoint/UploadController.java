package com.example.warehouse.endpoint;

import com.example.warehouse.service.ArticleService;
import com.example.warehouse.service.ProductService;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/upload")
public class UploadController {
	
	private final static Logger log = LoggerFactory.getLogger(UploadController.class);
	private final ArticleService articleService;
	private final ProductService productService;
	
	public UploadController(ArticleService articleService,
			ProductService productService) {
		this.articleService = articleService;
		this.productService = productService;
	}
	
	@GetMapping("/products")
	public String uploadInventory() {
		return "upload-products";
	}
	
	@GetMapping("/articles")
	public String uploadArticles() {
		return "upload-articles";
	}
	
	@PostMapping("/articlesFile")
	public String uploadInventoryFile(@RequestParam("file") MultipartFile file) {
		try {
			articleService.store(file);
		} catch (IOException e) {
			log.error("Cannot upload file", e);
		}
		return "redirect:/";
	}
	
	@PostMapping("/productsFile")
	public String uploadProductsFile(@RequestParam("file") MultipartFile file) {
		try {
			productService.store(file);
		} catch (IOException e) {
			log.error("Cannot upload file", e);
		}
		return "redirect:/";
	}
}

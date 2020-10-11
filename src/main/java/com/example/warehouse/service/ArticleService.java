package com.example.warehouse.service;

import com.example.warehouse.model.Article;
import com.example.warehouse.model.Inventory;
import com.example.warehouse.repository.ArticleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ArticleService {
	
	private final ObjectMapper mapper;
	private final ArticleRepository repository;
	
	public ArticleService(ObjectMapper mapper, ArticleRepository repository) {
		this.mapper = mapper;
		this.repository = repository;
	}
	
	public void store(MultipartFile file) throws IOException {
		repository.saveAll(parse(file));
	}
	
	public List<Article> parse(MultipartFile file) throws IOException {
		return mapper.readValue(file.getBytes(), Inventory.class).getInventory();
	}
}

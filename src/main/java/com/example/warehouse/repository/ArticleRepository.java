package com.example.warehouse.repository;

import com.example.warehouse.model.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Integer> {
	
	Article findById(int id);
}

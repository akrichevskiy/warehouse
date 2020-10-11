package com.example.warehouse.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import com.example.warehouse.model.Article;
import com.example.warehouse.repository.ArticleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

class ArticleServiceTest {
	
	private final ArticleRepository repository = mock(ArticleRepository.class);
	private final ArticleService sut = new ArticleService(new ObjectMapper(), repository);
	
	@Test
	void testArticleParsing() throws IOException {
		String payload = "{\n"
				+ "  \"inventory\": [\n"
				+ "    {\n"
				+ "      \"art_id\": \"1\",\n"
				+ "      \"name\": \"leg\",\n"
				+ "      \"stock\": \"12\"\n"
				+ "    },\n"
				+ "    {\n"
				+ "      \"art_id\": \"4\",\n"
				+ "      \"name\": \"table top\",\n"
				+ "      \"stock\": \"1\"\n"
				+ "    }\n"
				+ "  ]\n"
				+ "}\n";
		MultipartFile file = new MockMultipartFile("name", payload.getBytes());
		List<Article> actual = sut.parse(file);
		
		assertEquals(Arrays.asList(
				new Article(1, "leg", 12),
				new Article(4, "table top", 1)
		), actual);
	}
}

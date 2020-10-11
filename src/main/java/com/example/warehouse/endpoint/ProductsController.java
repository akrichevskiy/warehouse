package com.example.warehouse.endpoint;

import com.example.warehouse.model.AvailableProduct;
import com.example.warehouse.service.ProductService;
import com.example.warehouse.service.ProductService.NotEnoughStockException;
import com.example.warehouse.service.ProductService.UnknownProductException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductsController {
	
	private final static Logger log = LoggerFactory.getLogger(ProductsController.class);
	
	private final ProductService productService;
	
	public ProductsController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping("/available")
	public String availableProducts(Model model) {
		model.addAttribute("products", productService.getAvailableProducts());
		return "available-products";
	}
	
	@GetMapping("/sell")
	public String sellProductGet(Model model) {
		model.addAttribute("product", new AvailableProduct());
		return "delete-product";
	}
	
	@PostMapping("/sellProduct")
	public String sellProduct(@ModelAttribute AvailableProduct availableProduct, Model model) {
		try {
			productService.sellProduct(availableProduct.getProductId(), availableProduct.getQuantity());
		} catch (NotEnoughStockException | UnknownProductException e) {
			return "delete-product-result";
		}
		return "redirect:/";
	}
}

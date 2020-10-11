package com.example.warehouse.repository;

import com.example.warehouse.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {

}

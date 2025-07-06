package com.example.ecom_proj.repo;

import com.example.ecom_proj.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product,Integer> {
}

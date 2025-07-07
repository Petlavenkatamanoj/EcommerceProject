package com.example.ecom_proj.repo;

import com.example.ecom_proj.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product,Integer> {

    @Query("SELECT p from product p WHERE" +"LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword ,'%')) OR" +
    "LOWER(p.decription) LIKE LOWER(CONCAT('%', :keyword,'%)) OR" +"LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword,'%)) OR" +
            "LOWER(p.decription) LIKE LOWER(CONCAT('%', :keyword,'%'))")
    public List<Product> searchProduct(String keyword);
}

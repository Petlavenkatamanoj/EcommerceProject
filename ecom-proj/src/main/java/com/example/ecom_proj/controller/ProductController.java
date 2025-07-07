package com.example.ecom_proj.controller;

import com.example.ecom_proj.model.Product;
import com.example.ecom_proj.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService service;
    @RequestMapping("/")
    public String greet()
    {
        return "hello world";
    }

    @GetMapping("/product")
    public ResponseEntity<List<Product>> getAllProduct()
    {
       return new ResponseEntity<>(service.getAllProduct(), HttpStatus.OK);
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id)
    {
        Product product=service.getProductById(id);
        if(product!=null)
        {
            return new ResponseEntity<>(product,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile)
    {
        try {
            Product product1 = service.addProduct(product, imageFile);
            return new ResponseEntity<>(product1,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/product/{ProductId}/image")
    public ResponseEntity<byte[]> getImageByProduct(@PathVariable int ProductId)
    {
        Product product=service.getProductById(ProductId);
        byte[] imageFile=product.getImageDate();

        return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(imageFile);
    }
    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProductById(@PathVariable int id,@RequestPart Product product, @RequestPart MultipartFile imageFile)
    {
        try {
            service.updateProductById(id,product,imageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (product!=null) {
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Updated Failed",HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("product/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable int id)
    {
        boolean a=service.deleteProductById(id);
        if(a) {
            return new ResponseEntity<>("Delete Product Successfully", HttpStatus.OK);
        }else{
            return  new ResponseEntity<>("Delete Failed",HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/product/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword)
    {
        List<Product> products=service.searchProduct(keyword);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
}

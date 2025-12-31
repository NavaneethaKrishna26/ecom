package com.ecomapp.controller;

import com.ecomapp.dto.ProductDTO.ProductRequest;
import com.ecomapp.dto.ProductDTO.ProductResponse;
import com.ecomapp.model.Product;
import com.ecomapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("ecomapp/products")
public class ProductController
{

    private ProductService productService;
    ProductController(ProductService productService){
        this.productService=productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> postProduct(@RequestBody ProductRequest product){
       ProductResponse productResponse= productService.addNewProduct(product);
        return ResponseEntity.status(201).body(productResponse);
    }


    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(){
        return ResponseEntity.status(200).body(productService.getAllProducts());
    }


    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> getAProduct(@RequestParam String keyword){
       return ResponseEntity.ok(productService.getAProduct(keyword)) ;

    }


    @PutMapping("/{id}")
    public ResponseEntity<String> putProduct(@PathVariable long id,@RequestBody ProductRequest productRequest){
        if(productService.modifyProduct(id,productRequest)){
           return ResponseEntity.ok("Update success");
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")

    public ResponseEntity<String> deleteProduct(@PathVariable long id){
        if(productService.deleteAProduct(id)){
            return ResponseEntity.status(201).body("User Deleted");
        }
        return ResponseEntity.notFound().build();
    }
}

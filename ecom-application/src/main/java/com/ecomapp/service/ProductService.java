package com.ecomapp.service;

import com.ecomapp.dto.ProductDTO.ProductRequest;
import com.ecomapp.dto.ProductDTO.ProductResponse;
import com.ecomapp.model.Product;
import com.ecomapp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.PrivateKey;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService
{
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository){
        this.productRepository=productRepository;
    }
    public List<ProductResponse> getAllProducts(){
        return productRepository.findByActiveTrue().stream().map(this::productToProductResponse).collect(Collectors.toList());
    }

    public List<ProductResponse> getAProduct(String keyword){
        return productRepository.searchProducts(keyword).stream().map(this::productToProductResponse).collect(Collectors.toList());
    }

    public ProductResponse addNewProduct(ProductRequest product){
        Product newproduct=new Product();
        productReqToProduct(newproduct,product);
        Product product1=productRepository.save(newproduct);
        return productToProductResponse(product1);
    }

    public boolean modifyProduct(Long id,ProductRequest product){
        return productRepository.findById(id).map(existingproduct->{
           productReqToProduct(existingproduct,product);
           productRepository.save(existingproduct);
           return true;
        }).orElse(false);
    }
    public boolean deleteAProduct(long id){
        return productRepository.findById(id).map(product ->{
            product.setActive(false);
            productRepository.save(product);
            return true;
        }).orElse(false);
    }


    public void productReqToProduct(Product existingproduct,ProductRequest proreq){
        existingproduct.setName(proreq.getName());
        existingproduct.setDescription(proreq.getDescription());

        existingproduct.setPrice(proreq.getPrice());
        existingproduct.setStockQuantity(proreq.getStockQuantity());
        existingproduct.setCategory(proreq.getCategory());
        existingproduct.setImageUrl(proreq.getImageUrl());
    }

    public ProductResponse productToProductResponse(Product product){
        ProductResponse productResponse=new ProductResponse();
        productResponse.setId(String.valueOf(product.getId()));
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setStockQuantity(product.getStockQuantity());
        productResponse.setCategory(product.getCategory());
        productResponse.setImageUrl(product.getImageUrl());
        productResponse.setActive(product.getActive());
        return productResponse;
    }
}

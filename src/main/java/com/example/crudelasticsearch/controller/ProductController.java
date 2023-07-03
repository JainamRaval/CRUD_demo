package com.example.crudelasticsearch.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.crudelasticsearch.model.Product;
import com.example.crudelasticsearch.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@Tag(name = "Product", description = "Product API")
public class ProductController {
    
    @Autowired
    private ProductService service;

    @GetMapping("/products")
    @Operation(summary = "Fetch Products", description = "Fetch all the products.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)), @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }) })
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(required = false,defaultValue = "0") int offset,
    @RequestParam(required = false,defaultValue = "3") int pageSize,
    @RequestParam(required = false,defaultValue = "empty") String field) {
        Iterable<Product> list = service.getAllProducts(pageSize,offset,field);
        List<Product> result = new ArrayList<Product>();
        list.forEach(result::add);
        if(result.size() == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(result));
    }
    
    @GetMapping("/products/{id}")
    @Operation(summary = "Fetch Product", description = "Fetch a perticular product with productID.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)), @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }) })
    public ResponseEntity<Product> geProduct(@PathVariable("id") Long productID){
        Optional<Product> product = service.getProduct(productID);
        if(product.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(product.get()));
    }


    @PostMapping("/products")
    @Operation(summary = "Create Product", description = "Create a product by giving productID,rating and review.")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "successful operation", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)), @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }) })
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        try {
            Product p = service.saveProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(p);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        
    }

    @PutMapping("/products/{id}")
    @Operation(summary = "Update Product", description = "Update the product")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)), @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }) })
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long productID,@RequestBody Product product){
        try{
            Product result = service.updateProduct(product, productID);
            return ResponseEntity.ok().body(result);
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/products/{id}")
    @Operation(summary = "Delete Product", description = "Delete the product.")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "successful operation", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)), @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }) })
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long productID){
        try {
            service.deleteProduct(productID);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

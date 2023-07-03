package com.example.crudelasticsearch.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.crudelasticsearch.productRepo;
import com.example.crudelasticsearch.model.Product;

@Service
public class ProductService {
    @Autowired
    private productRepo repo;

    public Iterable<Product> getAllProducts(int pageSize,int offset,String field) {
        if(pageSize == 0){
            if(field.equals("empty")){
                return repo.findAll();
            }
            else{
                return repo.findAll(Sort.by(field));
            }
        }
        else{
            if(field.equals("empty")){
                return repo.findAll(PageRequest.of(offset,pageSize));
            }
            else{
                return repo.findAll(PageRequest.of(offset,pageSize).withSort(Sort.by(field)));
            }
        }
    }

    public Optional<Product> getProduct(Long id) {
        return repo.findById(id);
    }

    public Product saveProduct(Product product) {
        return repo.save(product);
    }

    public Product updateProduct(Product product,Long id) throws Exception{
        Optional<Product> p = repo.findById(id);
        if(p.isEmpty())
        throw new Exception();
        Product result = p.get();
        result.setRating(product.getRating());
        result.setReviews(product.getReviews());
        return repo.save(result);
    }

    public void deleteProduct(Long id){
        repo.deleteById(id);
    }
}

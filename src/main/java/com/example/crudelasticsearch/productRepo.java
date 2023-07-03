package com.example.crudelasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.example.crudelasticsearch.model.Product;

public interface productRepo extends ElasticsearchRepository<Product,Long> {
    
}

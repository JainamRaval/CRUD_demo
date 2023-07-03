package com.example.crudelasticsearch.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.crudelasticsearch.productRepo;
import com.example.crudelasticsearch.model.Product;
import com.example.crudelasticsearch.model.Review;

@Service
public class ReviewService {
    
    @Autowired
    private productRepo repo;

    public Iterable<Review> getAllReviews(Long id){
        return repo.findById(id).orElse(new Product()).getReviews();
    }

    public Review getReview(Long productID,Long reviewID){
        Optional<Product> product = repo.findById(productID);
        if(product.isEmpty())
        return null;
        List<Review> reviews = product.get().getReviews();
        for(Review r:reviews){
            if(r.getReviewID() == reviewID){
                return r;
            }
        }
        return null;
    }

    public Review creatReview(Long productID,Review review){
        Optional<Product> product = repo.findById(productID);
        if(product.isEmpty())
        return null;
        List<Review> reviews = product.get().getReviews();
        reviews.add(review);
        product.get().setReviews(reviews);
        repo.save(product.get());
        return review;
    }

    public Review updatReview(Long productID,Long reviewID,Review review){
        Optional<Product> product = repo.findById(productID);
        if(product.isEmpty())
        return null;
        List<Review> reviews = product.get().getReviews();
        for(Review r:reviews){
            if(r.getReviewID() == reviewID){
                r.setDescription(review.getDescription());
                r.setName(review.getName());
                break;
            }
        }
        repo.save(product.get());
        return review;
    }

    public Product deletReview(Long productID,Long reviewID){
        Optional<Product> product = repo.findById(productID);
        if(product.isEmpty()){
            return null;
        }
        List<Review> reviews = product.get().getReviews();
        int idx=-1;
        for(int i=0;i<reviews.size();++i){
            if(reviews.get(i).getReviewID() == reviewID){
                idx = i;
                break;
            }
        }
        if(idx == -1){
            return null;
        }
        reviews.remove(idx);
        product.get().setReviews(reviews);
        return repo.save(product.get());
    }
}

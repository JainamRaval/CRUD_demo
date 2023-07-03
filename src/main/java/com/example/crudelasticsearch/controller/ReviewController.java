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
import org.springframework.web.bind.annotation.RestController;

import com.example.crudelasticsearch.model.Product;
import com.example.crudelasticsearch.model.Review;
import com.example.crudelasticsearch.service.ReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Review", description = "Review API")
public class ReviewController {
    @Autowired
    private ReviewService service;

    @GetMapping("/products/{productID}/reviews")
    @Operation(summary = "Fetch Reviews", description = "Fetch all the Reviews.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Review.class)), @Content(mediaType = "application/json", schema = @Schema(implementation = Review.class)) }) })
    public ResponseEntity<List<Review>> getReviews(@PathVariable("productID") Long id){
        Iterable<Review> list = service.getAllReviews(id);
        List<Review> result = new ArrayList<Review>();
        list.forEach(result::add);
        if(result.size() == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(result));
    }

    @GetMapping("/products/{productID}/reviews/{reviewID}")
    @Operation(summary = "Fetch Review", description = "Fetch a perticular review with reviewID.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)), @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }) })
    public ResponseEntity<Review> getReview(@PathVariable("productID") Long pID,@PathVariable("reviewID") Long rID){
        Review review = service.getReview(pID, rID);
        if(review == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(review));
    }

    @PostMapping("/products/{productID}/reviews")
    @Operation(summary = "Create Review", description = "Create a review by giving reviewID,name and description.")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "successful operation", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)), @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }) })
    public ResponseEntity<Review> creatReview(@PathVariable("productID") Long id,@RequestBody Review review){
        try {
            Review r = service.creatReview(id, review);
            if(r == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(r);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/products/{productID}/reviews/{reviewID}")
    @Operation(summary = "Update review", description = "Update the review for a product")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)), @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }) })
    public ResponseEntity<Review> updatReview(@PathVariable("productID") Long pID,@PathVariable("reviewID") Long rID,@RequestBody Review review){
        try {
            Review r = service.updatReview(pID, rID, review);
            if(r == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok().body(r);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/products/{productID}/reviews/{reviewID}")
    @Operation(summary = "Delete Review", description = "Delete the Review.")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "successful operation", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)), @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }) })
    public ResponseEntity<Void> deletReview(@PathVariable("productID") Long pID,@PathVariable("reviewID") Long rID){
        try {
            service.deletReview(pID, rID);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

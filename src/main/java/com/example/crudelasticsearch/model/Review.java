package com.example.crudelasticsearch.model;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    private Long reviewID;
    private String name;
    private String description;
}

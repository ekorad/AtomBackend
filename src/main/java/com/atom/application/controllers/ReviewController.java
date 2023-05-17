package com.atom.application.controllers;


import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.atom.application.models.Product;
import com.atom.application.models.Review;
import com.atom.application.services.ProductsService;
import com.atom.application.services.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
public class ReviewController {
    
    @Autowired
    private ReviewService serviceReview;
    @Autowired
    private ProductsService serviceProduct;


    @PostMapping("/add")
    public void addNewReview(@Valid @RequestBody Review newReview) {

        Review reviewToBeAdded = new Review();
        reviewToBeAdded.setGrade(newReview.getGrade()); 
        reviewToBeAdded.setReview(newReview.getReview());
        reviewToBeAdded.setDate(newReview.getDate());

       Optional<Product> optionalProduct = serviceProduct.getProductById(newReview.getProductId());
    
// System.out.println(newReview.getProductId()+"ia id ul");
// System.out.println(serviceProduct.getProductById(1l)+"ce da aici");
// System.out.println(serviceProduct.getProductById(newReview.getProductId())+"ce da aici");

        Product productToBeUpdated = optionalProduct.get();

        List<Review> listOfReview = productToBeUpdated.getReviews();
        listOfReview.add(reviewToBeAdded);

        productToBeUpdated.setReviews(listOfReview);
        serviceReview.addNewReview(reviewToBeAdded);
        serviceProduct.addNewProduct(productToBeUpdated);

    }

}

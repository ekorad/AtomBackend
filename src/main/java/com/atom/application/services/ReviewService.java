package com.atom.application.services;

import java.util.List;

import com.atom.application.models.Review;
import com.atom.application.repos.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    
    @Autowired
    private ReviewRepository repo;

    public List<Review> getAllReviews() {
        return repo.findAll();
    }

    public Review addNewReview(Review newReview) {
        return repo.save(newReview);
    }

}

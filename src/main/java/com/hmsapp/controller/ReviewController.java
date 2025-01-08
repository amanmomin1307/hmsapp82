package com.hmsapp.controller;

import com.hmsapp.entity.Property;
import com.hmsapp.entity.Reviews;
import com.hmsapp.entity.User;
import com.hmsapp.payload.ProfileDto;
import com.hmsapp.repository.PropertyRepository;
import com.hmsapp.repository.ReviewsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {

    private PropertyRepository propertyRepository;
    private ReviewsRepository reviewsRepository;

    public ReviewController(PropertyRepository propertyRepository, ReviewsRepository reviewsRepository){
        this.propertyRepository=propertyRepository;
        this.reviewsRepository = reviewsRepository;
    }

    @PostMapping
    public String addReview(
            @RequestBody Reviews reviews,
            @RequestParam long propertyId,
            @AuthenticationPrincipal User user
    ){
        Property property = propertyRepository.findById(propertyId).get();

        Reviews reviewsStatus = reviewsRepository.findByPropertyAndUser(property,user);

        if(reviewsStatus!=null) {
            reviews.setProperty(property);
            reviews.setUser(user);
            reviewsRepository.save(reviews);
        }
        return "Review alerdly given";
    }

    @GetMapping("user/reviews")
    public List<Reviews> viewMyReviews(
            @AuthenticationPrincipal User user
    ){
        return reviewsRepository.findByUser(user);
    }

    @GetMapping
    public ResponseEntity<ProfileDto> getUserProfile(
            @AuthenticationPrincipal User user
    ){
        ProfileDto dto = new ProfileDto();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}

package com.lewd.user.service.external.services;

import com.lewd.user.service.entity.Hotel;
import com.lewd.user.service.entity.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "RATING-SERVICE")
public interface RatingService {

    //get
    @GetMapping("ratings/users/{userId}")
    Rating[] getRating(@PathVariable("userId")String userId);

    //post
    @PostMapping("/ratings")
    public Rating createRating(@RequestBody Rating rating);

    //PUT
    @PutMapping("/ratings/{ratingId}")
    public Rating updateRating(@PathVariable("ratingId") String ratingId, Rating rating);
}

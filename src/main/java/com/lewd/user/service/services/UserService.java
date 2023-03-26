package com.lewd.user.service.services;

import com.lewd.user.service.entity.Hotel;
import com.lewd.user.service.entity.Rating;
import com.lewd.user.service.entity.User;
import com.lewd.user.service.exceptions.ResourceNotFoundException;
import com.lewd.user.service.external.services.HotelService;
import com.lewd.user.service.external.services.RatingService;
import com.lewd.user.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RatingService ratingService;

    public User saveUser(User user){
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
       return userRepository.save(user);
    }

    public List<User> getAllUser(){
    return userRepository.findAll();
    }

    public User getUserById(String userId){

    User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found "+userId));

    /*String url = "http://RATING-SERVICE/ratings/users/"+user.getUserId();

        Rating[] ratingList = restTemplate.getForObject(url, Rating[].class );*/
        Rating[] ratingList = ratingService.getRating(user.getUserId());
        List<Rating> ratings = Arrays.stream(ratingList).collect(Collectors.toList());

    List<Rating> newRatingList = ratings.stream().map((rating) ->{

       // String hotelURL = "http://HOTEL-SERVICE/hotels/"+rating.getHotelId();
        //ResponseEntity<Hotel> hotel = restTemplate.getForEntity(hotelURL, Hotel.class);
        //Hotel hotel1 = hotel.getBody();
        Hotel hotel1 = hotelService.getHotel(rating.getHotelId());
        rating.setHotel(hotel1);
        return rating;
    }).collect(Collectors.toList());

    user.setRating(newRatingList);
    return user;
    }
}

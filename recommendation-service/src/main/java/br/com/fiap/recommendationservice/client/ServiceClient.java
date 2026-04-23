package br.com.fiap.recommendationservice.client;

import br.com.fiap.recommendationservice.dto.RestaurantDTO;
import br.com.fiap.recommendationservice.dto.UserPreferencesDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ServiceClient {
    
    private final RestTemplate restTemplate;
    
    @Retryable(
        retryFor = {RuntimeException.class},
        maxAttempts = 3,
        backoff = @Backoff(delay = 1000, multiplier = 2.0)
    )
    public UserPreferencesDTO getUserPreferences(Long userId) {
        log.info("Fetching user preferences for user id: {}", userId);
        try {
            String url = "http://user-service/api/users/" + userId;
            return restTemplate.getForObject(url, UserPreferencesDTO.class);
        } catch (Exception e) {
            log.error("Error fetching user preferences", e);
            throw new RuntimeException("Failed to fetch user preferences", e);
        }
    }
    
    @Retryable(
        retryFor = {RuntimeException.class},
        maxAttempts = 3,
        backoff = @Backoff(delay = 1000, multiplier = 2.0)
    )
    public List<RestaurantDTO> getAllRestaurants() {
        log.info("Fetching all restaurants");
        try {
            String url = "http://restaurant-service/api/restaurants";
            RestaurantDTO[] restaurants = restTemplate.getForObject(url, RestaurantDTO[].class);
            return Arrays.asList(restaurants != null ? restaurants : new RestaurantDTO[0]);
        } catch (Exception e) {
            log.error("Error fetching restaurants", e);
            throw new RuntimeException("Failed to fetch restaurants", e);
        }
    }
    
    @Retryable(
        retryFor = {RuntimeException.class},
        maxAttempts = 3,
        backoff = @Backoff(delay = 1000, multiplier = 2.0)
    )
    public List<RestaurantDTO> getRestaurantsByCategory(String category) {
        log.info("Fetching restaurants by category: {}", category);
        try {
            String url = "http://restaurant-service/api/restaurants/category/" + category;
            RestaurantDTO[] restaurants = restTemplate.getForObject(url, RestaurantDTO[].class);
            return Arrays.asList(restaurants != null ? restaurants : new RestaurantDTO[0]);
        } catch (Exception e) {
            log.error("Error fetching restaurants by category", e);
            throw new RuntimeException("Failed to fetch restaurants by category", e);
        }
    }
    
    @Retryable(
        retryFor = {RuntimeException.class},
        maxAttempts = 3,
        backoff = @Backoff(delay = 1000, multiplier = 2.0)
    )
    public List<RestaurantDTO> getRestaurantsByLocation(String location) {
        log.info("Fetching restaurants by location: {}", location);
        try {
            String url = "http://restaurant-service/api/restaurants/location/" + location;
            RestaurantDTO[] restaurants = restTemplate.getForObject(url, RestaurantDTO[].class);
            return Arrays.asList(restaurants != null ? restaurants : new RestaurantDTO[0]);
        } catch (Exception e) {
            log.error("Error fetching restaurants by location", e);
            throw new RuntimeException("Failed to fetch restaurants by location", e);
        }
    }
}

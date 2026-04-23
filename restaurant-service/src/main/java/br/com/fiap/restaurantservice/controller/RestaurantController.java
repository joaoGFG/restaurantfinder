package br.com.fiap.restaurantservice.controller;

import br.com.fiap.restaurantservice.domain.Restaurant;
import br.com.fiap.restaurantservice.service.RestaurantService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public Restaurant create(@RequestBody Restaurant restaurant) {
        return restaurantService.saveRestaurant(restaurant);
    }

    @GetMapping
    public List<Restaurant> getAll() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{id}")
    public Restaurant getById(@PathVariable Long id) {
        return restaurantService.getRestaurantById(id).orElse(null);
    }

    @GetMapping("/category/{category}")
    public List<Restaurant> getByCategory(@PathVariable String category) {
        return restaurantService.findByCategory(category);
    }

    @GetMapping("/location/{location}")
    public List<Restaurant> getByLocation(@PathVariable String location) {
        return restaurantService.findByLocation(location);
    }

    @GetMapping("/rating/{rating}")
    public List<Restaurant> getByRating(@PathVariable Double rating) {
        return restaurantService.findByRating(rating);
    }

    @GetMapping("/search")
    public List<Restaurant> search(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String location) {
        if (category != null && location != null) {
            return restaurantService.findByCategoryAndLocation(category, location);
        }
        return List.of();
    }

    @PutMapping("/{id}")
    public Restaurant update(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        restaurant.setId(id);
        return restaurantService.saveRestaurant(restaurant);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
    }
}

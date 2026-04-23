package br.com.fiap.restaurantservice.repository;

import br.com.fiap.restaurantservice.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByCategory(String category);
    List<Restaurant> findByLocation(String location);
    List<Restaurant> findByRatingGreaterThanEqual(Double rating);
    List<Restaurant> findByCategoryAndLocation(String category, String location);
}

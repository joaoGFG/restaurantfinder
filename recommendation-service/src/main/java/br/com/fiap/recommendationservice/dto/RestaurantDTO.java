package br.com.fiap.recommendationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantDTO {
    private Long id;
    private String name;
    private String category;
    private String location;
    private Double rating;
    private String cuisine;
    private Double priceRange;
    private String description;
}

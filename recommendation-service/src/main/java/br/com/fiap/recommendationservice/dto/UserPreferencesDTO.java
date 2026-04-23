package br.com.fiap.recommendationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPreferencesDTO {
    private Long userId;
    private String location;
    private List<String> preferredCuisines;
    private List<String> preferredCategories;
    private Double maxPriceRange;
    private Double minRating;
}

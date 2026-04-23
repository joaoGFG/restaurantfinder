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
public class RecommendationDTO {
    private Long userId;
    private List<RestaurantDTO> recommendedRestaurants;
    private String aiReasoning;
}

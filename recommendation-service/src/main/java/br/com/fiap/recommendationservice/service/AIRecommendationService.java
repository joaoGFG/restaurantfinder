package br.com.fiap.recommendationservice.service;

import br.com.fiap.recommendationservice.client.ServiceClient;
import br.com.fiap.recommendationservice.dto.RecommendationDTO;
import br.com.fiap.recommendationservice.dto.RestaurantDTO;
import br.com.fiap.recommendationservice.dto.UserPreferencesDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AIRecommendationService {
    
    private final ServiceClient serviceClient;
    private final ChatClient chatClient;
    
    public RecommendationDTO getAIRecommendations(Long userId) {
        log.info("Getting AI recommendations for user: {}", userId);
        
        try {
            // Fetch user preferences
            UserPreferencesDTO userPreferences = serviceClient.getUserPreferences(userId);
            log.info("User preferences fetched: {}", userPreferences);
            
            // Fetch all restaurants
            List<RestaurantDTO> allRestaurants = serviceClient.getAllRestaurants();
            log.info("Total restaurants available: {}", allRestaurants.size());
            
            // Filter restaurants based on user preferences
            List<RestaurantDTO> filteredRestaurants = filterRestaurants(allRestaurants, userPreferences);
            log.info("Filtered restaurants count: {}", filteredRestaurants.size());
            
            // Get AI reasoning
            String aiReasoning = getAIReasoning(userPreferences, filteredRestaurants);
            
            return RecommendationDTO.builder()
                .userId(userId)
                .recommendedRestaurants(filteredRestaurants.stream().limit(5).collect(Collectors.toList()))
                .aiReasoning(aiReasoning)
                .build();
                
        } catch (Exception e) {
            log.error("Error getting AI recommendations", e);
            throw new RuntimeException("Failed to get AI recommendations", e);
        }
    }
    
    private List<RestaurantDTO> filterRestaurants(List<RestaurantDTO> restaurants, UserPreferencesDTO preferences) {
        return restaurants.stream()
            .filter(r -> preferences.getMinRating() == null || r.getRating() >= preferences.getMinRating())
            .filter(r -> preferences.getMaxPriceRange() == null || r.getPriceRange() <= preferences.getMaxPriceRange())
            .filter(r -> preferences.getPreferredCuisines() == null || preferences.getPreferredCuisines().isEmpty() || 
                    preferences.getPreferredCuisines().contains(r.getCuisine()))
            .filter(r -> preferences.getPreferredCategories() == null || preferences.getPreferredCategories().isEmpty() || 
                    preferences.getPreferredCategories().contains(r.getCategory()))
            .filter(r -> preferences.getLocation() == null || r.getLocation().equalsIgnoreCase(preferences.getLocation()))
            .collect(Collectors.toList());
    }
    
    private String getAIReasoning(UserPreferencesDTO preferences, List<RestaurantDTO> filteredRestaurants) {
        log.info("Generating AI reasoning for recommendations");
        
        try {
            String prompt = buildPrompt(preferences, filteredRestaurants);
            
            String response = chatClient.prompt(prompt)
                .call()
                .content();
            
            log.info("AI reasoning generated successfully");
            return response;
            
        } catch (Exception e) {
            log.error("Error generating AI reasoning", e);
            return "We filtered restaurants based on your preferences for cuisines, price range, and ratings.";
        }
    }
    
    private String buildPrompt(UserPreferencesDTO preferences, List<RestaurantDTO> restaurants) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Based on the user's preferences:\n");
        prompt.append("- Preferred Cuisines: ").append(preferences.getPreferredCuisines()).append("\n");
        prompt.append("- Preferred Categories: ").append(preferences.getPreferredCategories()).append("\n");
        prompt.append("- Max Price Range: ").append(preferences.getMaxPriceRange()).append("\n");
        prompt.append("- Min Rating: ").append(preferences.getMinRating()).append("\n");
        prompt.append("- Location: ").append(preferences.getLocation()).append("\n\n");
        
        prompt.append("Filtered restaurants:\n");
        for (RestaurantDTO restaurant : restaurants) {
            prompt.append("- ").append(restaurant.getName())
                .append(" (").append(restaurant.getCuisine()).append(", ")
                .append("Rating: ").append(restaurant.getRating()).append(", ")
                .append("Price: ").append(restaurant.getPriceRange()).append(")\n");
        }
        
        prompt.append("\nProvide a brief recommendation explaining why these restaurants match the user's preferences.");
        
        return prompt.toString();
    }
}

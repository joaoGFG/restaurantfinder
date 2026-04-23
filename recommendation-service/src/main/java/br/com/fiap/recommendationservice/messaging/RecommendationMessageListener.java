package br.com.fiap.recommendationservice.messaging;

import br.com.fiap.recommendationservice.config.RabbitMQConfig;
import br.com.fiap.recommendationservice.dto.RecommendationDTO;
import br.com.fiap.recommendationservice.service.AIRecommendationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendationMessageListener {
    
    private final AIRecommendationService recommendationService;
    private final ObjectMapper objectMapper;
    
    @RabbitListener(queues = RabbitMQConfig.RECOMMENDATION_QUEUE)
    public void handleRecommendationRequest(String message) {
        log.info("Received recommendation request: {}", message);
        
        try {
            Long userId = Long.valueOf(message);
            RecommendationDTO recommendation = recommendationService.getAIRecommendations(userId);
            log.info("Recommendation generated for user: {}", userId);
            log.info("Recommendation: {}", objectMapper.writeValueAsString(recommendation));
            
        } catch (Exception e) {
            log.error("Error processing recommendation request", e);
        }
    }
}

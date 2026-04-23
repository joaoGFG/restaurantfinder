package br.com.fiap.recommendationservice.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    
    public static final String RECOMMENDATION_QUEUE = "recommendation-queue";
    public static final String RECOMMENDATION_EXCHANGE = "recommendation-exchange";
    public static final String RECOMMENDATION_ROUTING_KEY = "recommendation.request";
    
    public static final String RESTAURANT_QUEUE = "restaurant-queue";
    public static final String RESTAURANT_EXCHANGE = "restaurant-exchange";
    public static final String RESTAURANT_ROUTING_KEY = "restaurant.update";
    
    @Bean
    public Queue recommendationQueue() {
        return new Queue(RECOMMENDATION_QUEUE, true);
    }
    
    @Bean
    public TopicExchange recommendationExchange() {
        return new TopicExchange(RECOMMENDATION_EXCHANGE, true, false);
    }
    
    @Bean
    public Binding recommendationBinding(Queue recommendationQueue, TopicExchange recommendationExchange) {
        return BindingBuilder.bind(recommendationQueue)
            .to(recommendationExchange)
            .with(RECOMMENDATION_ROUTING_KEY);
    }
    
    @Bean
    public Queue restaurantQueue() {
        return new Queue(RESTAURANT_QUEUE, true);
    }
    
    @Bean
    public TopicExchange restaurantExchange() {
        return new TopicExchange(RESTAURANT_EXCHANGE, true, false);
    }
    
    @Bean
    public Binding restaurantBinding(Queue restaurantQueue, TopicExchange restaurantExchange) {
        return BindingBuilder.bind(restaurantQueue)
            .to(restaurantExchange)
            .with(RESTAURANT_ROUTING_KEY);
    }
}

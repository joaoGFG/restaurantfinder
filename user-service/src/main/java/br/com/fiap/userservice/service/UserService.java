package br.com.fiap.userservice.service;

import br.com.fiap.userservice.domain.User;
import br.com.fiap.userservice.repository.UserRepository;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Retryable(
        retryFor = {RuntimeException.class},
        maxAttempts = 3,
        backoff = @Backoff(delay = 1000, multiplier = 2.0)
    )
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    @Retryable(
        retryFor = {RuntimeException.class},
        maxAttempts = 3,
        backoff = @Backoff(delay = 1000, multiplier = 2.0)
    )
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    @Retryable(
        retryFor = {RuntimeException.class},
        maxAttempts = 3,
        backoff = @Backoff(delay = 1000, multiplier = 2.0)
    )
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public User createUser(User user) {
        return userRepository.save(user);
    }
    
    public User updateUser(Long id, User user) {
        user.setId(id);
        return userRepository.save(user);
    }
    
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    public User updatePreferences(Long id, User user) {
        return userRepository.findById(id)
            .map(existingUser -> {
                existingUser.setPreferredCuisines(user.getPreferredCuisines());
                existingUser.setPreferredCategories(user.getPreferredCategories());
                existingUser.setMaxPriceRange(user.getMaxPriceRange());
                existingUser.setMinRating(user.getMinRating());
                existingUser.setLocation(user.getLocation());
                return userRepository.save(existingUser);
            })
            .orElseThrow(() -> new RuntimeException("User not found"));
    }
}

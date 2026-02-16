package com.cefii.learning.order_management.service;

import com.cefii.learning.order_management.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cefii.learning.order_management.model.Order;
import com.cefii.learning.order_management.model.User;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public List<Order> getOrdersByUser(Long userId) {
        return userRepository.findById(userId)
                .map(User::getOrders)
                .orElse(null);
    }

}
package com.framework.modules.useraccess.validation;

import com.framework.common.exceptions.BusinessException;
import com.framework.common.exceptions.ResourceAlreadyExistsException;
import com.framework.common.exceptions.ResourceNotFoundException;
import com.framework.modules.useraccess.entity.User;
import com.framework.modules.useraccess.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserValidation {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserValidation(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User validateUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    public User validateUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    public void validateEmailAvailability(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new ResourceAlreadyExistsException("E-mail is already in use.");
        });
    }

    public void validatePasswords(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("The passwords don't match.");
        }
    }

    public void validateCurrentPassword(User user, String currentPassword) {
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new BusinessException("Current password is incorrect.");
        }
    }

    public void validateUserExists(UUID id) {
        if (userRepository.existsById(id)) {
            throw new BusinessException("User already exists");
        }
    }
}

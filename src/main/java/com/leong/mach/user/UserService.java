package com.leong.mach.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leong.mach.role.Role;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<Role> getUserRoles(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getRoles();  // Trả về danh sách Role
        } else {
            throw new RuntimeException("User not found with email: " + email);
        }
    }

    public User getUser(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user;  // Trả về danh sách Role
        } else {
            throw new RuntimeException("User not found id" + userId);
        }
    }
}

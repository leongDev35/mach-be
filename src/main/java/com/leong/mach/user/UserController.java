package com.leong.mach.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leong.mach.role.Role;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;


    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getUserRoles(@RequestParam String email) {
        System.out.println(email);
        
        List<Role> roles = userService.getUserRoles(email);
        return ResponseEntity.ok(roles);
    }


    @GetMapping("/")
    public User getUser(@RequestParam Integer userId) {
        User user = userService.getUser(userId);
        return user;
        
    }
    

}

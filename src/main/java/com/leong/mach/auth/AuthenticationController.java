package com.leong.mach.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.leong.mach.common.ApiResponse;
import com.leong.mach.handler.ExceptionResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.OK;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name = "Authentication") // ! dùng để chỉ định tag (nhãn) của API cho mục đích tài liệu hóa API.
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationRequest request) throws MessagingException {
        service.register(request);
        return ResponseEntity.status(OK)
                .body(
                        new ApiResponse("Created new user"));
    }

    @PostMapping("/login") // ! login
    public ResponseEntity<AuthenticationResponse> authenticate(
        @Valid  @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/activate-account")
    public ModelAndView confirm(
            @RequestParam String token) throws MessagingException {
        service.activateAccount(token);
        ModelAndView modelAndView = new ModelAndView("activation");
        modelAndView.addObject("message", "Your account has been successfully activated!");
        return modelAndView;
    }

}

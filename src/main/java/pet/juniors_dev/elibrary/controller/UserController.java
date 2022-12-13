package pet.juniors_dev.elibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pet.juniors_dev.elibrary.dto.form.LoginRequest;
import pet.juniors_dev.elibrary.dto.form.RegisterRequest;
import pet.juniors_dev.elibrary.service.UserService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(userService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.login(loginRequest));
    }

    @GetMapping("/user") // route for testing jwt access token depending on role
    public ResponseEntity<String> userRoute(Authentication authentication) {
        return ResponseEntity.ok("hello user");
    }

    @GetMapping("/admin") // route for testing jwt access token depending on role
    public ResponseEntity<String> mentorRoute(Authentication authentication) {
        return ResponseEntity.ok("hello mentor");
    }
}

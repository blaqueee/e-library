package pet.juniors_dev.elibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pet.juniors_dev.elibrary.dto.form.*;
import pet.juniors_dev.elibrary.service.MailSenderService;
import pet.juniors_dev.elibrary.service.UserService;

import javax.mail.internet.AddressException;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) throws AddressException {
        userService.register(registerRequest);
        return ResponseEntity.ok("Activation link sent to your email!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.login(loginRequest));
    }

    @GetMapping("/activate/{token}")
    public ResponseEntity<?> createUser(@PathVariable String token) {
        userService.activate(token);
        return ResponseEntity.ok("Your account has been activated!");
    }

    @PostMapping("/password/reset")
    public ResponseEntity<?> sendPasswordResetLink(@Valid @RequestBody EmailRequest emailRequest) {
        userService.sendPasswordResetLink(emailRequest);
        return ResponseEntity.ok("We sent the link to your email to reset your password!");
    }

    @PostMapping("/password/reset/{token}")
    public ResponseEntity<?> resetPassword(@PathVariable String token,
                                           @Valid @RequestBody ResetPasswordRequest resetRequest) {
        userService.resetPassword(token, resetRequest);
        return ResponseEntity.ok("You password has been successfully reset");
    }

    @GetMapping("/user") // route for testing jwt access token depending on role
    public ResponseEntity<String> userRoute(Authentication authentication) {
        return ResponseEntity.ok("hello user");
    }

    @GetMapping("/admin") // route for testing jwt access token depending on role
    public ResponseEntity<String> mentorRoute(Authentication authentication) {
        return ResponseEntity.ok("hello mentor");
    }

//    public ResponseEntity<?> userEdit(@Valid @RequestBody UserEditRequest userEditRequest){
//
//    }
}

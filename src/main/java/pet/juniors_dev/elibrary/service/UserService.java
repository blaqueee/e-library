package pet.juniors_dev.elibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pet.juniors_dev.elibrary.configuration.JwtUtils;
import pet.juniors_dev.elibrary.dto.JwtDto;
import pet.juniors_dev.elibrary.dto.UserDto;
import pet.juniors_dev.elibrary.dto.form.LoginRequest;
import pet.juniors_dev.elibrary.dto.form.RegisterRequest;
import pet.juniors_dev.elibrary.entity.User;
import pet.juniors_dev.elibrary.exception.EmailAlreadyExistsException;
import pet.juniors_dev.elibrary.mapper.UserMapper;
import pet.juniors_dev.elibrary.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public UserDto register(RegisterRequest registerForm) throws EmailAlreadyExistsException {
        if (userRepository.existsByEmail(registerForm.getEmail()))
            throw new EmailAlreadyExistsException("Email " + registerForm.getEmail() + " already exists!");
        registerForm.setPassword(encoder.encode(registerForm.getPassword()));
        User user = userMapper.toUser(registerForm);
        User savedUser = userRepository.save(user);
        return userMapper.toUserDto(savedUser);
    }

    public JwtDto login(LoginRequest loginRequest) {
        Authentication auth = authenticate(loginRequest);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = jwtUtils.generateJwtToken(auth);
        User user = (User) auth.getPrincipal();
        return userMapper.toJwtDto(user, token);
    }

    private Authentication authenticate(LoginRequest loginRequest) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
    }
}

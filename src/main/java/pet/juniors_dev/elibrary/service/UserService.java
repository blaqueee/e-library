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
import pet.juniors_dev.elibrary.entity.ActivationToken;
import pet.juniors_dev.elibrary.entity.User;
import pet.juniors_dev.elibrary.exception.EmailAlreadyExistsException;
import pet.juniors_dev.elibrary.exception.TokenException;
import pet.juniors_dev.elibrary.mapper.UserMapper;
import pet.juniors_dev.elibrary.repository.ActivationTokenRepository;
import pet.juniors_dev.elibrary.repository.UserRepository;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Service
@RequiredArgsConstructor
public class UserService {
    private final MailSenderService mailSenderService;
    private final UserRepository userRepository;
    private final ActivationTokenRepository activationTokenRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public void register(RegisterRequest registerForm) throws EmailAlreadyExistsException, AddressException {
        if (userRepository.existsByEmail(registerForm.getEmail()) || activationTokenRepository.existsByEmail(registerForm.getEmail()))
            throw new EmailAlreadyExistsException("Email " + registerForm.getEmail() + " already exists!");
        validateEmail(registerForm.getEmail());
        registerForm.setPassword(encoder.encode(registerForm.getPassword()));
        ActivationToken activationToken = userMapper.toActivationToken(registerForm);
        var saved = activationTokenRepository.save(activationToken);
        mailSenderService.sendMessage(saved.getEmail(), "Activate your account!",
                "Go to the link below to activate your account:\n" +
                        "http://localhost:8080/activate/" + saved.getToken());
    }

    public UserDto activate(String token) throws TokenException {
        ActivationToken activationToken = activationTokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenException("Token doesn't exists!"));
        User user = userMapper.toUser(activationToken);
        User savedUser = userRepository.save(user);
        activationTokenRepository.delete(activationToken);
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

    private boolean validateEmail(String email) throws AddressException {
        InternetAddress internetAddress = new InternetAddress(email);
        internetAddress.validate();
        return true;
    }
}

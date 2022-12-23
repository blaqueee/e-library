package pet.juniors_dev.elibrary.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pet.juniors_dev.elibrary.configuration.JwtUtils;
import pet.juniors_dev.elibrary.dto.FileDto;
import pet.juniors_dev.elibrary.dto.JwtDto;
import pet.juniors_dev.elibrary.dto.UserDto;
import pet.juniors_dev.elibrary.dto.form.*;
import pet.juniors_dev.elibrary.entity.ActivationToken;
import pet.juniors_dev.elibrary.entity.ResetPassword;
import pet.juniors_dev.elibrary.entity.User;
import pet.juniors_dev.elibrary.exception.*;
import pet.juniors_dev.elibrary.mapper.UserMapper;
import pet.juniors_dev.elibrary.repository.ActivationTokenRepository;
import pet.juniors_dev.elibrary.repository.ResetPasswordRepository;
import pet.juniors_dev.elibrary.repository.UserRepository;
import pet.juniors_dev.elibrary.utility.CloudStorageUtil;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final MailSenderService mailSenderService;
    private final UserRepository userRepository;
    private final ActivationTokenRepository activationTokenRepository;
    private final ResetPasswordRepository resetPasswordRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final CloudStorageUtil cloudStorage;

    public void register(RegisterRequest registerForm) throws EmailAlreadyExistsException, AddressException {
        if (userRepository.existsByEmail(registerForm.getEmail()) || activationTokenRepository.existsByEmail(registerForm.getEmail()))
            throw new EmailAlreadyExistsException("Email " + registerForm.getEmail() + " already exists!");
        validateEmail(registerForm.getEmail());
        registerForm.setPassword(encoder.encode(registerForm.getPassword()));
        ActivationToken activationToken = userMapper.toActivationToken(registerForm);
        var saved = activationTokenRepository.save(activationToken);
        mailSenderService.sendMessage(saved.getEmail(), "Activate your account!",
                "Go to the link below to activate your account:\n" +
                        "http://http://elibrary-env.eba-8chmdsyi.us-east-1.elasticbeanstalk.com/api/accounts/activate/" + saved.getToken());
    }

    public UserDto activate(String token) throws TokenException {
        ActivationToken activationToken = activationTokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenException("Link doesn't exist!"));
        if (!isTokenValid(activationToken.getCreatedAt(), ActivationToken.getExpirationInMinutes()))
            throw new TokenException("Link has been expired!");
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

    public void sendPasswordResetLink(EmailRequest email) throws NotFoundException {
        Optional<User> user = userRepository.findByEmail(email.getEmail());
        if (user.isEmpty())
            throw new NotFoundException("User with email " + email.getEmail() + " not found!");
        var token = userMapper.toResetPasswordToken(user.get());
        var savedToken = resetPasswordRepository.save(token);
        mailSenderService.sendMessage(user.get().getEmail(), "Reset your password!",
                "Go to the link below to reset your password:\n" +
                        "http://elibrary-env.eba-8chmdsyi.us-east-1.elasticbeanstalk.com/api/accounts/password/reset/" + savedToken.getToken());
    }

    public void resetPassword(String token, ResetPasswordRequest resetRequest) throws TokenException, IllegalArgumentException {
        var resetPassword = resetPasswordRepository.findByToken(token)
                .orElseThrow(() -> new TokenException("Link doesn't exist!"));
        if (!resetRequest.getPassword().equals(resetRequest.getRepeatedPassword()))
            throw new IllegalArgumentException("Passwords are not similar!");
        if (!isTokenValid(resetPassword.getCreatedAt(), ResetPassword.getExpirationInMinutes()))
            throw new TokenException("Link has been expired!");
        User user = resetPassword.getUser();
        user.setPassword(encoder.encode(resetRequest.getPassword()));
        userRepository.save(user);
        resetPasswordRepository.delete(resetPassword);
    }

    public UserDto updateUser(UserEditRequest userEditRequest, User principal) throws FileException, FileWriteException, GCPFileUploadException {
        checkAvatar(userEditRequest.getAvatar());
        FileDto file = cloudStorage.uploadFile(userEditRequest.getAvatar());
        userEditRequest.setPassword(encoder.encode(userEditRequest.getPassword()));
        User user = userMapper.toUser(userEditRequest, principal, file);
        User saved = userRepository.save(user);
        return userMapper.toUserDto(saved);
    }

    private void checkAvatar(MultipartFile avatar) throws FileException {
        String extension = FilenameUtils.getExtension(avatar.getOriginalFilename());
        Set<String> extensions = Set.of("jpg", "jpeg", "img", "png", "svg");
        if (!extensions.contains(extension))
            throw new FileException("Avatar's extension is not correct!");
    }

    private boolean isTokenValid(LocalDateTime createdAt, long expiresInMinutes) {
        LocalDateTime deadline = createdAt.plusMinutes(expiresInMinutes);
        return deadline.isAfter(LocalDateTime.now());
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

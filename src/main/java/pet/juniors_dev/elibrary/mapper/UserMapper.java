package pet.juniors_dev.elibrary.mapper;

import org.springframework.stereotype.Component;
import pet.juniors_dev.elibrary.dto.JwtDto;
import pet.juniors_dev.elibrary.dto.UserDto;
import pet.juniors_dev.elibrary.dto.form.RegisterRequest;
import pet.juniors_dev.elibrary.entity.ActivationToken;
import pet.juniors_dev.elibrary.entity.ResetPassword;
import pet.juniors_dev.elibrary.entity.Role;
import pet.juniors_dev.elibrary.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class UserMapper {
    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .avatarUrl("none")
                .role(user.getRole().getValue())
                .build();
    }

    public User toUser(RegisterRequest registerForm) {
        return User.builder()
                .email(registerForm.getEmail())
                .username(registerForm.getUsername())
                .password(registerForm.getPassword())
                .role(Role.ROLE_USER)
                .build();
    }

    public User toUser(ActivationToken token) {
        return User.builder()
                .username(token.getUsername())
                .email(token.getEmail())
                .password(token.getPassword())
                .role(Role.ROLE_USER)
                .build();
    }

    public JwtDto toJwtDto(User user, String token) {
        return JwtDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .accessToken(token)
                .tokenType("Bearer")
                .build();
    }

    public ActivationToken toActivationToken(RegisterRequest form) {
        return ActivationToken.builder()
                .username(form.getUsername())
                .email(form.getEmail())
                .password(form.getPassword())
                .token(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public ResetPassword toResetPasswordToken(User user) {
        return ResetPassword.builder()
                .createdAt(LocalDateTime.now())
                .token(UUID.randomUUID().toString())
                .user(user)
                .build();
    }
}

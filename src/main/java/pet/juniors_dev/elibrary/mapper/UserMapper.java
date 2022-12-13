package pet.juniors_dev.elibrary.mapper;

import org.springframework.stereotype.Component;
import pet.juniors_dev.elibrary.dto.JwtDto;
import pet.juniors_dev.elibrary.dto.UserDto;
import pet.juniors_dev.elibrary.dto.form.RegisterRequest;
import pet.juniors_dev.elibrary.entity.Role;
import pet.juniors_dev.elibrary.entity.User;

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

    public JwtDto toJwtDto(User user, String token) {
        return JwtDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .accessToken(token)
                .tokenType("Bearer")
                .build();
    }
}

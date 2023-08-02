package account.mapper;

import account.dto.auth.UserRequest;
import account.dto.auth.UserResponse;
import account.dto.payment.PaymentResponse;
import account.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User toEntity(UserRequest authRequest) {
        return User.builder()
                .name(authRequest.name())
                .lastname(authRequest.lastname())
                .email(authRequest.email())
                .password(passwordEncoder.encode(authRequest.password()))
                .build();
    }

    public UserResponse entityToDto(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getLastname(),
                user.getEmail()
        );
    }

    public List<UserResponse> toDtoList(List<User> users) {
        return users.stream()
                .map(this::entityToDto)
                .toList();
    }
}

package account.dto.auth;

public record UserResponse(Long id,
                           String name,
                           String lastname,
                           String email) {
}

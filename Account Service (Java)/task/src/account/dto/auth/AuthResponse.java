package account.dto.auth;

public record AuthResponse(Long id,
                           String name,
                           String lastname,
                           String email) {
}

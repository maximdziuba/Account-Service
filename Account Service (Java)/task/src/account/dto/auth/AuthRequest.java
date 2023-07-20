package account.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record AuthRequest(@NotEmpty String name,
                          @NotEmpty String lastname,
                          @NotEmpty @Email(regexp = "^.+@acme.com$") String email,
                          @NotEmpty String password) {
}

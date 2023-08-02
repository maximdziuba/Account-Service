package account.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequest(@NotEmpty String name,
                          @NotEmpty String lastname,
                          @NotEmpty @Email(regexp = "^.+@acme.com$") String email,
                          @NotNull @Size(min = 12, message = "{newPassword.length}") String password) {
}

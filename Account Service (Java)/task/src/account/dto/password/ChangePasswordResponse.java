package account.dto.password;

import jakarta.validation.constraints.NotEmpty;

public record ChangePasswordResponse(@NotEmpty String email,
                                     String status) {
}

package account.mapper;

import account.dto.auth.AuthResponse;
import account.dto.payment.PaymentResponse;
import account.model.User;

public class UserMapper {

    public static PaymentResponse entityToPaymentDto(User user) {
        return new PaymentResponse(
                user.getId(),
                user.getName(),
                user.getLastname(),
                user.getEmail()
        );
    }

    public static AuthResponse entityToDto(User user) {
        return new AuthResponse(
                user.getId(),
                user.getName(),
                user.getLastname(),
                user.getEmail()
        );
    }
}

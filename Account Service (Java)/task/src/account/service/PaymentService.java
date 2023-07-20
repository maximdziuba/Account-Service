package account.service;

import account.dto.payment.PaymentResponse;
import account.model.User;

public interface PaymentService {

    PaymentResponse getPayment(User user);
}

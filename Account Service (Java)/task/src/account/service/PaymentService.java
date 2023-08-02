package account.service;

import account.dto.SimpleResponse;
import account.dto.payment.PaymentRequest;
import account.dto.payment.PaymentResponse;
import account.model.User;

import java.util.List;

public interface PaymentService {

    List<PaymentResponse> getPayment(User user);

    PaymentResponse getPayment(User user, String period);

    SimpleResponse uploadPayrolls(List<PaymentRequest> payments);

    SimpleResponse updatePayment(PaymentRequest paymentRequest);
}

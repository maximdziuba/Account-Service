package account.service.impl;

import account.dto.payment.PaymentResponse;
import account.mapper.UserMapper;
import account.model.User;
import account.repository.UserRepository;
import account.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public PaymentResponse getPayment(User user) {
        var paymentDto = UserMapper.entityToPaymentDto(user);
        return paymentDto;
    }
}

package account.mapper;

import account.dto.payment.PaymentRequest;
import account.dto.payment.PaymentResponse;
import account.exception.UserNotFoundException;
import account.model.Payment;
import account.model.User;
import account.service.UserService;
import account.utils.CurrencyUtil;
import account.utils.DateUtil;
import account.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PaymentMapper {

    private final UserService userService;
    private final MessageUtil messageUtil;

    public Payment toEntity(PaymentRequest paymentRequest) {
        var employee = userService.findByEmail(paymentRequest.getEmployeeEmail()).orElseThrow(() ->
                new UserNotFoundException(messageUtil.getMessageByCode("user.notFound")));
        return Payment.builder()
                .employee(employee)
                .period(paymentRequest.getPeriod())
                .salary(paymentRequest.getSalary())
                .build();
    }

    public List<Payment> toEntityList(List<PaymentRequest> paymentRequests) {
        return paymentRequests.stream()
                .map(this::toEntity)
                .toList();
    }

    public PaymentResponse toDto(Payment payment, User user) {
        var salary = CurrencyUtil.generateCurrencyString(payment.getSalary());
        var period = DateUtil.generatePeriodString(payment.getPeriod());
        return new PaymentResponse(
                user.getName(),
                user.getLastname(),
                period,
                salary
        );
    }

    public List<PaymentResponse> toDtoList(List<Payment> payments, User user) {
        List<PaymentResponse> responses = new ArrayList<>();
        payments.forEach(payment -> responses.add(this.toDto(payment, user)));
        return responses;
    }
}

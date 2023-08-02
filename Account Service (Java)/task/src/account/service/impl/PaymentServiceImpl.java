package account.service.impl;

import account.dto.SimpleResponse;
import account.dto.payment.PaymentRequest;
import account.dto.payment.PaymentResponse;
import account.exception.PaymentSavingException;
import account.exception.PeriodNotFoundException;
import account.exception.UserNotFoundException;
import account.mapper.PaymentMapper;
import account.model.User;
import account.repository.PaymentRepository;
import account.service.PaymentService;
import account.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final MessageUtil messageUtil;

    @Override
    public List<PaymentResponse> getPayment(User user) {
        var payment = paymentRepository.findPaymentsByEmployee_EmailOrderByPeriodDesc(user.getEmail());
        return paymentMapper.toDtoList(payment, user);
    }

    @Override
    public PaymentResponse getPayment(User user, String period) {
        var payment = paymentRepository.findPaymentByEmployee_EmailAndPeriod(user.getEmail(), period)
                .orElseThrow(() -> new PeriodNotFoundException(messageUtil.getMessageByCode("error")));
        return paymentMapper.toDto(payment, user);
    }

    @Override
    @Transactional
    public SimpleResponse uploadPayrolls(List<PaymentRequest> payments) {
        var entities = paymentMapper.toEntityList(payments);
        entities.forEach(payment -> {
            var email = payment.getEmployee().getEmail();
            var period = payment.getPeriod();
            if (paymentRepository.findPaymentByEmployee_EmailAndPeriod(email, period).isPresent()) {
                throw new PaymentSavingException(messageUtil.getMessageByCode("period.unique"));
            }
        });
        paymentRepository.saveAll(entities);
        return new SimpleResponse(messageUtil.getMessageByCode("saved"));
    }

    @Override
    @Transactional
    public SimpleResponse updatePayment(PaymentRequest paymentRequest) {
        var paymentFromDb = paymentRepository.findPaymentByEmployee_EmailIgnoreCaseAndPeriod(
                paymentRequest.getEmployeeEmail(),
                paymentRequest.getPeriod()
                ).orElseThrow(() ->
                new UserNotFoundException(messageUtil.getMessageByCode("user.notFound")));
        paymentFromDb.setPeriod(paymentRequest.getPeriod());
        paymentFromDb.setSalary(paymentRequest.getSalary());
        paymentRepository.save(paymentFromDb);
        return new SimpleResponse(messageUtil.getMessageByCode("updated"));
    }
}

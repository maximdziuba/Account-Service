package account.repository;

import account.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findPaymentsByEmployee_EmailOrderByPeriodDesc(String employeeEmail);

    Optional<Payment> findPaymentByEmployee_Email(String employeeEmail);

    Optional<Payment> findPaymentByEmployee_EmailIgnoreCase(String employeeEmail);
    Optional<Payment> findPaymentByEmployee_EmailIgnoreCaseAndPeriod(String employeeEmail, String period);

    Optional<Payment> findPaymentByEmployee_EmailAndPeriod(String employeeEmail, String period);

}

package account.controller;

import account.dto.SimpleResponse;
import account.dto.payment.PaymentRequest;
import account.dto.payment.PaymentResponse;
import account.model.User;
import account.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/empl/payment")
    public ResponseEntity getPayment(@RequestParam(name = "period", required = false) String period,
                                                            @AuthenticationPrincipal User user) {
        if (period == null) {
            var paymentResponseList = paymentService.getPayment(user);
            return new ResponseEntity<>(paymentResponseList, HttpStatus.OK);
        } else {
            var paymentResponse = paymentService.getPayment(user, period);
            return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
        }
    }

//    @GetMapping("/empl/payment")
//    public ResponseEntity<PaymentResponse> getPayment(@RequestParam(name = "period") String period,
//                                                      @AuthenticationPrincipal User user) {
//        var paymentDto = paymentService.getPayment(user, period);
//        return new ResponseEntity<>(paymentDto, HttpStatus.OK);
//    }

    @PostMapping("/acct/payments")
    public ResponseEntity<SimpleResponse> uploadPayrolls(@Valid @RequestBody List<PaymentRequest> payments) {
        var response = paymentService.uploadPayrolls(payments);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/acct/payments")
    public ResponseEntity<SimpleResponse> changeSalary(@Valid @RequestBody PaymentRequest paymentRequest) {
        var response = paymentService.updatePayment(paymentRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}

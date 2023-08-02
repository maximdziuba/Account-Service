package account.dto.payment;

import account.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {


    @JsonProperty("employee")
    private String employeeEmail;

    @NotNull
    private String period;

    @NotNull
    private Long salary;

    @AssertTrue(message = "Salary can't be negative")
    public boolean isSalaryValidated() {
        return this.salary != null && this.salary >= 0;
    }

    @AssertTrue(message = "Period is not valid")
    public boolean isPeriodValidated() {
        int month = DateUtil.getMonthNumber(this.period);
        return this.period != null && month <= 12 && month > 0;
    }
}

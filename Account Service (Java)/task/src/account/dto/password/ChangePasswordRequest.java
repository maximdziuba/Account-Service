package account.dto.password;

import account.utils.LeakedPasswords;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//public record ChangePasswordRequest(
////        @Size(min = 12, message = "{newPassword.length}")  @NotNull(message = "{newPassword.null}") String newPassword) {
//         @NotNull(message = "Password length must be 12 chars minimum!") String newPassword) {
//
//    @AssertFalse(message = "The password is in the hacker's database!")
//    public boolean isLeaked() {
//        return this.newPassword != null && LeakedPasswords.contains(this.newPassword);
//    }
//
//    @AssertTrue(message = "Password length must be 12 chars minimum!")
//    public boolean hasValidSize() {
//        return this.newPassword != null && this.newPassword.length() >= 12;
//    }
//}

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {

    @JsonProperty("new_password")
    @NotNull
    private String password;

    @AssertFalse(message = "The password is in the hacker's database!")
    public boolean isBreached() {
        return this.password != null && LeakedPasswords.contains(this.password);
    }

    @AssertTrue(message = "Password length must be 12 chars minimum!")
    public boolean hasValidLength() {
        return this.password != null && this.password.length() >= 12;
    }

}

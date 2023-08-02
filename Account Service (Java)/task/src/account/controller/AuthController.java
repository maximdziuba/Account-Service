package account.controller;

import account.dto.auth.UserRequest;
import account.dto.auth.UserResponse;
import account.dto.password.ChangePasswordRequest;
import account.dto.password.ChangePasswordResponse;
import account.model.User;
import account.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUp(@Valid @RequestBody UserRequest authRequest) {
        var response = userService.saveUser(authRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/changepass")
    public ResponseEntity<ChangePasswordResponse> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest,
                                                                 @AuthenticationPrincipal User user) {
        var response = userService.changeUsersPassword(changePasswordRequest, user);
        return ResponseEntity.ok(response);
    }
}

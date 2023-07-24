package account.controller;

import account.dto.auth.AuthRequest;
import account.dto.auth.AuthResponse;
import account.dto.password.ChangePasswordRequest;
import account.dto.password.ChangePasswordResponse;
import account.exception.PasswordUpdateException;
import account.model.User;
import account.service.UserService;
import account.utils.MessageUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final MessageUtil messageUtil;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder, MessageUtil messageUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.messageUtil = messageUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUp(@Valid @RequestBody AuthRequest authRequest) {
        var response = userService.saveUser(authRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/changepass")
    public ResponseEntity<ChangePasswordResponse> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest,
                                                                 @AuthenticationPrincipal User user) {
//        if (passwordEncoder.matches(changePasswordRequest.getPassword(), user.getPassword())) {
//            throw new PasswordUpdateException(messageUtil.getMessageByCode("password.different"));
//        }
        var response = userService.changeUsersPassword(changePasswordRequest, user);
        return ResponseEntity.ok(response);
    }
}

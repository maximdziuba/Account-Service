package account.controller;

import account.dto.auth.AuthRequest;
import account.exception.ExceptionBody;
import account.exception.UserExistsException;
import account.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity signUp(@Valid @RequestBody AuthRequest authRequest) {
        if (userService.findByEmail(authRequest.email()).isPresent()) {
//            var exceptionBody = new ExceptionBody(
//                    LocalDateTime.now(),
//                    HttpStatus.BAD_REQUEST.value(),
//                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
//                    "/api/auth/signup"
//            );
//            return new ResponseEntity<>(exceptionBody, HttpStatus.BAD_REQUEST);
            throw new UserExistsException("User exist!");
        }
        return ResponseEntity.ok(userService.saveUser(authRequest));
    }
}

package account.service;

import account.dto.auth.AuthRequest;
import account.dto.auth.AuthResponse;
import account.dto.password.ChangePasswordRequest;
import account.dto.password.ChangePasswordResponse;
import account.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    AuthResponse saveUser(AuthRequest authRequest);

    Optional<User> findByEmail(String email);

    List<AuthResponse> findAll();

    ChangePasswordResponse changeUsersPassword(ChangePasswordRequest changePasswordRequest, User user);
}

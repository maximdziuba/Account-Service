package account.service;

import account.dto.auth.UserRequest;
import account.dto.auth.UserResponse;
import account.dto.password.ChangePasswordRequest;
import account.dto.password.ChangePasswordResponse;
import account.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserResponse saveUser(UserRequest authRequest);

    Optional<User> findByEmail(String email);

    List<UserResponse> findAll();

    ChangePasswordResponse changeUsersPassword(ChangePasswordRequest changePasswordRequest, User user);
}

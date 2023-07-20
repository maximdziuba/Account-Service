package account.service;

import account.dto.auth.AuthRequest;
import account.dto.auth.AuthResponse;
import account.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    AuthResponse saveUser(AuthRequest authRequest);

    Optional<User> findByEmail(String email);

    List<AuthResponse> findAll();
}

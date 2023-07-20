package account.service.impl;

import account.dto.auth.AuthRequest;
import account.dto.auth.AuthResponse;
import account.exception.UserExistsException;
import account.mapper.UserMapper;
import account.model.User;
import account.repository.UserRepository;
import account.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public AuthResponse saveUser(AuthRequest authRequest) {
        var user = new User();
        user.setEmail(authRequest.email());
        user.setName(authRequest.name());
        user.setLastname(authRequest.lastname());
        user.setPassword(passwordEncoder.encode(authRequest.password()));
        var saved = userRepository.save(user);
        var response = new AuthResponse(saved.getId(), authRequest.name(), authRequest.lastname(), authRequest.email());
        return response;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findUserByEmailIgnoreCase(email);
    }

    @Override
    public List<AuthResponse> findAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::entityToDto)
                .toList();
    }
}

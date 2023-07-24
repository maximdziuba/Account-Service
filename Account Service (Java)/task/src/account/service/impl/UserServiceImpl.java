package account.service.impl;

import account.dto.auth.AuthRequest;
import account.dto.auth.AuthResponse;
import account.dto.password.ChangePasswordRequest;
import account.dto.password.ChangePasswordResponse;
import account.exception.PasswordUpdateException;
import account.exception.RegistrationException;
import account.exception.UserExistsException;
import account.mapper.UserMapper;
import account.model.User;
import account.repository.UserRepository;
import account.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import account.utils.MessageUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final String[] leakedPasswords =  {"PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch", "PasswordForApril",
            "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
            "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember"};

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageUtil messageUtil;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, MessageUtil messageUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.messageUtil = messageUtil;
    }

    @Override
    @Transactional
    public AuthResponse saveUser(AuthRequest authRequest) {
        if (Arrays.stream(leakedPasswords).anyMatch(pass -> pass.equals(authRequest.password()))) {
            throw new RegistrationException(messageUtil.getMessageByCode("password.leaked"));
        }
        if (this.findByEmail(authRequest.email()).isPresent()) {
            throw new UserExistsException(messageUtil.getMessageByCode("user.exist"));
        }
        var user = new User();
        user.setEmail(authRequest.email());
        user.setName(authRequest.name());
        user.setLastname(authRequest.lastname());
        user.setPassword(passwordEncoder.encode(authRequest.password()));
        var saved = userRepository.save(user);
        return new AuthResponse(saved.getId(), authRequest.name(), authRequest.lastname(), authRequest.email());
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

    @Override
    @Transactional
    public ChangePasswordResponse changeUsersPassword(ChangePasswordRequest changePasswordRequest, User user) {
        if (passwordEncoder.matches(changePasswordRequest.getPassword(), user.getPassword())) {
            throw new PasswordUpdateException(messageUtil.getMessageByCode("password.different"));
        }
        if (Arrays.stream(leakedPasswords).anyMatch(pass -> pass.equals(changePasswordRequest.getPassword()))) {
            throw new PasswordUpdateException(messageUtil.getMessageByCode("password.leaked"));
        }
        var newPass = passwordEncoder.encode(changePasswordRequest.getPassword());
        user.setPassword(newPass);
        userRepository.saveAndFlush(user);
        return new ChangePasswordResponse(user.getEmail().toLowerCase(), messageUtil.getMessageByCode("password.updated"));
    }
}

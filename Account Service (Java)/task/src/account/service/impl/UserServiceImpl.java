package account.service.impl;

import account.dto.auth.UserRequest;
import account.dto.auth.UserResponse;
import account.dto.password.ChangePasswordRequest;
import account.dto.password.ChangePasswordResponse;
import account.exception.PasswordUpdateException;
import account.exception.RegistrationException;
import account.exception.UserExistsException;
import account.mapper.UserMapper;
import account.model.User;
import account.repository.UserRepository;
import account.service.UserService;
import account.utils.LeakedPasswords;
import account.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageUtil messageUtil;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponse saveUser(UserRequest authRequest) {
        if (LeakedPasswords.contains(authRequest.password())) {
            throw new RegistrationException(messageUtil.getMessageByCode("password.leaked"));
        }
        if (this.findByEmail(authRequest.email()).isPresent()) {
            throw new UserExistsException(messageUtil.getMessageByCode("user.exist"));
        }
        var user = userMapper.toEntity(authRequest);
        var saved = userRepository.save(user);
        return new UserResponse(saved.getId(), authRequest.name(), authRequest.lastname(), authRequest.email());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findUserByEmailIgnoreCase(email);
    }

    @Override
    public List<UserResponse> findAll() {
        var entities = userRepository.findAll();
        return userMapper.toDtoList(entities);
    }

    @Override
    @Transactional
    public ChangePasswordResponse changeUsersPassword(ChangePasswordRequest changePasswordRequest, User user) {
        var newPassword = changePasswordRequest.getPassword();
        if (passwordEncoder.matches(changePasswordRequest.getPassword(), user.getPassword())) {
            throw new PasswordUpdateException(messageUtil.getMessageByCode("password.different"));
        }
        if (LeakedPasswords.contains(newPassword)) {
            throw new PasswordUpdateException(messageUtil.getMessageByCode("password.leaked"));
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.saveAndFlush(user);
        return new ChangePasswordResponse(user.getEmail().toLowerCase(), messageUtil.getMessageByCode("password.updated"));
    }
}

package com.pet.stock_price_tracker.service.user.impl;

import com.pet.stock_price_tracker.config.MailConfig;
import com.pet.stock_price_tracker.constants.ExceptionMessage;
import com.pet.stock_price_tracker.dto.security.JwtDTO;
import com.pet.stock_price_tracker.dto.user.login.UserLoginDTO;
import com.pet.stock_price_tracker.dto.user.login.UserResponseLoginDTO;
import com.pet.stock_price_tracker.dto.user.registration.UserDTO;
import com.pet.stock_price_tracker.dto.user.registration.UserResponseDTO;
import com.pet.stock_price_tracker.dto.user.restore.RestorePasswordDTO;
import com.pet.stock_price_tracker.dto.user.restore.RestorePasswordResponseDTO;
import com.pet.stock_price_tracker.entity.User;
import com.pet.stock_price_tracker.enums.Roles;
import com.pet.stock_price_tracker.exception.UserNotFoundException;
import com.pet.stock_price_tracker.repository.UserRepository;
import com.pet.stock_price_tracker.service.user.roles.RoleService;
import com.pet.stock_price_tracker.service.security.jwt.JwtService;
import com.pet.stock_price_tracker.service.user.UserService;
import com.pet.stock_price_tracker.service.utils.mapping.UserMapper;
import com.pet.stock_price_tracker.service.validation.manager.impl.UserLoginValidationManager;
import com.pet.stock_price_tracker.service.validation.manager.impl.UserRegistrationValidationManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRegistrationValidationManager userRegistrationValidationManager;
    private final UserLoginValidationManager userLoginValidationManager;
    private final JwtService jwtService;
    private final RoleService roleService;
    private final JavaMailSender mailSender;
    private final MailConfig mailConfig;

    public UserServiceImpl(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            UserRegistrationValidationManager userRegistrationValidationManager,
            UserLoginValidationManager userLoginValidationManager,
            JwtService jwtService,
            RoleService roleService,
            JavaMailSender mailSender,
            MailConfig mailConfig
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRegistrationValidationManager = userRegistrationValidationManager;
        this.userLoginValidationManager = userLoginValidationManager;
        this.jwtService = jwtService;
        this.roleService = roleService;
        this.mailSender = mailSender;
        this.mailConfig = mailConfig;
    }

    @Override
    public UserResponseDTO createUser(UserDTO userDTO) {

        userRegistrationValidationManager.validate(userDTO);

        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        roleService.createRoleForUser(user, Roles.USER);

        User savedUser = userRepository.save(user);

        return userMapper.toDTO(savedUser);
    }

    @Override
    public UserResponseLoginDTO login(UserLoginDTO userLoginDTO) {
        userLoginValidationManager.validate(userLoginDTO);

        User foundUser = findUserEntityByLogin(userLoginDTO.getLogin());

        JwtDTO authentication = new JwtDTO(
                jwtService.generateAccessToken(foundUser.getLogin()),
                jwtService.generateRefreshToken(foundUser.getLogin())
        );

        return new UserResponseLoginDTO(
                userMapper.toDTO(foundUser),
                authentication
        );
    }


    @Override
    public UserResponseDTO findUserByLogin(String login) {
        Optional<User> foundUser = userRepository.findUserByLogin(login);

        return userMapper.toDTO(foundUser.orElseThrow(() -> new UserNotFoundException(login)));
    }

    @Override
    public JwtDTO refreshToken(String refreshToken) {
        return new JwtDTO(jwtService.refreshAccessToken(refreshToken), refreshToken);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.getAllUsers().stream().map(userMapper::toDTO).toList();
    }

    @Override
    public User findUserEntityByLogin(String login) {
        Optional<User> foundUser = userRepository.findUserByLogin(login);

        return foundUser.orElseThrow(() -> new UserNotFoundException(ExceptionMessage.USER_NOT_FOUND_EXCEPTION));
    }

    @Override
    public User findUserEntityByEmail(String email) {
        Optional<User> user = userRepository.findUserByEmail(email);

        return user.orElseThrow(() -> new UserNotFoundException(ExceptionMessage.USER_NOT_FOUND_EXCEPTION));
    }

    @Override
    public void restorePassword(RestorePasswordDTO restorePasswordDTO) {
        User user = findUserEntityByEmail(restorePasswordDTO.getEmail());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@gmail.com");
        message.setTo(user.getEmail());
        message.setSubject("Restore Password");
        message.setText("text");

        mailConfig.getJavaMailSender().send(message);
    }
}

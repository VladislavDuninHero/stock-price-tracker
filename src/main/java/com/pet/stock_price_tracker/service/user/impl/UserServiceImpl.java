package com.pet.stock_price_tracker.service.user.impl;

import com.pet.stock_price_tracker.config.MailConfig;
import com.pet.stock_price_tracker.constants.ExceptionMessage;
import com.pet.stock_price_tracker.constants.MailConstant;
import com.pet.stock_price_tracker.constants.Message;
import com.pet.stock_price_tracker.controller.MainController;
import com.pet.stock_price_tracker.dto.security.JwtDTO;
import com.pet.stock_price_tracker.dto.user.info.UserInfoDTO;
import com.pet.stock_price_tracker.dto.user.login.UserLoginDTO;
import com.pet.stock_price_tracker.dto.user.login.UserResponseLoginDTO;
import com.pet.stock_price_tracker.dto.user.registration.UserDTO;
import com.pet.stock_price_tracker.dto.user.registration.UserResponseDTO;
import com.pet.stock_price_tracker.dto.user.restore.RestorePasswordDTO;
import com.pet.stock_price_tracker.dto.user.restore.RestorePasswordResponseDTO;
import com.pet.stock_price_tracker.dto.user.restore.UpdateRestorePasswordDTO;
import com.pet.stock_price_tracker.entity.User;
import com.pet.stock_price_tracker.enums.Roles;
import com.pet.stock_price_tracker.exception.UserNotFoundException;
import com.pet.stock_price_tracker.repository.UserRepository;
import com.pet.stock_price_tracker.service.user.roles.RoleService;
import com.pet.stock_price_tracker.service.security.jwt.JwtService;
import com.pet.stock_price_tracker.service.user.UserService;
import com.pet.stock_price_tracker.service.utils.mapping.UserMapper;
import com.pet.stock_price_tracker.service.utils.uri.restore.RestorePasswordService;
import com.pet.stock_price_tracker.service.validation.manager.impl.UserLoginValidationManager;
import com.pet.stock_price_tracker.service.validation.manager.impl.UserRegistrationValidationManager;
import io.jsonwebtoken.Claims;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;
import org.springframework.http.HttpHeaders;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URI;
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
    private final RestorePasswordService restorePasswordService;

    public UserServiceImpl(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            UserRegistrationValidationManager userRegistrationValidationManager,
            UserLoginValidationManager userLoginValidationManager,
            JwtService jwtService,
            RoleService roleService,
            JavaMailSender mailSender,
            MailConfig mailConfig,
            RestorePasswordService restorePasswordService
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
        this.restorePasswordService = restorePasswordService;
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
    public RestorePasswordResponseDTO restorePassword(RestorePasswordDTO restorePasswordDTO) {
        User user = findUserEntityByEmail(restorePasswordDTO.getEmail());

        URI restorePasswordUri = restorePasswordService.generateRestorePasswordUri(user.getLogin());

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            mimeMessage.addHeader(HttpHeaders.CONTENT_TYPE, "text/html; charset=utf-8");
            mimeMessage.setContent(
                    String.format(MailConstant.RESTORE_PASSWORD_HTML_CONTENT, restorePasswordUri),
                    "text/html; charset=utf-8"
            );
            mimeMessage.setSubject(
                    MimeUtility.encodeText(
                            MailConstant.RESTORE_PASSWORD_SUBJECT,
                            "UTF-8", "8"
                    )
            );

            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setFrom("noreply@gmail.com");
            message.setTo(user.getEmail());

            mailConfig.getJavaMailSender().send(mimeMessage);

            return new RestorePasswordResponseDTO(Message.SUCCESS_MESSAGE, restorePasswordUri);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void updateUserPasswordByLogin(UpdateRestorePasswordDTO updateRestorePasswordDTO, String token) {
        String login = jwtService.getLoginFromToken(token);

        User user = findUserEntityByLogin(login);

        user.setPassword(
                passwordEncoder.encode(updateRestorePasswordDTO.getNewPassword())
        );

        userRepository.save(user);
    }

    @Override
    public UserInfoDTO getUserInfo(String login) {
        return null;
    }
}

package me.nolanjames.booknetworkapi.auth;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import me.nolanjames.booknetworkapi.email.EmailService;
import me.nolanjames.booknetworkapi.email.EmailTemplateName;
import me.nolanjames.booknetworkapi.role.RoleRepository;
import me.nolanjames.booknetworkapi.role.RoleService;
import me.nolanjames.booknetworkapi.user.Token;
import me.nolanjames.booknetworkapi.user.TokenService;
import me.nolanjames.booknetworkapi.user.User;
import me.nolanjames.booknetworkapi.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private static final int ACTIVATION_CODE_LENGTH = 6;
    private static final int ACTIVATION_CODE_EXPIRE_MINUTES = 15;
    private static final String ACTIVATION_CODE_CHARS = "123456789";
    private static final String ACTIVATION_EMAIL_SUBJECT = "Account Activation";

    private final RoleService roleService;
    private final UserService userService;
    private final TokenService tokenService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Value("${application.mail.frontend.activation-url")
    private String activationUrl;

    public void register(RegistrationRequest request) throws MessagingException {
        var userRole = roleService.getUserRole("USER");
        var user = User.builder()
                .firstName(request.firstname())
                .lastName(request.lastname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        userService.saveUser(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateActivationToken(user);

        emailService.sendEmail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                ACTIVATION_EMAIL_SUBJECT
        );

    }

    private String generateActivationToken(User user) {
        String generatedToken = generateActivationCode(ACTIVATION_CODE_LENGTH);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(ACTIVATION_CODE_EXPIRE_MINUTES))
                .user(user)
                .build();
        tokenService.saveToken(token);

        return generatedToken;
    }

    private String generateActivationCode(int length) {
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(ACTIVATION_CODE_CHARS.length());
            codeBuilder.append(ACTIVATION_CODE_CHARS.charAt(randomIndex));
        }

        return codeBuilder.toString();
    }
}

package me.nolanjames.booknetworkapi.auth;

import lombok.RequiredArgsConstructor;
import me.nolanjames.booknetworkapi.role.RoleRepository;
import me.nolanjames.booknetworkapi.role.RoleService;
import me.nolanjames.booknetworkapi.user.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public void register(RegistrationRequest request) {
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
    }
}

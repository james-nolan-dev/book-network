package me.nolanjames.booknetworkapi.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getUserRole(String userRole) {
        // todo - improve exception handling
        return roleRepository.findByName(userRole).orElseThrow(
                () -> new IllegalStateException("Role USER was not initialised"));
    }
}

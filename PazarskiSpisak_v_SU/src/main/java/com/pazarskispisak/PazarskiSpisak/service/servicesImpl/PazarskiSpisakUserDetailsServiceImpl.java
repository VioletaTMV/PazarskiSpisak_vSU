package com.pazarskispisak.PazarskiSpisak.service.servicesImpl;

import com.pazarskispisak.PazarskiSpisak.models.entities.User;
import com.pazarskispisak.PazarskiSpisak.models.entities.UserRoleEntity;
import com.pazarskispisak.PazarskiSpisak.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class PazarskiSpisakUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public PazarskiSpisakUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserDetails userDetails = this.userRepository.findByEmail(email)
                .map(this::mapUserEntityToUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found!"));

        return userDetails;
    }

    private UserDetails mapUserEntityToUserDetails(User user) {
        UserDetails ud = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getUserRoles()
                        .stream()
                        .map(userRoleEntity -> mapUserRoleToUserDetailsGrantedAuthority(userRoleEntity))
                        .toList())
                .build();

        return ud;
    }

    private GrantedAuthority mapUserRoleToUserDetailsGrantedAuthority(UserRoleEntity userRoleEntity) {

        return new SimpleGrantedAuthority("ROLE_" + userRoleEntity.getUserRole().name());

    }
}

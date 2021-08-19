package com.github.slamdev.oldschool.business.control;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserService {

    private static final GrantedAuthority USER_ROLE = new SimpleGrantedAuthority("ROLE_USER");

    private final UserDetailsManager userDetailsManager;

    private final PasswordEncoder encoder;

    public HttpStatus createUser(String email, String password) {
        if (userDetailsManager.userExists(email)) {
            return HttpStatus.CONFLICT;
        }
        UserDetails user = User.builder()
                .username(email)
                .passwordEncoder(encoder::encode)
                .password(password)
                .authorities(USER_ROLE)
                .build();
        userDetailsManager.createUser(user);
        return HttpStatus.OK;
    }
}

package com.github.slamdev.oldschool.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

import javax.sql.DataSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(DataSource dataSource) {
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.userDetailsService = new JdbcUserDetailsManager(dataSource);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(r -> r.anyRequest().permitAll())
                .formLogin(f -> f
                        .loginPage("/login")
                        .usernameParameter("email")
                )
                .logout(l -> l.logoutSuccessUrl("/"))
                .rememberMe(r -> {
                            SpringSessionRememberMeServices svc = new SpringSessionRememberMeServices();
                            svc.setAlwaysRemember(true);
                            r.rememberMeServices(svc).alwaysRemember(true);
                        }
                );
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return passwordEncoder;
    }
}

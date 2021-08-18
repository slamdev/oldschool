package com.github.slamdev.oldschool.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfig {

    @RequiredArgsConstructor
    static class Adapter extends WebSecurityConfigurerAdapter {

        private final UserDetailsService userDetailsService;

        @Override
        public void configure(WebSecurity web) {
//        web.ignoring()
//                // Spring Security should completely ignore URLs starting with /resources/
//                .antMatchers("/resources/**");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests(r -> r.anyRequest().permitAll())
                    .formLogin(f -> f
                            .loginPage("/login")
                            .defaultSuccessUrl("/")
                            .usernameParameter("email")
                    )
                    .logout(l -> l.logoutSuccessUrl("/"))
                    .rememberMe();
        }

        @Override
        protected UserDetailsService userDetailsService() {
            return userDetailsService;
        }
    }

    @Bean
    public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter(UserDetailsService userDetailsService) {
        return new Adapter(userDetailsService);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user@gmail.com")
                .password("user")
                .authorities(Collections.emptyList())
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}

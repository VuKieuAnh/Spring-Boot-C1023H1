package com.example.demo.config;

import com.example.demo.service.appuser.AppUserService;
import com.example.demo.service.appuser.IAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.function.Function;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class AppSecConfig  {
    @Autowired
    private AppUserService userService;

//    @Bean
//    public static PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((authorize) -> {
            authorize
                    .requestMatchers("/home").permitAll()
                    .requestMatchers("/user").hasRole("USER")
                    .requestMatchers("/admin").hasRole("ADMIN")
            ;
        }).formLogin(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
//        authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
//    @Bean
//    public UserDetailsService userDetailsService() {

//        UserDetails user = User.builder().username("user").password(passwordEncoder().encode("123456")).roles("USER")
//                .build();
//
//        UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user,admin);
//    }
}

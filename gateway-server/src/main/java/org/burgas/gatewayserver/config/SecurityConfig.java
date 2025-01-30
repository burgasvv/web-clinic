package org.burgas.gatewayserver.config;

import org.burgas.gatewayserver.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic(Customizer.withDefaults())
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationManager(authenticationManager())
                .authorizeHttpRequests(
                        requests -> requests

                                .requestMatchers(
                                        "/filials", "/filials/{filial-id}", "/positions", "/positions/{position-id}",
                                        "/departments", "/departments/{department-id}", "/departments/in-filial/{filial-id}",
                                        "/identities/create", "/authentication/principal",
                                        "/employees", "/employees/{employee-id}"
                                )
                                .permitAll()

                                .requestMatchers(
                                        "/identities/**", "/authorities/**", "/patients/**",
                                        "/appointments/**","/documents/**","/conclusions/**"
                                )
                                .hasAnyAuthority("ADMIN", "USER", "EMPLOYEE")

                                .requestMatchers(
                                        "/employees/create", "/employees/update", "/employees/delete"
                                )
                                .hasAnyAuthority("ADMIN", "EMPLOYEE")

                                .requestMatchers(
                                        "/positions/create", "/positions/update",
                                        "/filials/create", "/filials/update", "/filials/delete",
                                        "/departments/create", "/departments/update", "/departments/delete"
                                )
                                .hasAnyAuthority("ADMIN")
                )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

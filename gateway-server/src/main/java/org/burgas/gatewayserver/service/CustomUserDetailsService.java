package org.burgas.gatewayserver.service;

import org.burgas.gatewayserver.handler.RestClientHandler;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final RestClientHandler restClientHandler;

    public CustomUserDetailsService(RestClientHandler restClientHandler) {
        this.restClientHandler = restClientHandler;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return restClientHandler.getIdentityByEmail(username).getBody();
    }
}

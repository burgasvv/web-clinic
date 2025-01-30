package org.burgas.gatewayserver.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class IdentityResponse implements UserDetails {

    private Long id;
    private String email;
    private String phone;
    private String password;
    private String created;
    private AuthorityResponse authority;

    public IdentityResponse() {
    }

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public String getEmail() {
        return email;
    }

    @SuppressWarnings("unused")
    public String getPhone() {
        return phone;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority(authority.getAuthority())
        );
    }

    @SuppressWarnings("unused")
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @SuppressWarnings("unused")
    public String getCreated() {
        return created;
    }

    @SuppressWarnings("unused")
    public AuthorityResponse getAuthority() {
        return authority;
    }

    @SuppressWarnings("unused")
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final IdentityResponse identity;

        public Builder() {
            identity = new IdentityResponse();
        }

        @SuppressWarnings("unused")
        public Builder id(Long id) {
            this.identity.id = id;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder email(String email) {
            this.identity.email = email;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder phone(String phone) {
            this.identity.phone = phone;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder password(String password) {
            this.identity.password = password;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder created(String created) {
            this.identity.created = created;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder authority(AuthorityResponse authority) {
            this.identity.authority = authority;
            return this;
        }

        public IdentityResponse build() {
            return identity;
        }
    }
}

package org.burgas.gatewayserver.dto;

import org.springframework.security.core.GrantedAuthority;

public class AuthorityResponse implements GrantedAuthority {

    private Long id;
    private String name;

    public AuthorityResponse() {
    }

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String getAuthority() {
        return name;
    }

    public static class Builder  {

        private final AuthorityResponse authority;

        public Builder() {
            authority = new AuthorityResponse();
        }

        @SuppressWarnings("unused")
        public Builder id(Long id) {
            this.authority.id = id;
            return this;
        }

        public Builder name(String name) {
            this.authority.name = name;
            return this;
        }

        public AuthorityResponse build() {
            return authority;
        }
    }
}

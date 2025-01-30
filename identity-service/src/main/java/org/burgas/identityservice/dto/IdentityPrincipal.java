package org.burgas.identityservice.dto;

public class IdentityPrincipal {

    private Long id;
    private String email;
    private String authority;
    private Boolean authenticated;

    public IdentityPrincipal() {
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
    public String getAuthority() {
        return authority;
    }

    @SuppressWarnings("unused")
    public Boolean getAuthenticated() {
        return authenticated;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final IdentityPrincipal identityPrincipal;

        public Builder() {
            identityPrincipal = new IdentityPrincipal();
        }

        public Builder id(Long id) {
            this.identityPrincipal.id = id;
            return this;
        }

        public Builder email(String email) {
            this.identityPrincipal.email = email;
            return this;
        }

        public Builder authority(String authority) {
            this.identityPrincipal.authority = authority;
            return this;
        }

        public Builder authenticated(Boolean authenticated) {
            this.identityPrincipal.authenticated = authenticated;
            return this;
        }

        public IdentityPrincipal build() {
            return identityPrincipal;
        }
    }
}

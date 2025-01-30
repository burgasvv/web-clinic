package org.burgas.identityservice.dto;

public class IdentityResponse {

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

    @SuppressWarnings("unused")
    public String getPassword() {
        return password;
    }

    @SuppressWarnings("unused")
    public String getCreated() {
        return created;
    }

    @SuppressWarnings("unused")
    public AuthorityResponse getAuthority() {
        return authority;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final IdentityResponse identity;

        public Builder() {
            identity = new IdentityResponse();
        }

        public Builder id(Long id) {
            this.identity.id = id;
            return this;
        }

        public Builder email(String email) {
            this.identity.email = email;
            return this;
        }

        public Builder phone(String phone) {
            this.identity.phone = phone;
            return this;
        }

        public Builder password(String password) {
            this.identity.password = password;
            return this;
        }

        public Builder created(String created) {
            this.identity.created = created;
            return this;
        }

        public Builder authority(AuthorityResponse authority) {
            this.identity.authority = authority;
            return this;
        }

        public IdentityResponse build() {
            return identity;
        }
    }
}

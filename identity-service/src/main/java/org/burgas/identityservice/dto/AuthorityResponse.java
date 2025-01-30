package org.burgas.identityservice.dto;

public class AuthorityResponse {

    private Long id;
    private String name;

    public AuthorityResponse() {
    }

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final AuthorityResponse authority;

        public Builder() {
            authority = new AuthorityResponse();
        }

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

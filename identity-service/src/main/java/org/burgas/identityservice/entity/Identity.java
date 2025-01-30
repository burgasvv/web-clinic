package org.burgas.identityservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Identity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String phone;
    private String password;
    private LocalDateTime created;
    private Long authorityId;

    public Identity() {
    }

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public String getEmail() {
        return email;
    }

    @SuppressWarnings("unused")
    public void setEmail(String email) {
        this.email = email;
    }

    @SuppressWarnings("unused")
    public String getPhone() {
        return phone;
    }

    @SuppressWarnings("unused")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @SuppressWarnings("unused")
    public String getPassword() {
        return password;
    }

    @SuppressWarnings("unused")
    public void setPassword(String password) {
        this.password = password;
    }

    @SuppressWarnings("unused")
    public LocalDateTime getCreated() {
        return created;
    }

    @SuppressWarnings("unused")
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @SuppressWarnings("unused")
    public Long getAuthorityId() {
        return authorityId;
    }

    @SuppressWarnings("unused")
    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Identity identity;

        public Builder() {
            identity = new Identity();
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

        public Builder created(LocalDateTime created) {
            this.identity.created = created;
            return this;
        }

        public Builder authorityId(Long authorityId) {
            this.identity.authorityId = authorityId;
            return this;
        }

        public Identity build() {
            return identity;
        }
    }
}

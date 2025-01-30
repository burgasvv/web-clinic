package org.burgas.identityservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Authority() {
    }

    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public void setName(String name) {
        this.name = name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Authority authority;

        public Builder() {
            authority = new Authority();
        }

        public Builder id(Long id) {
            this.authority.id = id;
            return this;
        }

        public Builder name(String name) {
            this.authority.name = name;
            return this;
        }

        public Authority build() {
            return authority;
        }
    }
}

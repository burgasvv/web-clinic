package org.burgas.departmentservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalTime;

@Entity
public class Filial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
    private LocalTime open;
    private LocalTime close;

    public Filial() {
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
    public String getAddress() {
        return address;
    }

    @SuppressWarnings("unused")
    public void setAddress(String address) {
        this.address = address;
    }

    @SuppressWarnings("unused")
    public LocalTime getOpen() {
        return open;
    }

    @SuppressWarnings("unused")
    public void setOpen(LocalTime open) {
        this.open = open;
    }

    @SuppressWarnings("unused")
    public LocalTime getClose() {
        return close;
    }

    @SuppressWarnings("unused")
    public void setClose(LocalTime close) {
        this.close = close;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Filial filial;

        public Builder() {
            filial = new Filial();
        }

        public Builder id(Long id) {
            this.filial.id = id;
            return this;
        }

        public Builder address(String address) {
            this.filial.address = address;
            return this;
        }

        public Builder open(LocalTime open) {
            this.filial.open = open;
            return this;
        }

        public Builder close(LocalTime close) {
            this.filial.close = close;
            return this;
        }

        public Filial build() {
            return filial;
        }
    }
}

package org.burgas.departmentservice.dto;

public class FilialResponse {

    private Long id;
    private String address;
    private String open;
    private String close;

    public FilialResponse() {
    }

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public String getAddress() {
        return address;
    }

    @SuppressWarnings("unused")
    public String getOpen() {
        return open;
    }

    @SuppressWarnings("unused")
    public String getClose() {
        return close;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final FilialResponse filial;

        public Builder() {
            filial = new FilialResponse();
        }

        public Builder id(Long id) {
            this.filial.id = id;
            return this;
        }

        public Builder address(String address) {
            this.filial.address = address;
            return this;
        }

        public Builder open(String open) {
            this.filial.open = open;
            return this;
        }

        public Builder close(String close) {
            this.filial.close = close;
            return this;
        }

        public FilialResponse build() {
            return filial;
        }
    }
}

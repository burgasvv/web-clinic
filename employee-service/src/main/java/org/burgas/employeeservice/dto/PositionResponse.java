package org.burgas.employeeservice.dto;

public class PositionResponse {

    private Long id;
    private String name;

    public PositionResponse() {
    }

    @SuppressWarnings("unused")
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

        private final PositionResponse position;

        public Builder() {
            position = new PositionResponse();
        }

        public Builder id(Long id) {
            this.position.id = id;
            return this;
        }

        public Builder name(String name) {
            this.position.name = name;
            return this;
        }

        public PositionResponse build() {
            return position;
        }
    }
}

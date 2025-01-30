package org.burgas.departmentservice.dto;

public class DepartmentResponse {

    private Long id;
    private String name;
    private String description;

    public DepartmentResponse() {
    }

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public String getDescription() {
        return description;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final DepartmentResponse department;

        public Builder() {
            department = new DepartmentResponse();
        }

        public Builder id(Long id) {
            this.department.id = id;
            return this;
        }

        public Builder name(String name) {
            this.department.name = name;
            return this;
        }

        public Builder description(String description) {
            this.department.description = description;
            return this;
        }

        public DepartmentResponse build() {
            return department;
        }
    }
}

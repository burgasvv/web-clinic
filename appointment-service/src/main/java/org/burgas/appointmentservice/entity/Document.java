package org.burgas.appointmentservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String filename;
    private byte[] data;

    public Document() {
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
    public String getFilename() {
        return filename;
    }

    @SuppressWarnings("unused")
    public void setFilename(String filename) {
        this.filename = filename;
    }

    @SuppressWarnings("unused")
    public byte[] getData() {
        return data;
    }

    @SuppressWarnings("unused")
    public void setData(byte[] data) {
        this.data = data;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Document document;

        public Builder() {
            document = new Document();
        }

        public Builder id(Long id) {
            this.document.id = id;
            return this;
        }

        public Builder filename(String filename) {
            this.document.filename = filename;
            return this;
        }

        public Builder data(byte[] data) {
            this.document.data = data;
            return this;
        }

        public Document build() {
            return document;
        }
    }
}

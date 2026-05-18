package com.ouadia.rovista1.dtos;

import java.time.LocalDateTime;

public class FavorieDto {

    private  Long id;
    private String description;
    private LocalDateTime dateCreation;

    public FavorieDto() {
    }

    public FavorieDto(Long id, String description, LocalDateTime dateCreation) {
        this.id = id;
        this.description = description;
        this.dateCreation = dateCreation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

}

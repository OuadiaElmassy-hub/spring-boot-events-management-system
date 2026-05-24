package com.ouadia.rovista1.dtos;

import com.ouadia.rovista1.entities.enums.TypeMessage;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class NotificationDto {
    private Long id;
    @NotNull
    private String content;
    @NotNull
    private LocalDateTime dateEnvoi;
    @NotNull
    private TypeMessage typeMessage;

    public NotificationDto() {
    }

    public NotificationDto(Long id, String content, LocalDateTime dateEnvoi, TypeMessage typeMessage) {
        this.id = id;
        this.content = content;
        this.dateEnvoi = dateEnvoi;
        this.typeMessage = typeMessage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(LocalDateTime dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public TypeMessage getTypeMessage() {
        return typeMessage;
    }

    public void setTypeMessage(TypeMessage typeMessage) {
        this.typeMessage = typeMessage;
    }
}

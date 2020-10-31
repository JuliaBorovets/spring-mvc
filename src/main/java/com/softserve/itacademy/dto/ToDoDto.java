package com.softserve.itacademy.dto;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ToDoDto {

    private long id;

    @NotBlank(message = "The 'title' cannot be empty")
    private String title;

    private String createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

    private Long ownerId;

    public ToDoDto() {
    }

    public ToDoDto(long id, String title, String createdAt, Long ownerId) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.ownerId = ownerId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "ToDoDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", ownerId=" + ownerId +
                '}';
    }
}

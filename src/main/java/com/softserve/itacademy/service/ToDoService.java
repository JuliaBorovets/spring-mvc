package com.softserve.itacademy.service;

import com.softserve.itacademy.model.ToDo;

import java.util.List;

public interface ToDoService {
    ToDo create(ToDo todo);
    ToDo readById(long id);
    ToDo update(ToDo todo);
    void delete(long id);
    void addCollaborator(long id, long collaboratorId);
    void removeCollaborator(long id, long collaboratorId);

    List<ToDo> getAll();
    List<ToDo> getByUserId(long userId);
}

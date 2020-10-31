package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.ToDoRepository;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ToDoServiceImpl implements ToDoService {

    private ToDoRepository todoRepository;
    private UserService userService;

    public ToDoServiceImpl(ToDoRepository todoRepository, UserService userService) {
        this.todoRepository = todoRepository;
        this.userService = userService;
    }

    @Override
    public ToDo create(ToDo todo) {
        return todoRepository.save(todo);
    }

    @Override
    public ToDo readById(long id) {
        Optional<ToDo> optional = todoRepository.findById(id);
        return optional.get();
    }

    @Override
    public ToDo update(ToDo todo) {
        ToDo oldTodo = readById(todo.getId());
        oldTodo.setTitle(todo.getTitle());
        return todoRepository.save(oldTodo);
    }

    @Override
    public void delete(long id) {
        ToDo todo = readById(id);
        todoRepository.delete(todo);
    }

    @Override
    public void addCollaborator(long id, long collaboratorId) {
        ToDo toDo = readById(id);
        User collaborator = userService.readById(collaboratorId);
        toDo.getCollaborators().add(collaborator);
        collaborator.getOtherTodos().add(toDo);
        todoRepository.save(toDo);
        userService.update(collaborator);
    }

    @Override
    public void removeCollaborator(long id, long collaboratorId) {
        ToDo toDo = readById(id);
        User collaborator = userService.readById(collaboratorId);
        toDo.getCollaborators().remove(collaborator);
        collaborator.getOtherTodos().remove(toDo);
        todoRepository.save(toDo);
        userService.update(collaborator);
    }

    @Override
    public List<ToDo> getAll() {
        List<ToDo> todos = todoRepository.findAll();
        return todos.isEmpty() ? new ArrayList<>() : todos;
    }

    @Override
    public List<ToDo> getByUserId(long userId) {
        List<ToDo> todos = todoRepository.getByUserId(userId);
        return todos.isEmpty() ? new ArrayList<>() : todos;
    }
}

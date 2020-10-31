package com.softserve.itacademy.controller;

import com.softserve.itacademy.dto.ToDoDto;
import com.softserve.itacademy.dto.ToDoTransformer;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/todos")
public class ToDoController {

    private ToDoService toDoService;
    private UserService userService;

    public ToDoController(ToDoService toDoService, UserService userService) {
        this.toDoService = toDoService;
        this.userService = userService;
    }

    @GetMapping("/create/users/{owner_id}")
    public String create(@PathVariable("owner_id") Long ownerId, Model model) {
        model.addAttribute("toDo", new ToDo());
        return "create-todo";
    }

    @PostMapping("/create/users/{owner_id}")
    public String create(@PathVariable("owner_id") Long ownerId, @Valid @ModelAttribute ToDoDto toDoDto,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/todos/create/users/{owner_id}";
        }
        ToDo toDoToCreate = ToDoTransformer.convertToEntity(toDoDto);
        toDoToCreate.setOwner(userService.readById(ownerId));
        toDoService.create(toDoToCreate);
        return "redirect:/todos/all/users/{owner_id}";
    }

    @GetMapping("/{id}/tasks")
    public String read(@PathVariable("id") Long id, Model model) {
        model.addAttribute("todo", toDoService.readById(id));
        //todo create template
        return "todo-tasks";
    }

    @GetMapping("/{todo_id}/update/users/{owner_id}")
    public String update(@PathVariable("todo_id") Long todoId, @PathVariable("owner_id") Long ownerId, Model model) {
        ToDoDto toDo = ToDoTransformer.convertToDto(toDoService.readById(todoId));
        model.addAttribute("toDo", toDo);
        return "update-todo";
    }

    @PostMapping("/{todo_id}/update/users/{owner_id}")
    public String update(@PathVariable("todo_id") Long todoId, @PathVariable("owner_id") Long ownerId,
                         @Valid @ModelAttribute("toDo") ToDoDto toDo, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "redirect:/todos/{todo_id}/update/users/{owner_id}";
        }

        ToDo toDoToUpdate = ToDoTransformer.convertToEntity(toDo);
        toDoToUpdate.setId(todoId);
        toDoService.update(toDoToUpdate);
        return "redirect:/todos/all/users/{owner_id}";
    }

    @GetMapping("/{todo_id}/delete/users/{owner_id}")
    public String delete(@PathVariable("todo_id") Long todoId, @PathVariable("owner_id") Long ownerId) {
        toDoService.delete(todoId);
        return "redirect:/todos/all/users/{owner_id}";
    }


    @GetMapping("/all/users/{user_id}")
    public String getAll(@PathVariable("user_id") Long userId, Model model) {
        List<ToDoDto> toDoList = toDoService.getByUserId(userId)
                .stream()
                .map(ToDoTransformer::convertToDto)
                .collect(Collectors.toList());
        model.addAttribute("todoList", toDoList);
        return "todos-user";
    }

    @GetMapping("/{id}/add")
    public String addCollaborator(@PathVariable("id") Long id, @RequestParam("coll_id") Long collaboratorId) {
        toDoService.addCollaborator(id, collaboratorId);
        return "redirect:/todos/{id}/tasks";
    }

    @GetMapping("/{id}/remove")
    public String removeCollaborator(@PathVariable("id") Long id, @RequestParam("coll_id") Long collaboratorId) {
        toDoService.removeCollaborator(id, collaboratorId);
        return "redirect:/todos/{id}/tasks";
    }
}

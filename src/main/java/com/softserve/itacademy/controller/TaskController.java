package com.softserve.itacademy.controller;

import com.softserve.itacademy.dto.TaskDto;
import com.softserve.itacademy.dto.TaskTransformer;
import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.service.StateService;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;
    private StateService stateService;
    private final ToDoService todoService;

    public TaskController(TaskService taskService, StateService stateService, ToDoService todoService) {
        this.taskService = taskService;
        this.stateService = stateService;
        this.todoService = todoService;
    }

    @GetMapping("/create/todos/{todo_id}")
    public String create(@PathVariable("todo_id") long todoId, Model model) {
        model.addAttribute("task", new TaskDto());
        model.addAttribute("todo", todoService.readById(todoId));
        model.addAttribute("priorities", Priority.values());
        return "create-task";
    }

    @PostMapping("/create/todos/{todo_id}")
    public String create(@PathVariable("todo_id") long todoId, Model model,
                         @Validated @ModelAttribute("task") TaskDto taskDto, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("todo", todoService.readById(todoId));
            model.addAttribute("priorities", Priority.values());
            return "create-task";
        }
        Task task = TaskTransformer.convertToEntity(
                taskDto,
                todoService.readById(taskDto.getTodoId()),
                stateService.getByName("New")
        );
        taskService.create(task);
        return "redirect:/todos/" + todoId + "/tasks";
    }

    @GetMapping("/{task_id}/update/todos/{todo_id}")
    public String update(@PathVariable("task_id") Long taskId, @PathVariable("todo_id") Long todoId, Model model) {
        TaskDto taskDto = TaskTransformer.convertToDto(taskService.readById(taskId));
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("states", stateService.getAll());
        model.addAttribute("taskDto", taskDto);
        return "update-task";
    }

    @PostMapping("/{task_id}/update/todos/{todo_id}")
    public String update(@PathVariable("task_id") Long taskId, @PathVariable("todo_id") Long todoId,
                         @Valid @ModelAttribute("taskDto") TaskDto taskDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/tasks/{task_id}/update/todos/{todo_id}";
        }

        Task taskToUpdate = TaskTransformer.convertToEntity(taskDto, stateService.readById(taskDto.getStateId()));
        taskToUpdate.setId(taskId);
        taskService.update(taskToUpdate);
        return "redirect:/todos/{todo_id}/tasks";
    }


    @GetMapping("/{task_id}/delete/todos/{todo_id}")
    public String delete(@PathVariable("task_id") long taskId, @PathVariable("todo_id") long todoId) {
        taskService.delete(taskId);
        return "redirect:/todos/" + todoId + "/tasks";
    }
}

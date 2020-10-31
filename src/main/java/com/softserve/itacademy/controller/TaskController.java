package com.softserve.itacademy.controller;

import com.softserve.itacademy.dto.TaskDto;
import com.softserve.itacademy.dto.TaskTransformer;
import com.softserve.itacademy.dto.ToDoDto;
import com.softserve.itacademy.dto.ToDoTransformer;
import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.service.StateService;
import com.softserve.itacademy.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;
    private StateService stateService;

    public TaskController(TaskService taskService, StateService stateService) {
        this.taskService = taskService;
        this.stateService = stateService;
    }

    //    @GetMapping("/create/todos/{todo_id}")
//    public String create(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
//
//    @PostMapping("/create/todos/{todo_id}")
//    public String create(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
//
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
//
//    @GetMapping("/{task_id}/delete/todos/{todo_id}")
//    public String delete(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
}

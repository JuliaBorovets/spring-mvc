package com.softserve.itacademy.controller;

import com.softserve.itacademy.dto.UserDto;
import com.softserve.itacademy.dto.UserTransformer;
import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.RoleService;
import com.softserve.itacademy.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("user", new UserDto());
        return "create-user";
    }

    @PostMapping("/create")
    public String create(@Validated @ModelAttribute("user") UserDto userDto, BindingResult result) {
        userDto.setRoleId(roleService.readById(2).getId());
        if (result.hasErrors()) {
            return "create-user";
        }
        User newUser = UserTransformer.convertToUser(userDto);
        userService.create(newUser);
        return "redirect:/users/all";
    }

    @GetMapping("/{id}/read")
    public String read(@PathVariable long id, Model model) {
        User user = userService.readById(id);
        model.addAttribute("user", user);
        return "user-info";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable long id, Model model,
                         @Validated @ModelAttribute("user") UserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            return "reidrect:/users/{id}/update";
        }

        User user = UserTransformer.convertToUser(userDto);
        user.setRole(roleService.readById(userDto.getRoleId()));
        userService.update(user);
        return "redirect:/users/" + id + "/read";
    }

    @GetMapping("/{id}/update")
    public String update(@PathVariable long id, Model model) {
        UserDto userDto = UserTransformer.convertToDto(userService.readById(id));
        model.addAttribute("user", userDto);
        model.addAttribute("roles", roleService.getAll());
        return "update-user";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/users/all";
    }

    @GetMapping("/all")
    public String getAll(Model model) {
        model.addAttribute("users", userService.getAll());
        return "users-list";
    }
}

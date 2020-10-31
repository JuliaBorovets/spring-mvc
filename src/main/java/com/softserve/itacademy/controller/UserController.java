package com.softserve.itacademy.controller;

import com.softserve.itacademy.dto.UserDto;
import com.softserve.itacademy.dto.UserTransformer;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.RoleService;
import com.softserve.itacademy.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

    //add needed fields

    //    @GetMapping("/create")
//    public String create(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
//
//    @PostMapping("/create")
//    public String create(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
//

    @GetMapping("/{id}/read")
    public String read(@PathVariable long id, Model model) {
        User user = userService.readById(id);
        model.addAttribute("user", user);
        return "user-info";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable long id, @RequestParam("oldPassword") String oldPassword,
                         @RequestParam("roleId") long roleId, Model model,
                         @Validated @ModelAttribute("user") UserDto userDto, BindingResult result) {
        User user = UserTransformer.convertToUser(userDto);
        System.out.println("USER ==" + user.toString());
        User oldUser = userService.readById(id);
        //System.out.println("USVER1 ==" + userService.readById(id).getRole().toString());
        if (result.hasErrors()) {
            user.setRole(oldUser.getRole());
            model.addAttribute("roles", roleService.getAll());
            return "update-user";
        }
        System.out.println(user.toString());
        userService.update(user);
        //System.out.println("USVER2 ==" + userService.readById(id).getRole().toString());
        return "redirect:/users/" + id + "/read";
    }

    @GetMapping("/{id}/update")
    public String update(@PathVariable long id, Model model) {
        UserDto userDto = UserTransformer.convertToDto(userService.readById(id));
        //System.out.println(userDto.toString());
        model.addAttribute("user", userDto);
        model.addAttribute("roles", roleService.getAll());
        return "update-user";
    }



//    @GetMapping("/{id}/delete")
//    public String delete(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
//
//    @GetMapping("/all")
//    public String getAll(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
}

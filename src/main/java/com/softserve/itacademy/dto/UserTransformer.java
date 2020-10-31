package com.softserve.itacademy.dto;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.RoleService;

public class UserTransformer {



    public static UserDto convertToDto(User user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().getId(),
                user.getMyTodos(),
                user.getOtherTodos());
    }

    public static User convertToUser (UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setMyTodos(userDto.getMyTodos());
        user.setOtherTodos(userDto.getOtherTodos());
        return user;
    }
}

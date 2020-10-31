package com.softserve.itacademy.dto;

import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.model.ToDo;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

public class UserDto {

    private long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private Long roleId;

    private List<ToDo> myTodos;

    private List<ToDo> otherTodos;

    public UserDto() {
    }

    public UserDto(long id, @NotBlank String firstName, @NotBlank String lastName, @NotBlank String email, @NotBlank String password, Long role, List<ToDo> myTodos, List<ToDo> otherTodos) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roleId = role;
        this.myTodos = myTodos;
        this.otherTodos = otherTodos;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public List<ToDo> getMyTodos() {
        return myTodos;
    }

    public void setMyTodos(List<ToDo> myTodos) {
        this.myTodos = myTodos;
    }

    public List<ToDo> getOtherTodos() {
        return otherTodos;
    }

    public void setOtherTodos(List<ToDo> otherTodos) {
        this.otherTodos = otherTodos;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + roleId +
                ", myTodos=" + myTodos +
                ", otherTodos=" + otherTodos +
                '}';
    }
}

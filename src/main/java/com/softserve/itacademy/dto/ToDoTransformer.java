package com.softserve.itacademy.dto;

import com.softserve.itacademy.model.ToDo;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ToDoTransformer {

    public static ToDoDto convertToDto(ToDo toDo) {
        return new ToDoDto(
                toDo.getId(),
                toDo.getTitle(),
                toDo.getCreatedAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")),
                toDo.getOwner().getId(),
                toDo.getOwner().getFirstName() + ' ' + toDo.getOwner().getLastName()
        );
    }

    public static ToDo convertToEntity(ToDoDto toDoDto) {

        ToDo toDo = new ToDo();

        toDo.setId(toDoDto.getId());
        toDo.setTitle(toDoDto.getTitle());
        toDo.setCreatedAt(LocalDateTime.parse(toDoDto.getCreatedAt(), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));

        System.out.println(toDo);
        return toDo;
    }
}

package com.ereed.libraryproject.models;

import lombok.*;

import javax.validation.constraints.*;

@Data
public class Person {
    private int id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 150, message = "Имя должно быть длиной от 2 до 150 символов")
    private String name;

    @Min(value = 1900, message = "Год рождения должен быть больше, чем 1900")
    private int yearOfBirth;

    private int count_books;
}


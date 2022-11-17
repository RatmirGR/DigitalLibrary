package com.ereed.libraryproject.models;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class Book {
    private int id;

    @NotEmpty(message = "Название книги не должно быть пустым")
    @Size(min = 2, max = 250, message = "Название книги должно быть длиной от 2 до 250 символов")
    private String title;

    @NotEmpty(message = "Имя автора книги не должно быть пустым")
    @Size(min = 2, max = 250, message = "Имя автора книги должно быть длиной от 2 до 250 символов")
    private String author;

    @Min(value = 1700, message = "Год рождения должен быть больше, чем 1700")
    private int year_of_created;

    private boolean check_status;
}
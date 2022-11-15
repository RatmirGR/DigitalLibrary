package com.ereed.libraryproject.models;

import lombok.*;

import javax.validation.constraints.*;

@Data
public class Person {
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;

//    @Pattern(regexp = "",
//            message = "Your ...")
    private int yearOfBirth;
    private int count_books;
}

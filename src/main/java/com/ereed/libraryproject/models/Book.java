package com.ereed.libraryproject.models;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class Book {
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 200, message = "Name should be between 2 and 200 characters")
    private String name;

    //    @Pattern(regexp = "",
//            message = "Your ...")
    private String author;
    private int year_of_created;
    private boolean check_status;
}

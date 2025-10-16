package com.nlitvins.web_application.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Builder(toBuilder = true)
public class Book {

    private int id;
    private String title;
    private String author;
    private int quantity;
    private LocalDate creationYear;
    private BookStatus status;
    private BookGenre genre;
    private short pages;
    private String edition;
    private LocalDate releaseDate;
    private BookType type;
}

package com.nlitvins.web_application.inbound.model;

import com.nlitvins.web_application.domain.model.BookGenre;
import com.nlitvins.web_application.domain.model.BookStatus;
import com.nlitvins.web_application.domain.model.BookType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class BookCreateRequest {
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
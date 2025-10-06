package com.nlitvins.web_application.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Setter
@Getter
@Builder(toBuilder = true)
//@EqualsAndHashCode
public class Book {

    private int id;
    private String title;
    private String author;
    //TODO
    private boolean isBorrowed;
    private int quantity;
    private LocalDate creationYear;
    private BookStatus status;
    private BookGenre genre;
    private short pages;
    private String edition;
    private LocalDate releaseDate;
    private BookType type;

    @Override
    public String toString() {
        return "Book: " +
                "id: " + id +
                ", " + title +
                ", " + author +
                ", " + isBorrowed +
                ' ';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return id == book.id && isBorrowed == book.isBorrowed && Objects.equals(title, book.title) && Objects.equals(author, book.author);
    }
}

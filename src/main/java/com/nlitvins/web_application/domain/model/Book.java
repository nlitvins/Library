package com.nlitvins.web_application.domain.model;

import lombok.Builder;
import lombok.Setter;

import java.util.Objects;

@Setter
@Builder(toBuilder = true)
public class Book {

    private int id;
    private String title;
    private String author;
    private boolean isBorrowed;
    private int quantity;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public int getQuantity() {
        return quantity;
    }

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

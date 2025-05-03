package com.nlitvins.web_application.domain.model;

import java.util.Objects;

public class Book {

    private int id;
    private String title;
    private String author;
    private boolean isBorrowed;
    private int quantity;

    public Book() {
    }

    public Book(int id, String title, String author, boolean isBorrowed) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isBorrowed = isBorrowed;
        this.quantity = 1;
    }

    public Book(int id, String title, String author, int quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

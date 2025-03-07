package com.nlitvins.web_application;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class BookEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "author_id", nullable = false)
    private int author;

    @Column(name = "quantity")
    private int quantity;

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public int getAuthor() {
        return author;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getId() {
        return id;
    }
}

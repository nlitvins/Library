package com.nlitvins.web_application.outbound.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "creation_year")
    private LocalDate creationYear;

    @Column(name = "status")
    private short status;

    @Column(name = "genre")
    private short genre;

    @Column(name = "pages")
    private short pages;

    @Column(name = "edition")
    private String edition;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "type")
    private short type;

    @Column(name = "isbn", length = 20)
    private String isbn;

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreationYear(LocalDate creationYear) {
        this.creationYear = creationYear;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public void setGenre(short genre) {
        this.genre = genre;
    }

    public void setPages(short pages) {
        this.pages = pages;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setType(short type) {
        this.type = type;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getId() {
        return id;
    }

    public LocalDate getCreationYear() {
        return creationYear;
    }

    public short getStatus() {
        return status;
    }

    public short getGenre() {
        return genre;
    }

    public short getPages() {
        return pages;
    }

    public String getEdition() {
        return edition;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public short getType() {
        return type;
    }

    public String getIsbn() {
        return isbn;
    }
}

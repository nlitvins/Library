package com.nlitvins.web_application.inbound.model;

import lombok.Builder;

@Builder
public class BookResponse {
    private int id;
    private String title;
    private String author;
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

    public int getQuantity() {
        return quantity;
    }
}

package com.nlitvins.web_application.inbound.model;

import lombok.Builder;

@Builder
public class ReservationCreateRequest {
    private int userId;
    private int bookId;

    public int getUserId() {
        return userId;
    }

    public int getBookId() {
        return bookId;
    }


    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}

package com.nlitvins.web_application.domain.repository;

import com.nlitvins.web_application.domain.model.Book;

public interface IsbnBookRepository {

    Book save(Book book);
}

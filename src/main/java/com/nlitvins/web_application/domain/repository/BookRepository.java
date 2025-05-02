package com.nlitvins.web_application.domain.repository;

import com.nlitvins.web_application.domain.model.Book;

import java.util.List;

public interface BookRepository {

    List<Book> findAll();

    Book findById(int id);

    Book save(Book book);
}

package com.nlitvins.web_application.domain.usecase.book;


import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.repository.BookRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookReadUseCase {

    private final BookRepository bookRepository;

    public BookReadUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(int id) {
        return bookRepository.findById(id);
    }
}

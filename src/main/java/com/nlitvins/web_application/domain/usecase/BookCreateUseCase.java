package com.nlitvins.web_application.domain.usecase;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.repository.BookRepository;
import org.springframework.stereotype.Component;

@Component
public class BookCreateUseCase {

    private final BookRepository bookRepository;

    public BookCreateUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }
}

package com.nlitvins.web_application.domain.usecase.book;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.model.BookStatus;
import com.nlitvins.web_application.domain.repository.BookRepository;
import org.springframework.stereotype.Component;

@Component
public class BookSetStatusUseCase {

    private final BookRepository bookRepository;

    public BookSetStatusUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book setStatus(int id) {
        Book book = bookRepository.findById(id);
        if (book.getStatus() == BookStatus.AVAILABLE) {
            book.setStatus(BookStatus.NOT_AVAILABLE);
        } else if (book.getStatus() == BookStatus.NOT_AVAILABLE) {
            book.setStatus(BookStatus.AVAILABLE);
        }
        bookRepository.save(book);
        return book;
    }
}


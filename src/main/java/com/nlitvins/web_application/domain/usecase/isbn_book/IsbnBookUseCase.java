package com.nlitvins.web_application.domain.usecase.isbn_book;

import com.nlitvins.web_application.domain.exception.IsbnBookNotFoundException;
import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.model.IsbnBook;
import com.nlitvins.web_application.domain.repository.BookRepository;
import com.nlitvins.web_application.domain.repository.IsbnBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class IsbnBookUseCase {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("LLL dd, yyyy", Locale.ENGLISH);

    private final IsbnBookRepository isbnBookRepository;
    private final BookRepository bookRepository;

    public Book createBookByIsbn(Book book) {
        IsbnBook bookByIsbn = isbnBookRepository.createBookByIsbn(book.getIsbn());
        if (bookByIsbn == null) {
            throw new IsbnBookNotFoundException(book.getIsbn());
        }
        mergeBookData(book, bookByIsbn);
        return bookRepository.save(book);
    }

    private void mergeBookData(Book book, IsbnBook bookByIsbn) {
        book.setTitle(bookByIsbn.getTitle());
        book.setAuthor(bookByIsbn.getAuthors().getFirst());
        book.setReleaseDate(getPublishedDate(bookByIsbn));
    }

    private LocalDate getPublishedDate(IsbnBook bookByIsbn) {
        String date = bookByIsbn.getPublishedDate();
        return LocalDate.parse(date, FORMATTER);
    }
}

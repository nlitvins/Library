package com.nlitvins.web_application.domain.usecase.isbn_book;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.model.IsbnBook;
import com.nlitvins.web_application.domain.repository.BookRepository;
import com.nlitvins.web_application.domain.repository.IsbnBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class IsbnBookUseCase {

    private final IsbnBookRepository isbnBookRepository;
    private final BookRepository bookRepository;

    public Book createBookByIsbn(Book book) {
        IsbnBook bookByIsbn = isbnBookRepository.createBookByIsbn(book.getIsbn());
        mergeBookData(book, bookByIsbn);
        return bookRepository.save(book);
    }

    private void mergeBookData(Book book, IsbnBook bookByIsbn) {
        book.setTitle(bookByIsbn.getTitle());
        book.setAuthor(bookByIsbn.getAuthors().getFirst());
        book.setReleaseDate(
                LocalDate.parse(bookByIsbn.getPublishedDate())
        );


        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
        String text = date.format(formatter);
        LocalDate parsedDate = LocalDate.parse(text, formatter);
    }
}

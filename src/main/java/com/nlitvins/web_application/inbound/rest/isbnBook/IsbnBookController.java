package com.nlitvins.web_application.inbound.rest.isbnBook;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.outbound.repository.BookByIsbnRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/test", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class IsbnBookController {

    private final BookByIsbnRepositoryImpl bookByISBNRepositoryImpl;

    @GetMapping
    public Book getBookByIsbn(String isbn) {
        bookByISBNRepositoryImpl.findByIsbn(isbn);
    }
}

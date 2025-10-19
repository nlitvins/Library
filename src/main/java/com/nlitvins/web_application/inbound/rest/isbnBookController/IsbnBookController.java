package com.nlitvins.web_application.inbound.rest.isbnBookController;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.outbound.rest.BookByIsbnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/test", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class IsbnBookController {

    private final BookByIsbnRepository bookByISBNRepository;

    @GetMapping
    public Book getBookByIsbn(String isbn) {
        return bookByISBNRepository.findByIsbn(isbn);
    }
}

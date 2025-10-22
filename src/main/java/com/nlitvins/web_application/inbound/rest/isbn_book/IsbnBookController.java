package com.nlitvins.web_application.inbound.rest.isbn_book;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.usecase.isbn_book.IsbnBookUseCase;
import com.nlitvins.web_application.inbound.model.BookCreateRequest;
import com.nlitvins.web_application.inbound.model.BookResponse;
import com.nlitvins.web_application.inbound.utils.InboundMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/books", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class IsbnBookController {

    private final IsbnBookUseCase isbnBookUseCase;

    @PostMapping("/isbn")
    public BookResponse createBookByIsbn(@RequestBody BookCreateRequest request) {
        Book book = InboundMapper.Books.toDomain(request);
        Book savedBook = isbnBookUseCase.createBookByIsbn(book);
        return InboundMapper.Books.toDTO(savedBook);
    }
}

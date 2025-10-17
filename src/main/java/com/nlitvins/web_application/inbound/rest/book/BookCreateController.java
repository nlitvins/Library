package com.nlitvins.web_application.inbound.rest.book;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.usecase.book.BookCreateUseCase;
import com.nlitvins.web_application.inbound.model.BookCreateRequest;
import com.nlitvins.web_application.inbound.model.BookResponse;
import com.nlitvins.web_application.inbound.utils.InboundMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/books", produces = APPLICATION_JSON_VALUE)
public class BookCreateController {

    private final BookCreateUseCase bookCreateUseCase;

    public BookCreateController(BookCreateUseCase bookCreateUseCase) {
        this.bookCreateUseCase = bookCreateUseCase;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public BookResponse postBook(@RequestBody BookCreateRequest request) {
        Book book = InboundMapper.Books.toDomain(request);
        Book savedBook = bookCreateUseCase.addBook(book);
        return InboundMapper.Books.toDTO(savedBook);
    }
}

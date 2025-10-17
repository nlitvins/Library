package com.nlitvins.web_application.inbound.rest.book;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.usecase.book.BookSetStatusUseCase;
import com.nlitvins.web_application.inbound.model.BookResponse;
import com.nlitvins.web_application.inbound.utils.InboundMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/books", produces = APPLICATION_JSON_VALUE)
public class BookSetStatusController {

    private final BookSetStatusUseCase bookSetStatusUseCase;

    public BookSetStatusController(BookSetStatusUseCase bookSetStatusUseCase) {
        this.bookSetStatusUseCase = bookSetStatusUseCase;
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public BookResponse setBookStatus(@PathVariable int id) {
        Book book = bookSetStatusUseCase.setStatus(id);
        return InboundMapper.Books.toDTO(book);
    }
}

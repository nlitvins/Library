package com.nlitvins.web_application.inbound.rest;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.usecase.BookCreateUseCase;
import com.nlitvins.web_application.domain.usecase.BookReadUseCase;
import com.nlitvins.web_application.domain.usecase.BookSetStatusUseCase;
import com.nlitvins.web_application.inbound.model.BookCreateRequest;
import com.nlitvins.web_application.inbound.model.BookResponse;
import com.nlitvins.web_application.inbound.utils.InboundMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/books", produces = APPLICATION_JSON_VALUE)
public class BookController {

    private final BookReadUseCase bookReadUseCase;
    private final BookSetStatusUseCase bookSetStatusUseCase;
    private final BookCreateUseCase bookCreateUseCase;

    public BookController(BookReadUseCase bookReadUseCase, BookSetStatusUseCase bookSetStatusUseCase, BookCreateUseCase bookCreateUseCase) {
        this.bookReadUseCase = bookReadUseCase;
        this.bookSetStatusUseCase = bookSetStatusUseCase;
        this.bookCreateUseCase = bookCreateUseCase;
    }

    @GetMapping
    public List<BookResponse> books() {
        List<Book> book = bookReadUseCase.getBooks();
        return InboundMapper.Books.toDTOList(book);
    }

    @GetMapping("/{bookId}")
    public BookResponse findBook(@PathVariable int bookId) {
        Book book = bookReadUseCase.getBookById(bookId);
        return InboundMapper.Books.toDTO(book);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public BookResponse postBook(@RequestBody BookCreateRequest request) {
        Book book = InboundMapper.Books.toDomain(request);
        Book savedBook = bookCreateUseCase.addBook(book);
        return InboundMapper.Books.toDTO(savedBook);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public BookResponse setBookStatus(@PathVariable int id) {
        Book book = bookSetStatusUseCase.setStatus(id);
        return InboundMapper.Books.toDTO(book);
    }
}

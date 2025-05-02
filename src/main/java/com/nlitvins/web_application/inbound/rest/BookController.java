package com.nlitvins.web_application.inbound.rest;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.usecase.BookReadUseCase;
import com.nlitvins.web_application.inbound.model.BookRequest;
import com.nlitvins.web_application.inbound.model.BookResponse;
import com.nlitvins.web_application.inbound.utils.InboundMapper;
import com.nlitvins.web_application.outbound.repository.jpa.BookJpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/books", produces = APPLICATION_JSON_VALUE)
public class BookController {

    private final BookReadUseCase bookReadUseCase;
    private final BookJpaRepository bookJpaRepository;

    public BookController(BookJpaRepository bookJpaRepository, BookReadUseCase bookReadUseCase) {
        this.bookJpaRepository = bookJpaRepository;
        this.bookReadUseCase = bookReadUseCase;
    }

    @GetMapping
    public List<BookResponse> books() {
        List<Book> book = bookReadUseCase.getBook();
        return InboundMapper.Books.toDTOList(book);
    }

    @GetMapping("/{bookId}")
    public BookResponse findBook(@PathVariable int bookId) {
        Book book = bookReadUseCase.findById(bookId);
        return InboundMapper.Books.toDTO(book);
    }

    @PostMapping
    public BookResponse postBook(@RequestBody BookRequest request) {
        Book book = InboundMapper.Books.toDomain(request);
        Book savedBook = bookReadUseCase.addBook(book);
        return InboundMapper.Books.toDTO(savedBook);
    }
}

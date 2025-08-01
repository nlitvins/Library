package com.nlitvins.web_application.inbound.rest;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.usecase.BookReadUseCase;
import com.nlitvins.web_application.inbound.model.BookResponse;
import com.nlitvins.web_application.inbound.utils.InboundMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/books", produces = APPLICATION_JSON_VALUE)
public class BookReadController {

    private final BookReadUseCase bookReadUseCase;

    public BookReadController(BookReadUseCase bookReadUseCase) {
        this.bookReadUseCase = bookReadUseCase;
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
}

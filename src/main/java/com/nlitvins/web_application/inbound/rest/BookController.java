package com.nlitvins.web_application.inbound.rest;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.outbound.model.BookEntity;
import com.nlitvins.web_application.outbound.repository.BookJpaRepository;
import com.nlitvins.web_application.outbound.utils.OutboundMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    private final BookJpaRepository bookJpaRepository;

    public BookController(BookJpaRepository bookJpaRepository) {
        this.bookJpaRepository = bookJpaRepository;
    }

    @GetMapping
    public List<Book> books() {
        List<BookEntity> bookEntities = bookJpaRepository.findAll();
        return OutboundMapper.Books.toDomainList(bookEntities);
    }

    @GetMapping("/{bookId}")
    public Book findBook(@PathVariable int bookId) {
        BookEntity bookEntity = bookJpaRepository.getReferenceById(bookId);
        return OutboundMapper.Books.toDomain(bookEntity);
    }

    @PostMapping
    public Book postBook(@RequestBody Book book) {
        BookEntity bookEntity = new BookEntity();

        bookEntity.setTitle(book.getTitle());
        bookEntity.setAuthor(Integer.parseInt(book.getAuthor()));
        bookEntity.setQuantity(book.getQuantity());

        BookEntity savedBookEntity = bookJpaRepository.save(bookEntity);
        return OutboundMapper.Books.toDomain(savedBookEntity);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable int bookId) {
        bookJpaRepository.deleteById(bookId);
        return ResponseEntity.noContent().build();
    }
}

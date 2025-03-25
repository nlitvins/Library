package com.nlitvins.web_application;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    private final BookRepository bookRepository;
    private final BookJpaRepository bookJpaRepository;

    public BookController(BookJpaRepository bookJpaRepository, BookRepository bookRepository) {
        this.bookJpaRepository = bookJpaRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public List<Book> books() {
        List<BookEntity> bookEntities = bookJpaRepository.findAll();
        return Mapper.bookToList(bookEntities);
    }

    @PutMapping("/{putId}")
    public Book findBook(@PathVariable int putId) {
        BookEntity bookEntity = bookJpaRepository.getReferenceById(putId);
        return Mapper.entityToBook(bookEntity);
    }

    @PostMapping
    public Book postBook(@RequestBody Book book) {
        BookEntity bookEntity = new BookEntity();

        bookEntity.setTitle(book.getTitle());
        bookEntity.setAuthor(Integer.parseInt(book.getAuthor()));
        bookEntity.setQuantity(book.getQuantity());

        BookEntity savedBookEntity = bookJpaRepository.save(bookEntity);
        return Mapper.entityToBook(savedBookEntity);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable int bookId) {
        bookJpaRepository.deleteById(bookId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{getId}")
    public Book updateBook(@PathVariable int getId) {
        return bookRepository.updateBookStatus(getId);
    }
}

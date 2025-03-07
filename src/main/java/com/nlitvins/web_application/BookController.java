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
        return bookRepository.findAll();
    }

    @GetMapping("/{getId}")
    public Book findBook(@PathVariable int getId) {
        BookEntity bookEntity = bookJpaRepository.getReferenceById(getId);
        return Mapper.toBook(bookEntity);
    }

    @PostMapping
    public Book postBook(@RequestBody Book book) {

        return bookRepository.addBook(
                new Book(
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        false
                ));
    }

    @DeleteMapping("/{getId}")
    public ResponseEntity<Void> deleteBook(@PathVariable int getId) {
        boolean isDeleted = bookRepository.deleteById(getId);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{getId}")
    public Book reserveBook(@PathVariable int getId) {
        return bookRepository.updateBookStatus(getId);

    }
}

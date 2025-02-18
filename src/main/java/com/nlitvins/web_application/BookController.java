package com.nlitvins.web_application;

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

    private static final Library library = new Library();

    @GetMapping
    public List<Book> books() {
        return library.findAll();
    }

    @GetMapping("/{getId}")
    public Book findBook(@PathVariable int getId) {
        return library.findById(getId);
    }

    @PostMapping
    public Book postBook(@RequestBody Book book) {

        return library.addBook(
                new Book(
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        false
                ));
    }

    @DeleteMapping("/{getId}")
    public Book deleteBook(@PathVariable int getId) {

        library.deleteById(getId);

        return null;
    }

    @PutMapping("/{getId}")
    public Book reserveBook(@PathVariable int getId) {
        return library.updateBookStatus(getId);
    }
}

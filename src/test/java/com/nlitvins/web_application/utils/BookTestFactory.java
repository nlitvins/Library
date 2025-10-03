package com.nlitvins.web_application.utils;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.model.BookGenre;
import com.nlitvins.web_application.domain.model.BookStatus;
import com.nlitvins.web_application.domain.model.BookType;
import com.nlitvins.web_application.inbound.model.BookCreateRequest;
import com.nlitvins.web_application.inbound.model.BookResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookTestFactory {

    public static List<Book> givenBooks() {
        List<Book> books = new ArrayList<>();
        books.add(givenBook());
        books.add(givenBook());
        return books;
    }

    public static List<BookResponse> givenBookResponses() {
        List<BookResponse> bookResponses = new ArrayList<>();
        bookResponses.add(givenResponseBook());
        bookResponses.add(givenResponseBook());
        return bookResponses;
    }

    public static BookCreateRequest givenRequestBook() {
        return BookCreateRequest.builder()
                .title("Test Book")
                .author("Test Author")
                .quantity(4)
                .creationYear(LocalDate.parse("1888-04-03"))
                .status(BookStatus.AVAILABLE)
                .genre(BookGenre.ADVENTURE)
                .pages((short) 444)
                .edition("Test-edition")
                .releaseDate(LocalDate.parse("2021-10-09"))
                .type(BookType.BOOK)
                .build();
    }

    public static BookResponse givenResponseBook() {
        return BookResponse.builder()
                .title("Test Book")
                .author("Test Author")
                .quantity(4)
                .creationYear(LocalDate.parse("1888-04-03"))
                .status(BookStatus.AVAILABLE)
                .genre(BookGenre.ADVENTURE)
                .pages((short) 444)
                .edition("Test-edition")
                .releaseDate(LocalDate.parse("2021-10-09"))
                .type(BookType.BOOK)
                .build();
    }

    public static Book givenBook() {
        return Book.builder()
                .title("Test Book")
                .author("Test Author")
                .quantity(4)
                .creationYear(LocalDate.parse("1888-04-03"))
                .status(BookStatus.AVAILABLE)
                .genre(BookGenre.ADVENTURE)
                .pages((short) 444)
                .edition("Test-edition")
                .releaseDate(LocalDate.parse("2021-10-09"))
                .type(BookType.BOOK)
                .build();
    }

    public static Book givenBookWithId(int id) {
        return Book.builder()
                .id(id)
                .title("Test Book")
                .author("Test Author")
                .quantity(4)
                .creationYear(LocalDate.parse("1888-04-03"))
                .status(BookStatus.AVAILABLE)
                .genre(BookGenre.ADVENTURE)
                .pages((short) 444)
                .edition("Test-edition")
                .releaseDate(LocalDate.parse("2021-10-09"))
                .type(BookType.BOOK)
                .build();
    }

    public static BookResponse givenResponseBook(int id) {
        return BookResponse.builder()
                .id(id)
                .title("Test Book")
                .author("Test Author")
                .quantity(4)
                .creationYear(LocalDate.parse("1888-04-03"))
                .status(BookStatus.AVAILABLE)
                .genre(BookGenre.ADVENTURE)
                .pages((short) 444)
                .edition("Test-edition")
                .releaseDate(LocalDate.parse("2021-10-09"))
                .type(BookType.BOOK)
                .build();
    }
}

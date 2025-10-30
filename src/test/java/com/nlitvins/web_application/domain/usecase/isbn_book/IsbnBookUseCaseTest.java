package com.nlitvins.web_application.domain.usecase.isbn_book;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.model.BookGenre;
import com.nlitvins.web_application.domain.model.BookStatus;
import com.nlitvins.web_application.domain.model.BookType;
import com.nlitvins.web_application.domain.model.IsbnBook;
import com.nlitvins.web_application.outbound.repository.fake.BookRepositoryFake;
import com.nlitvins.web_application.outbound.repository.fake.IsbnBookRepositoryFake;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IsbnBookUseCaseTest {

    private IsbnBookUseCase sut;

    private IsbnBookRepositoryFake isbnBookRepository;
    private BookRepositoryFake bookRepository;

    @BeforeAll
    void setup() {
        isbnBookRepository = new IsbnBookRepositoryFake();
        bookRepository = new BookRepositoryFake();
        sut = new IsbnBookUseCase(isbnBookRepository, bookRepository);
    }

    @BeforeEach
    void clear() {
        isbnBookRepository.clear();
        bookRepository.clear();
    }


    @Test
    void saveBookWhenCorrectBookPassed() {
        Book before = bookRepository.findById(1);
        assertNull(before);
        Book book = givenBook();
        givenBookIsbn();

        Book result = sut.createBookByIsbn(book);
        Book saved = bookRepository.findById(1);

        assertResult(result, book);
        assertResult(saved, book);
    }

    private static void assertResult(Book saved, Book book) {
        assertThat(saved).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("Harry Potter and the Sorcerer's Stone");
        assertThat(saved.getAuthor()).isEqualTo("J.K. Rowling");
        assertThat(saved.getReleaseDate()).isEqualTo("1998-06-16");

        assertThat(book).usingRecursiveComparison()
                .ignoringFields("title", "author", "releaseDate")
                .isEqualTo(saved);
    }

    //TODO Exception


    private Book givenBook() {
        return Book.builder()
                .id(1)
                .title("String")
                .author("String")
                .quantity(11)
                .creationYear(LocalDate.parse("2025-10-29"))
                .status(BookStatus.AVAILABLE)
                .genre(BookGenre.ROMANCE)
                .pages((short) 11)
                .edition("String")
                .releaseDate(LocalDate.parse("2025-10-29"))
                .type(BookType.BOOK)
                .isbn("0606170979")
                .build();
    }

    private IsbnBook givenBookIsbn() {
        return isbnBookRepository.save(IsbnBook.builder()
                .title("Harry Potter and the Sorcerer's Stone")
                .authors(List.of("J.K. Rowling"))
                .publisher(List.of("Scholastic"))
                .publishedDate("Jun 16, 1998")
                .coverUrl("https://covers.openlibrary.org/b/id/1234567-M.jpg")
                .build(), "0606170979");
    }
}

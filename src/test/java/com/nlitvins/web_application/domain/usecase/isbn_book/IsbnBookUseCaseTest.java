package com.nlitvins.web_application.domain.usecase.isbn_book;

import com.nlitvins.web_application.domain.exception.IsbnBookNotFoundException;
import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.model.IsbnBook;
import com.nlitvins.web_application.outbound.repository.fake.BookRepositoryFake;
import com.nlitvins.web_application.outbound.repository.fake.IsbnBookRepositoryFake;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static com.nlitvins.web_application.utils.factory.BookTestFactory.givenBook;
import static com.nlitvins.web_application.utils.factory.BookTestFactory.givenBookWithIsbn;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        List<Book> before = bookRepository.findAll();
        assertThat(before).isEmpty();

        Book book = givenBookWithIsbn();
        givenBookIsbn();
        Book result = sut.createBookByIsbn(book);

        assertResult(result, book);
        List<Book> savedBooks = bookRepository.findAll();
        assertThat(savedBooks).hasSize(1);
        assertResult(savedBooks.getFirst(), book);
    }

    @Test
    void throwExceptionWhenIsbnBookNotFound() {
        Book book = givenBook();
        IsbnBookNotFoundException thrown = assertThrows(
                IsbnBookNotFoundException.class,
                () -> sut.createBookByIsbn(book)
        );
        assertEquals("Book not found for ISBN: null", thrown.getMessage());
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

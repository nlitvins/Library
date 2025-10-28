package com.nlitvins.web_application.domain.usecase.isbn_book;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.outbound.repository.fake.BookRepositoryFake;
import com.nlitvins.web_application.outbound.repository.fake.IsbnBookRepositoryFake;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IsbnBookUseCaseTest {

    private IsbnBookUseCase sut;

    private IsbnBookRepositoryFake repository;
    private BookRepositoryFake bookRepository;

    @BeforeAll
    void setup() {
        repository = new IsbnBookRepositoryFake();
        bookRepository = new BookRepositoryFake();
        sut = new IsbnBookUseCase(repository, bookRepository);
    }

    @BeforeEach
    void clear() {
        repository.clear();
        bookRepository.clear();
    }

    private Book givenBookFirst() {
        return bookRepository.save(
                Book.builder()
                        .id(1)
                        .title("Fiona")
                        .author("Jack")
                        .quantity(1)
                        .isbn("testIsbn")
                        .build()
        );
    }

    @Test
    void saveBookWhenCorrectBookPassed() {
        Book before = bookRepository.findById(1);
        assertNull(before);

        Book book = givenBookFirst();
        Book result = sut.createBookByIsbn(book);
        assertNotNull(result);
//
        Book after = bookRepository.findById(1);
        assertNotNull(after);
    }
}

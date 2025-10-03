package com.nlitvins.web_application.domain.usecase;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.usecase.book.BookCreateUseCase;
import com.nlitvins.web_application.outbound.repository.fake.BookRepositoryFake;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookCreateUseCaseTest {

    private BookCreateUseCase sut;

    private BookRepositoryFake bookRepository;

    @BeforeAll
    void setUp() {
        bookRepository = new BookRepositoryFake();
        sut = new BookCreateUseCase(bookRepository);
    }

    @BeforeEach
    void clear() {
        bookRepository.clear();
    }

    private Book givenBookFirst() {
        return bookRepository.save(
                Book.builder()
                        .id(1)
                        .title("Fiona")
                        .author("Jack")
                        .quantity(1)
                        .build()
        );
    }

    @Test
    void saveBookWhenCorrectBookPassed() {
        Book before = bookRepository.findById(1);
        assertNull(before);

        Book book = givenBookFirst();
        Book result = sut.addBook(book);
        assertNotNull(result);

        Book savedBook = bookRepository.findById(1);
        assertNotNull(savedBook);
    }
}

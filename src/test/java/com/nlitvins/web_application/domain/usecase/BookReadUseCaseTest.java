package com.nlitvins.web_application.domain.usecase;


import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.outbound.repository.fake.BookRepositoryFake;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookReadUseCaseTest {

    private BookReadUseCase sut;

    private BookRepositoryFake bookRepository;

    @BeforeAll
    void setUp() {
        bookRepository = new BookRepositoryFake();
        sut = new BookReadUseCase(bookRepository);
    }

    @BeforeEach
    void clear() {
        bookRepository.clear();
    }

    private Book givenBookFirst() {
        return bookRepository.save(new Book(1, "Fiona", String.valueOf(1), 1));
    }

    private Book givenBookSecond() {
        return bookRepository.save(new Book(2, "Shrek", String.valueOf(2), 4));
    }

    @Test
    void emptyListReturnWhenGetBooks() {
        List<Book> result = sut.getBook();
        assertTrue(result.isEmpty());
    }

    @Test
    void returnListWhenGetBooksCalled() {
        List<Book> before = sut.getBook();
        assertTrue(before.isEmpty());

        Book book1 = givenBookFirst();
        Book book2 = givenBookSecond();
        List<Book> result = sut.getBook();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertThat(result, containsInAnyOrder(List.of(book1, book2).toArray()));
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

    @Test
    void returnNullWhenBookNotFound() {
        Book result = bookRepository.findById(1);
        assertNull(result);
    }

}

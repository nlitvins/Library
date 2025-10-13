package com.nlitvins.web_application.domain.usecase.book;


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

    @Test
    void returnEmptyListWhenGetBooks() {
        List<Book> result = sut.getBooks();
        assertTrue(result.isEmpty());
    }

    @Test
    void returnListWhenGetBooksCalled() {
        Book book1 = givenBookFirst();
        Book book2 = givenBookSecond();
        List<Book> result = sut.getBooks();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertThat(result, containsInAnyOrder(List.of(book1, book2).toArray()));
    }

    @Test
    void returnNullWhenBookNotFound() {
        Book result = bookRepository.findById(1);
        assertNull(result);
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

    private Book givenBookSecond() {
        return bookRepository.save(
                Book.builder()
                        .id(2)
                        .title("Shrek")
                        .author("Bass")
                        .quantity(4)
                        .build()
        );
    }
}

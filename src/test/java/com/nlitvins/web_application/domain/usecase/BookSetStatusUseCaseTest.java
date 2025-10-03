package com.nlitvins.web_application.domain.usecase;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.model.BookStatus;
import com.nlitvins.web_application.domain.usecase.book.BookSetStatusUseCase;
import com.nlitvins.web_application.outbound.repository.fake.BookRepositoryFake;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookSetStatusUseCaseTest {

    private BookSetStatusUseCase sut;

    private BookRepositoryFake bookRepository;

    @BeforeAll
    void setUp() {
        bookRepository = new BookRepositoryFake();
        sut = new BookSetStatusUseCase(bookRepository);
    }

    @Test
    void returnStatusAvailableWhenBookWasNotAvailable() {
        Book book = givenBookStatusNotAvailable();

        Book result = sut.setStatus(book.getId());

        assertEquals(BookStatus.AVAILABLE, result.getStatus());
    }

    @Test
    void returnStatusNotAvailableWhenBookWasAvailable() {
        Book book = givenBookStatusAvailable();

        Book result = sut.setStatus(book.getId());

        assertEquals(BookStatus.NOT_AVAILABLE, result.getStatus());
    }


    private Book givenBookStatusAvailable() {
        return bookRepository.save(
                Book.builder()
                        .id(1)
                        .title("Fiona")
                        .author("Jack")
                        .quantity(1)
                        .status(BookStatus.AVAILABLE)
                        .build()
        );
    }

    private Book givenBookStatusNotAvailable() {
        return bookRepository.save(
                Book.builder()
                        .id(1)
                        .title("Fiona")
                        .author("Jack")
                        .quantity(1)
                        .status(BookStatus.NOT_AVAILABLE)
                        .build()
        );
    }
}



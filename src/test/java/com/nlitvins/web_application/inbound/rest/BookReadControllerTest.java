package com.nlitvins.web_application.inbound.rest;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.usecase.BookReadUseCase;
import com.nlitvins.web_application.inbound.model.BookCreateRequest;
import com.nlitvins.web_application.inbound.model.BookResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.nlitvins.web_application.utils.TestFactory.givenBook;
import static com.nlitvins.web_application.utils.TestFactory.givenRequestBook;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

class BookReadControllerTest extends AbstractControllerTest {

    private final BookReadUseCase bookReadUseCase = mock();
    private final BookReadController controller = new BookReadController(bookReadUseCase);

    @Override
    protected String getControllerURI() {
        return "/books";
    }

    @Override
    protected Object getController() {
        return controller;
    }

    @BeforeEach
    void setUp() {
        reset(bookReadUseCase);
    }

    @Nested
    class DirectCalls {

        @Test
        void returnBooks() {
            BookCreateRequest bookCreateRequest = givenRequestBook();
            Book book = givenBook();
            List<Book> books;
            doReturn(book).when(bookReadUseCase).getBooks();
            when(bookReadUseCase.getBooks()).thenReturn(books);

            List<BookResponse> booksResponse = controller.books();

            assertNotNull(booksResponse);
            assertEquals(1, booksResponse.size());
            assertThat(booksResponse, containsInAnyOrder(List.of(book).toArray()));
        }
    }
}

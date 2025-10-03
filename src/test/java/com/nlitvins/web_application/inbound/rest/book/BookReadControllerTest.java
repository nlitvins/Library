package com.nlitvins.web_application.inbound.rest.book;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.usecase.book.BookReadUseCase;
import com.nlitvins.web_application.inbound.model.BookResponse;
import com.nlitvins.web_application.inbound.rest.AbstractControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.nlitvins.web_application.utils.BookTestFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookReadControllerTest extends AbstractControllerTest {
    @Nested
    class DirectCalls {

        @Test
        void returnBooks() {
            List<Book> books = givenBooks();
            doReturn(books).when(bookReadUseCase).getBooks();

            List<BookResponse> booksResponse = controller.books();

            assertNotNull(booksResponse);
            assertEquals(2, booksResponse.size());
            assertEquals(givenBookResponses(), booksResponse);
        }

        @Test
        void returnBookById() {
            int bookId = 1;
            Book book = givenBookWithId(bookId);
            doReturn(book).when(bookReadUseCase).getBookById(bookId);

            BookResponse bookResponse = controller.findBook(bookId);

            assertEquals(givenResponseBook(bookId), bookResponse);
        }

    }
    @Nested
    class ApiCalls {

        @Test
        void returnBooks() throws Exception {
            List<Book> books = givenBooks();
            doReturn(books).when(bookReadUseCase).getBooks();

            MvcResult mvcResult = mockMvc.perform(
                            MockMvcRequestBuilders.get(getControllerURI())
                                    .contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            List<BookResponse> bookResponse = getResponseList(mvcResult, BookResponse.class);
            assertEquals(givenBookResponses(), bookResponse);
        }

    }

    //TODO
    @Test
    void returnBookById() throws Exception {
        int bookId = 1;
        Book book = givenBookWithId(bookId);
        doReturn(book).when(bookReadUseCase).getBookById(bookId);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get(getIdControllerURI(), bookId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        BookResponse bookResponse = getResponseObject(mvcResult, BookResponse.class);
        assertEquals(givenResponseBook().getTitle(), bookResponse.getTitle());

    }

    private final BookReadUseCase bookReadUseCase = mock();
    private final BookReadController controller = new BookReadController(bookReadUseCase);

    @Override
    protected String getControllerURI() {
        return "/books";
    }

    protected String getIdControllerURI() {
        return "/books/{bookId}";
    }

    @Override
    protected Object getController() {
        return controller;
    }

    @BeforeEach
    void setUp() {
        reset(bookReadUseCase);
    }
}

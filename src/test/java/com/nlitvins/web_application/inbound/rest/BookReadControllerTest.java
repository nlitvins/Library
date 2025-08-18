package com.nlitvins.web_application.inbound.rest;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.usecase.BookReadUseCase;
import com.nlitvins.web_application.inbound.model.BookResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.nlitvins.web_application.utils.TestFactory.givenBookResponses;
import static com.nlitvins.web_application.utils.TestFactory.givenBooks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
            List<Book> books = givenBooks();
            doReturn(books).when(bookReadUseCase).getBooks();

            List<BookResponse> booksResponse = controller.books();

            assertNotNull(booksResponse);
            assertEquals(2, booksResponse.size());
            assertEquals(givenBookResponses(), booksResponse);
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
}

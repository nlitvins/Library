package com.nlitvins.web_application.inbound.rest.book;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.usecase.book.BookSetStatusUseCase;
import com.nlitvins.web_application.inbound.model.BookResponse;
import com.nlitvins.web_application.inbound.rest.AbstractControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.nlitvins.web_application.utils.BookTestFactory.givenBookWithId;
import static com.nlitvins.web_application.utils.BookTestFactory.givenResponseBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookSetStatusControllerTest extends AbstractControllerTest {

    private final BookSetStatusUseCase bookSetStatusUseCase = mock();
    private final BookSetStatusController controller = new BookSetStatusController(bookSetStatusUseCase);

    @Override
    protected String getControllerURI() {
        return "/books/{id}/status";
    }

    @Override
    protected Object getController() {
        return controller;
    }

    @BeforeEach
    void setUp() {
        reset(bookSetStatusUseCase);
    }

    @Nested
    class DirectCalls {
        @Test
        void returnBookWhenSetBookStatus() {
            int bookId = 1;
            Book book = givenBookWithId(bookId);
            doReturn(book).when(bookSetStatusUseCase).setStatus(book.getId());

            BookResponse bookResponse = controller.setBookStatus(book.getId());

            assertEquals(givenResponseBook(1).getId(), bookResponse.getId());
            assertEquals(givenResponseBook(1).getStatus(), bookResponse.getStatus());
        }
    }

    @Nested
    class ApiCalls {

        @Test
        void returnBookWhenSetBookStatus() throws Exception {
            int bookId = 1;
            Book book = givenBookWithId(bookId);

            doReturn(book).when(bookSetStatusUseCase).setStatus(book.getId());

            MvcResult mvcResult = mockMvc.perform(
                            MockMvcRequestBuilders.put(getControllerURI(), bookId)
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            BookResponse bookResponse = getResponseObject(mvcResult, BookResponse.class);

            assertEquals(givenResponseBook(bookId).getStatus(), bookResponse.getStatus());

        }
    }
}

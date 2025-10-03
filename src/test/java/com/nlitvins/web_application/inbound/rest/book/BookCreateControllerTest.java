package com.nlitvins.web_application.inbound.rest.book;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.usecase.book.BookCreateUseCase;
import com.nlitvins.web_application.inbound.model.BookCreateRequest;
import com.nlitvins.web_application.inbound.model.BookResponse;
import com.nlitvins.web_application.inbound.rest.AbstractControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static com.nlitvins.web_application.utils.BookTestFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookCreateControllerTest extends AbstractControllerTest {
    @Nested
    class DirectCalls {
        @Test
        void returnBookWhenBookCreated() {
            BookCreateRequest bookCreateRequest = givenRequestBook();
            Book book = givenBook();
            doReturn(book).when(bookCreateUseCase).addBook(book);

            BookResponse bookResponse = controller.postBook(bookCreateRequest);

            assertEquals(givenResponseBook(), bookResponse);
        }
    }

    @Nested
    class ApiCalls {
        @Test
        void returnBookWhenBookCreated() throws Exception {
            BookCreateRequest bookCreateRequest = givenRequestBook();
            Book book = givenBook();
            doReturn(book).when(bookCreateUseCase).addBook(book);

            MvcResult mvcResult = mockMvc.perform(
                            post(getControllerURI())
                                    .content(getBodyContent(bookCreateRequest))
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            BookResponse bookResponse = getResponseObject(mvcResult, BookResponse.class);
            assertEquals(givenResponseBook(), bookResponse);
        }

    }

    private final BookCreateUseCase bookCreateUseCase = mock();
    private final BookCreateController controller = new BookCreateController(bookCreateUseCase);

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
        reset(bookCreateUseCase);
    }
}
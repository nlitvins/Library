package com.nlitvins.web_application.inbound.rest.isbn_book;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.usecase.isbn_book.IsbnBookUseCase;
import com.nlitvins.web_application.inbound.model.BookCreateRequest;
import com.nlitvins.web_application.inbound.model.BookResponse;
import com.nlitvins.web_application.inbound.rest.AbstractControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static com.nlitvins.web_application.utils.BookTestFactory.givenBookWithIsbn;
import static com.nlitvins.web_application.utils.BookTestFactory.givenRequestBookWithIsbn;
import static com.nlitvins.web_application.utils.BookTestFactory.givenResponseBookWithIsbn;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IsbnBookControllerTest extends AbstractControllerTest {
    private final IsbnBookUseCase isbnBookUseCase = mock();
    private final IsbnBookController controller = new IsbnBookController(isbnBookUseCase);

    @Override
    protected String getControllerURI() {
        return "/books/isbn";
    }

    @Override
    protected Object getController() {
        return controller;
    }

    @BeforeEach
    void setUp() {
        reset(isbnBookUseCase);
    }

    @Nested
    class DirectCalls {
        @Test
        void returnBookWhenBookCreated() {
            BookCreateRequest bookCreateRequest = givenRequestBookWithIsbn();
            Book book = givenBookWithIsbn();
            doReturn(book).when(isbnBookUseCase).createBookByIsbn(book);

            BookResponse bookResponse = controller.createBookByIsbn(bookCreateRequest);

            assertThat(bookResponse)
                    .usingRecursiveComparison()
                    .isEqualTo(givenResponseBookWithIsbn());
        }
    }

    @Nested
    class ApiCalls {
        @Test
        void returnBookWhenBookCreated() throws Exception {
            BookCreateRequest bookCreateRequest = givenRequestBookWithIsbn();
            Book book = givenBookWithIsbn();
            doReturn(book).when(isbnBookUseCase).createBookByIsbn(book);

            MvcResult mvcResult = mockMvc.perform(
                            post(getControllerURI())
                                    .content(getBodyContent(bookCreateRequest))
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            BookResponse bookResponse = getResponseObject(mvcResult, BookResponse.class);
            assertThat(bookResponse)
                    .usingRecursiveComparison()
                    .isEqualTo(givenResponseBookWithIsbn());
        }
    }
}

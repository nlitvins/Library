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

import static com.nlitvins.web_application.utils.BookTestFactory.givenBookResponses;
import static com.nlitvins.web_application.utils.BookTestFactory.givenBookWithId;
import static com.nlitvins.web_application.utils.BookTestFactory.givenBooks;
import static com.nlitvins.web_application.utils.BookTestFactory.givenResponseBook;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
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

            assertThat(booksResponse)
                    .usingRecursiveComparison()
                    .isEqualTo(givenBookResponses());

        }

        @Test
        void returnBookById() {
            int bookId = 1;
            Book book = givenBookWithId(bookId);
            doReturn(book).when(bookReadUseCase).getBookById(bookId);

            BookResponse bookResponse = controller.findBook(bookId);

            assertThat(bookResponse)
                    .usingRecursiveComparison()
                    .isEqualTo(givenResponseBook(bookId));
        }
    }

    @Nested
    class ApiCalls {
        @Test
        void returnBooks() throws Exception {
            List<Book> books = givenBooks();
            doReturn(books).when(bookReadUseCase).getBooks();

            MvcResult mvcResult = mockMvc.perform(
                            MockMvcRequestBuilders.get(getControllerURI()))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            List<BookResponse> bookResponse = getResponseList(mvcResult, BookResponse.class);
            assertThat(bookResponse)
                    .usingRecursiveComparison()
                    .isEqualTo(givenBookResponses());
        }


        @Test
        void returnBookById() throws Exception {
            int bookId = 1;
            Book book = givenBookWithId(bookId);
            doReturn(book).when(bookReadUseCase).getBookById(bookId);

            MvcResult mvcResult = mockMvc.perform(
                            MockMvcRequestBuilders.get(getControllerURI() + "/" + bookId))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            BookResponse bookResponse = getResponseObject(mvcResult, BookResponse.class);
            assertThat(bookResponse)
                    .usingRecursiveComparison()
                    .isEqualTo(givenResponseBook(bookId));

        }
    }
}
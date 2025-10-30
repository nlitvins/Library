package com.nlitvins.web_application.outbound.repository;

import com.nlitvins.web_application.domain.model.IsbnBook;
import com.nlitvins.web_application.outbound.rest.IsbnBookClient;
import com.nlitvins.web_application.utils.WireMockFixture;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IsbnBookRepositoryTest extends WireMockFixture {

    private IsbnBookRepositoryImpl sut;

    @BeforeAll
    void setUp() {
        IsbnBookClient client = Feign.builder()
                .contract(new SpringMvcContract())
                .decoder(new JacksonDecoder())
                .target(IsbnBookClient.class, serverUrl);

        sut = new IsbnBookRepositoryImpl(client);
    }


    @Test
    void shouldReturnBookWhenFound() {

        String isbn = "0606170979";

        String response = """
                {
                  "title": "Harry Potter and the Sorcerer's Stone",
                  "authors": [
                    "J. K. Rowling"
                  ],
                  "publishers": [
                    "Brand: Demco Media",
                    "Demco Media"
                  ],
                  "publishDate": "Jun 16, 1998",
                  "coverUrl": "https://covers.openlibrary.org/b/id/10447992-M.jpg"
                }
                """;

        wiremockGetStubFromJson("/books/" + isbn, response);

        IsbnBook book = sut.createBookByIsbn(isbn);

        assertThat(book).isNotNull();
        assertThat(book.getTitle()).isEqualTo("Harry Potter and the Sorcerer's Stone");
        assertThat(book.getAuthors()).hasSize(1);
        assertThat(book.getAuthors().getFirst()).isEqualTo("J. K. Rowling");
        assertThat(book.getPublishedDate()).isEqualTo("Jun 16, 1998");
    }
}


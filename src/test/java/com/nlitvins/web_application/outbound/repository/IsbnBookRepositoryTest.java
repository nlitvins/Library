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

import static com.nlitvins.web_application.utils.factory.BookTestFactory.TEST_ISBN;
import static com.nlitvins.web_application.utils.factory.BookTestFactory.isbnResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

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
        wiremockGetStubFromJson("/books/" + TEST_ISBN, isbnResponse());

        IsbnBook book = sut.createBookByIsbn(TEST_ISBN);

        assertThat(book).isNotNull();
        assertThat(book.getTitle()).isEqualTo("Harry Potter and the Sorcerer's Stone");
        assertThat(book.getAuthors()).hasSize(1);
        assertThat(book.getAuthors().getFirst()).isEqualTo("J. K. Rowling");
        assertThat(book.getPublishedDate()).isEqualTo("Jun 16, 1998");
    }

    @Test
    void shouldReturnNullWhenNotFound() {
        wiremockGetStubFromJson("/books/" + TEST_ISBN, null);

        IsbnBook book = sut.createBookByIsbn(TEST_ISBN);
        assertNull(book);
    }
}


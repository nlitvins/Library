package com.nlitvins.web_application.outbound.rest;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.outbound.model.BookByIsbnResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookByIsbnRepository {

    private final Client client;

    //    @Override
    public Book findByIsbn(String isbn) {
        BookByIsbnResponse response = client.getBookByIsbn(isbn);
        if (response == null) {
            return null;
        }
        return ResponseToBook(response);
    }

    private Book ResponseToBook(BookByIsbnResponse response) {
        return Book.builder()
                .title(response.getTitle())
                .author(response.getAuthors().getFirst())
//                .releaseDate(response.getPublishDate())
//                .edition(response.getPublisher())
                .build();
    }

}

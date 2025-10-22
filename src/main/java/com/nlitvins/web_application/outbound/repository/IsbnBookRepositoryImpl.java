package com.nlitvins.web_application.outbound.repository;

import com.nlitvins.web_application.domain.model.IsbnBook;
import com.nlitvins.web_application.domain.repository.IsbnBookRepository;
import com.nlitvins.web_application.outbound.model.IsbnBookResponse;
import com.nlitvins.web_application.outbound.rest.IsbnBookClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IsbnBookRepositoryImpl implements IsbnBookRepository {

    private final IsbnBookClient isbnBookClient;

    @Override
    public IsbnBook createBookByIsbn(String isbn) {
        IsbnBookResponse response = isbnBookClient.getBookByIsbn(isbn);
        if (response == null) {
            return null;
        }
        return mapIsbnBook(response);
    }

    private static IsbnBook mapIsbnBook(IsbnBookResponse response) {
        return IsbnBook.builder()
                .title(response.getTitle())
                .authors(response.getAuthors())
                .build();
    }

}

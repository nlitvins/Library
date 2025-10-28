package com.nlitvins.web_application.outbound.repository.fake;

import com.nlitvins.web_application.domain.model.IsbnBook;
import com.nlitvins.web_application.domain.repository.IsbnBookRepository;

import java.util.HashMap;

public class IsbnBookRepositoryFake implements IsbnBookRepository {

    private final HashMap<String, IsbnBook> books = new HashMap<>();

    @Override
    public IsbnBook createBookByIsbn(String isbn) {
        return books.get(isbn);
    }

    public void clear() {
        books.clear();
    }

    public IsbnBook save(IsbnBook book) {
        books.put(book.getIsbn(), book);
        return book.toBuilder().build();
    }
}

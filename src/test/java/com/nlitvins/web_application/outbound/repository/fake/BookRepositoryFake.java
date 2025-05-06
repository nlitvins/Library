package com.nlitvins.web_application.outbound.repository.fake;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.repository.BookRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class BookRepositoryFake implements BookRepository {

    private final HashMap<Integer, Book> books = new HashMap<>();

    @Override
    public Book findById(int searchId) {
        return books.get(searchId);
    }

    @Override
    public List<Book> findAll() {
        return books.values().stream().toList();
    }

    @Override
    public Book save(Book book) {
        books.put(book.getId(), book);
        return book;
    }

    public void clear() {
        books.clear();
    }
}
 
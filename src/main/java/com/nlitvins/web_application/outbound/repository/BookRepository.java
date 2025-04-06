package com.nlitvins.web_application.outbound.repository;

import com.nlitvins.web_application.domain.model.Book;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class BookRepository {

    private final HashMap<Integer, Book> books = new HashMap<>();

    public BookRepository() {
        books.put(1231, new Book(1231, "Fiona", "Mike", true));
        books.put(1232, new Book(1232, "Shrek", "John", true));
        books.put(1233, new Book(1233, "Lord Farquaad", "Jack", true));
        books.put(1234, new Book(1234, "Prince Charming", "Ivan", false));
    }

    public Book addBook(Book book) {
        if (books.putIfAbsent(book.getId(), book) != null) {
            throw new RuntimeException("ID already exists");
        }
        return book;
    }

    public Book findById(int searchId) {
        return books.get(searchId);
    }

    public List<Book> findAll() {
        return books.values().stream().toList();
    }

    public boolean deleteById(int deleteId) {
        Book book = books.get(deleteId);
        return books.remove(deleteId, book);
    }
    public Book updateBookStatus(int updateId) {
        Book book = books.get(updateId);
        if (book == null) {
            throw new RuntimeException("Book don't exist");
        }

        book.setBorrowed(!book.isBorrowed());
        books.put(updateId, book);
        return book;
    }
}
 
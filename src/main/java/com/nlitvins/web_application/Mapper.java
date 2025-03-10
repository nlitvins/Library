package com.nlitvins.web_application;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    public static BookEntity toEntity(Book book) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(book.getId());
        bookEntity.setTitle(book.getTitle());
        bookEntity.setAuthor(Integer.parseInt(book.getAuthor()));
        bookEntity.setQuantity(book.getQuantity());
        return bookEntity;
    }

    public static Book toBook(BookEntity bookEntity) {
        Book book = new Book();
        book.setId(bookEntity.getId());
        book.setTitle(bookEntity.getTitle());
        book.setAuthor(String.valueOf(bookEntity.getAuthor()));
        book.setQuantity(bookEntity.getQuantity());
        return book;
    }

    public static List<Book> toList(List<BookEntity> bookEntities) {

        List<Book> books = new ArrayList<>();
        for (int index = bookEntities.size() - 1; index >= 0; index--) {
            BookEntity get = bookEntities.get(index);
            Book mapper = toBook(get);
            books.add(mapper);
        }
        return books;
    }
}

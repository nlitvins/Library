package com.nlitvins.web_application;

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
}

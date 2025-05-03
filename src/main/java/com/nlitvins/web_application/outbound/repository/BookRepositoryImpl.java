package com.nlitvins.web_application.outbound.repository;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.repository.BookRepository;
import com.nlitvins.web_application.outbound.model.BookEntity;
import com.nlitvins.web_application.outbound.repository.jpa.BookJpaRepository;
import com.nlitvins.web_application.outbound.utils.OutboundMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookRepositoryImpl implements BookRepository {
    private final BookJpaRepository jpaRepository;

    public BookRepositoryImpl(BookJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Book> findAll() {
        List<BookEntity> bookEntities = jpaRepository.findAll();
        return OutboundMapper.Books.toDomainList(bookEntities);
    }

    @Override
    public Book findById(int id) {
        BookEntity bookEntities = jpaRepository.getReferenceById(id);
        return OutboundMapper.Books.toDomain(bookEntities);
    }

    @Override
    public Book save(Book book) {
        BookEntity bookEntity = OutboundMapper.Books.toEntity(book);
        BookEntity savedBookEntity = jpaRepository.save(bookEntity);
        return OutboundMapper.Books.toDomain(savedBookEntity);
    }
}

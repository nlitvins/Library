package com.nlitvins.web_application.outbound.repository;

import com.nlitvins.web_application.domain.model.IsbnBook;
import com.nlitvins.web_application.domain.repository.IsbnBookRepository;
import com.nlitvins.web_application.inbound.utils.InboundMapper;
import com.nlitvins.web_application.outbound.model.BookEntity;
import com.nlitvins.web_application.outbound.model.IsbnBookResponse;
import com.nlitvins.web_application.outbound.repository.jpa.IsbnBookJpaRepository;
import com.nlitvins.web_application.outbound.rest.IsbnBookClient;
import com.nlitvins.web_application.outbound.utils.OutboundMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookByIsbnRepositoryImpl implements IsbnBookRepository {

    private final IsbnBookClient isbnBookClient;
    private final IsbnBookJpaRepository jpaRepository;

    //    @Override
    public IsbnBook findByIsbn(String isbn) {
        IsbnBookResponse response = isbnBookClient.getBookByIsbn(isbn);
        if (response == null) {
            return null;
        }
        return ResponseToBook(response);
    }

    private IsbnBook ResponseToBook(IsbnBookResponse response) {
        return InboundMapper.BookByIsbn.toDomain(response);
    }

    @Override
    public IsbnBook save(IsbnBook isbnBook) {
        BookEntity bookEntity = OutboundMapper.IsbnBooks.toEntity(isbnBook);
        BookEntity savedBookEntity = jpaRepository.save(bookEntity);
        return OutboundMapper.IsbnBooks.toDomain(savedBookEntity);
    }

}

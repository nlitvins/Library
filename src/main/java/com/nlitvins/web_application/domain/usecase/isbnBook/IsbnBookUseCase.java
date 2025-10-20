package com.nlitvins.web_application.domain.usecase.isbnBook;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.repository.IsbnBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IsbnBookUseCase {

    private final IsbnBookRepository isbnBookRepository;

    public Book getBookByIsbn(Book book) {
        return isbnBookRepository.save(book);
    }
}

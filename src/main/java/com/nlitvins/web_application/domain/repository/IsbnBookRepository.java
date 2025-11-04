package com.nlitvins.web_application.domain.repository;

import com.nlitvins.web_application.domain.model.IsbnBook;

public interface IsbnBookRepository {

    IsbnBook createBookByIsbn(String isbn);
}

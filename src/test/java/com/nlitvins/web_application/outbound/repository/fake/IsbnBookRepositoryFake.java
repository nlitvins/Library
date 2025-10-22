package com.nlitvins.web_application.outbound.repository.fake;

import com.nlitvins.web_application.domain.model.IsbnBook;
import com.nlitvins.web_application.domain.repository.IsbnBookRepository;
import org.apache.commons.lang3.NotImplementedException;

public class IsbnBookRepositoryFake implements IsbnBookRepository {

    @Override
    public IsbnBook createBookByIsbn(String isbn) {
        throw new NotImplementedException();
    }
}

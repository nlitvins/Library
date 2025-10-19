package com.nlitvins.web_application.outbound.rest;

import com.nlitvins.web_application.outbound.model.BookByIsbnResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "openlibrary",
        url = "http://localhost:8081/",
        configuration = FeignConfig.class
)
public interface Client {
    @GetMapping("/books/{isbn}")
    BookByIsbnResponse getBookByIsbn(@PathVariable("isbn") String isbn);
}

package com.nlitvins.web_application.outbound.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class IsbnBookResponse {
    private String title;
    private List<String> authors;
    private List<String> publisher;
    private String publishDate;
    private String coverUrl;
}

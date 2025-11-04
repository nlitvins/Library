package com.nlitvins.web_application.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
public class IsbnBook {
    private String title;
    private List<String> authors;
    private List<String> publisher;
    private String publishedDate;
    private String coverUrl;
}

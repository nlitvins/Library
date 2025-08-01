package com.nlitvins.web_application.inbound.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nlitvins.web_application.inbound.config.RestExceptionHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.TimeZone;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractControllerTest {
    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper;

    protected abstract String getControllerURI();

    protected abstract Object getController();

    @BeforeAll
    public void initMockMvc() {
        MappingJackson2HttpMessageConverter converter = jacksonHttpMessageConverter();
        objectMapper = converter.getObjectMapper();

        mockMvc = MockMvcBuilders
                .standaloneSetup(getController())
                .setMessageConverters(converter)
                .setControllerAdvice(new RestExceptionHandler())
                .build();
    }

    protected MappingJackson2HttpMessageConverter jacksonHttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = converter.getObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.enable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.setTimeZone(TimeZone.getTimeZone("UTC"));
        return converter;
    }

    protected String getBodyContent(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert object to JSON", e);
        }
    }

    protected <T> T getResponseObject(MvcResult mvcResult, Class<T> clazz) throws IOException {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), clazz);
    }
} 
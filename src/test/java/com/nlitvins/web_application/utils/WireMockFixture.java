package com.nlitvins.web_application.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class WireMockFixture {
    protected String serverUrl;
    protected WireMockServer server;
    protected ObjectMapper objectMapper;

    private static final int SERVER_PORT = 63124;

    @BeforeAll
    public void baseBeforeAll() {
        serverUrl = "http://localhost:" + SERVER_PORT;
        server = new WireMockServer(WireMockConfiguration.options().port(SERVER_PORT));
        server.start();

        objectMapper = new ObjectMapper();
    }

    @AfterEach
    public void baseAfterEach() {
        server.resetAll();
    }

    @AfterAll
    public void baseAfterAll() {
        server.stop();
    }

    protected void wiremockGetStubFromFile(String url, String responsePath) {
        server.stubFor(
                get(urlEqualTo(url))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                        .withBody(FileUtils.getTextFromResource(responsePath))
                        )
        );
    }

    protected void wiremockGetStubFromJson(String url, String jsonResponse) {
        server.stubFor(
                get(urlEqualTo(url))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                        .withBody(jsonResponse)
                        )
        );
    }

    protected String toJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert object to JSON", e);
        }
    }

    protected String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
} 
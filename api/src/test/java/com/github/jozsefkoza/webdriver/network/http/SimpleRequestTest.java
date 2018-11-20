package com.github.jozsefkoza.webdriver.network.http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Unit test for {@link SimpleResponse}.
 *
 * @author JoeZee
 */
class SimpleRequestTest {
    private static final String METHOD = "";
    private static final URI URL = URI.create("");
    private static final Map<String, List<String>> HEADERS = Collections.emptyMap();
    private static final String PAYLOAD = "";

    @Test
    void shouldFailIfMethodIsNull() {
        assertThatThrownBy(() -> SimpleRequest.builder().method(null).url(URL).headers(HEADERS).payload(PAYLOAD)
            .build()).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldFailIfUrlIsNull() {
        assertThatThrownBy(() -> SimpleRequest.builder().method(METHOD).url(null).headers(HEADERS).payload(PAYLOAD)
            .build()).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldFailIfHeadersAreNull() {
        assertThatThrownBy(() -> SimpleRequest.builder().method(METHOD).url(URL).headers(null).payload(PAYLOAD).build())
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldNotFailIfBodyIsNull() {
        Request request = SimpleRequest.builder().method(METHOD).url(URL).headers(HEADERS).payload(null).build();

        assertThat(request.payload()).isEmpty();
    }
}

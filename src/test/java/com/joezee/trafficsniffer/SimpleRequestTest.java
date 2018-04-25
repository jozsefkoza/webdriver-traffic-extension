package com.joezee.trafficsniffer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;

import com.google.common.collect.ImmutableMap;
import com.joezee.trafficsniffer.TrafficElement.Request;

/**
 * Unit test for {@link SimpleResponse}.
 *
 * @author JoeZee
 */
class SimpleRequestTest {
    private static final HttpMethod NONNULL_METHOD = HttpMethod.GET;
    private static final URI NONNULL_URL = URI.create("");
    private static final Map<String, List<String>> NONNULL_HEADERS = ImmutableMap.of();
    private static final String NONNULL_BODY = "";

    @Test
    void shouldFailIfMathodIsNull() {
        assertThatThrownBy(() -> SimpleRequest.builder().withMethod(null).withUrl(NONNULL_URL).withHeaders(NONNULL_HEADERS).withBody(NONNULL_BODY).build())
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldFailIfUrlIsNull() {
        assertThatThrownBy(() -> SimpleRequest.builder().withMethod(NONNULL_METHOD).withUrl(null).withHeaders(NONNULL_HEADERS).withBody(NONNULL_BODY).build())
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldFailIfHeadersAreNull() {
        assertThatThrownBy(() -> SimpleRequest.builder().withMethod(NONNULL_METHOD).withUrl(NONNULL_URL).withHeaders(null).withBody(NONNULL_BODY).build())
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldNotFailIfBodyIsNull() {
        Request request = SimpleRequest.builder().withMethod(NONNULL_METHOD).withUrl(NONNULL_URL).withHeaders(NONNULL_HEADERS).withBody(null).build();

        assertThat(request.body()).isEmpty();
    }
}

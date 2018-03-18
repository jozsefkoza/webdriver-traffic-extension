package com.joezee.trafficsniffer.record;

import com.google.common.collect.ImmutableMap;
import org.springframework.http.HttpMethod;
import org.testng.annotations.Test;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test for {@link SimpleResponse}.
 *
 * @author JoeZee
 */
public class SimpleRequestTest {
    private static final HttpMethod NONNULL_METHOD = HttpMethod.GET;
    private static final URI NONNULL_URL = URI.create("");
    private static final Map<String, List<String>> NONNULL_HEADERS = ImmutableMap.of();
    private static final String NONNULL_BODY = "";

    @Test(expectedExceptions = NullPointerException.class)
    public void shouldFailIfMathodIsNull() {
        SimpleRequest.builder().withMethod(null).withUrl(NONNULL_URL).withHeaders(NONNULL_HEADERS).withBody(NONNULL_BODY).build();
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void shouldFailIfUrlIsNull() {
        SimpleRequest.builder().withMethod(NONNULL_METHOD).withUrl(null).withHeaders(NONNULL_HEADERS).withBody(NONNULL_BODY).build();
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void shouldFailIfHeadersAreNull() {
        SimpleRequest.builder().withMethod(NONNULL_METHOD).withUrl(NONNULL_URL).withHeaders(null).withBody(NONNULL_BODY).build();
    }

    @Test
    public void shouldNotFailIfBodyIsNull() {
        SimpleRequest request = SimpleRequest.builder().withMethod(NONNULL_METHOD).withUrl(NONNULL_URL).withHeaders(NONNULL_HEADERS).withBody
            (null).build();

        assertThat(request.body()).isEmpty();
    }
}

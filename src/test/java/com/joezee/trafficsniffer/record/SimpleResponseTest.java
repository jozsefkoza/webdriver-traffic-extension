package com.joezee.trafficsniffer.record;

import com.google.common.collect.ImmutableMap;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

/**
 * Unit test for {@link SimpleRequest}.
 *
 * @author JoeZee
 */
public class SimpleResponseTest {
    private static final Map<String, List<String>> NONNULL_HEADERS = ImmutableMap.of();
    private static final String NONNULL_CONTENT = "";
    private static final HttpStatus NONNULL_STATUS = HttpStatus.OK;

    @Test(expectedExceptions = NullPointerException.class)
    public void shouldFailIfStatusIsNull() {
        SimpleResponse.builder().withStatus(null).withHeaders(NONNULL_HEADERS).withContent(NONNULL_CONTENT).build();
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void shouldFailIfHeadersAreNull() {
        SimpleResponse.builder().withStatus(NONNULL_STATUS).withHeaders(null).withContent(NONNULL_CONTENT).build();

    }

    @Test(expectedExceptions = NullPointerException.class)
    public void shouldFailIfContentIsNull() {
        SimpleResponse.builder().withStatus(NONNULL_STATUS).withHeaders(NONNULL_HEADERS).withContent(null).build();
    }
}

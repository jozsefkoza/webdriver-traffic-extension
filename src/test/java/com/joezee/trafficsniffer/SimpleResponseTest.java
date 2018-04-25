package com.joezee.trafficsniffer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.google.common.collect.ImmutableMap;

/**
 * Unit test for {@link SimpleRequest}.
 *
 * @author JoeZee
 */
class SimpleResponseTest {
    private static final Map<String, List<String>> NONNULL_HEADERS = ImmutableMap.of();
    private static final String NONNULL_CONTENT = "";
    private static final HttpStatus NONNULL_STATUS = HttpStatus.OK;

    @Test
    void shouldFailIfStatusIsNull() {
        assertThatThrownBy(() -> SimpleResponse.builder().withStatus(null).withHeaders(NONNULL_HEADERS).withContent(NONNULL_CONTENT).build())
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldFailIfHeadersAreNull() {
        assertThatThrownBy(() -> SimpleResponse.builder().withStatus(NONNULL_STATUS).withHeaders(null).withContent(NONNULL_CONTENT).build())
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldFailIfContentIsNull() {
        assertThatThrownBy(() -> SimpleResponse.builder().withStatus(NONNULL_STATUS).withHeaders(NONNULL_HEADERS).withContent(null).build())
                .isInstanceOf(NullPointerException.class);
    }
}

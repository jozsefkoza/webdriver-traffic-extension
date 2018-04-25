package com.joezee.trafficsniffer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

/**
 * Unit test for {@link SimpleTrafficElement}.
 *
 * @author JoeZee
 */
class SimpleTrafficElementTest {
    private static final LocalDateTime NONULL_TIMESTAMP = LocalDateTime.now();
    private static final TrafficElement.Request NONNULL_REQUEST = mock(TrafficElement.Request.class);
    private static final TrafficElement.Response NONNULL_RESPONSE = mock(TrafficElement.Response.class);

    @Test
    void shouldFailIfTimestampIsNull() {
        assertThatThrownBy(() -> new SimpleTrafficElement(null, NONNULL_REQUEST, NONNULL_RESPONSE))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldFailIfRequestIsNull() {
        assertThatThrownBy(() -> new SimpleTrafficElement(NONULL_TIMESTAMP, null, NONNULL_RESPONSE))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldFailIfResponseIsNull() {
        assertThatThrownBy(() -> new SimpleTrafficElement(NONULL_TIMESTAMP, NONNULL_REQUEST, null))
                .isInstanceOf(NullPointerException.class);
    }
}

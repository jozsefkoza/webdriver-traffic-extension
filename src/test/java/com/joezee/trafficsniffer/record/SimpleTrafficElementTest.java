package com.joezee.trafficsniffer.record;

import org.testng.annotations.Test;

import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;

/**
 * Unit test for {@link SimpleTrafficElement}.
 *
 * @author JoeZee
 */
public class SimpleTrafficElementTest {
    private static final LocalDateTime NONULL_TIMESTAMP = LocalDateTime.now();
    private static final TrafficElement.Request NONNULL_REQUEST = mock(TrafficElement.Request.class);
    private static final TrafficElement.Response NONNULL_RESPONSE = mock(TrafficElement.Response.class);

    @Test(expectedExceptions = NullPointerException.class)
    public void shouldFailIfTimestampIsNull() {
        SimpleTrafficElement.builder().withTimestamp(null).withRequest(NONNULL_REQUEST).withResponse(NONNULL_RESPONSE).build();
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void shouldFailIfRequestIsNull() {
        SimpleTrafficElement.builder().withTimestamp(NONULL_TIMESTAMP).withRequest(null).withResponse(NONNULL_RESPONSE).build();
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void shouldFailIfResponseIsNull() {
        SimpleTrafficElement.builder().withTimestamp(NONULL_TIMESTAMP).withRequest(NONNULL_REQUEST).withResponse(null).build();
    }
}

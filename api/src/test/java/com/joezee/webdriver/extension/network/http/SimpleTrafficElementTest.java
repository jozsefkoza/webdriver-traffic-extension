package com.joezee.webdriver.extension.network.http;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

/**
 * Unit test for {@link SimpleTrafficElement}.
 *
 * @author JoeZee
 */
class SimpleTrafficElementTest {
    private static final Request REQUEST = mock(Request.class);
    private static final Response RESPONSE = mock(Response.class);

    @Test
    void shouldFailIfRequestIsNull() {
        assertThatThrownBy(() -> new SimpleTrafficElement(null, RESPONSE))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldFailIfResponseIsNull() {
        assertThatThrownBy(() -> new SimpleTrafficElement(REQUEST, null))
            .isInstanceOf(NullPointerException.class);
    }
}

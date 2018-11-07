package com.joezee.webdriver.extension.network.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.joezee.webdriver.extension.network.model.TrafficElement.Response;

/**
 * Unit test for {@link SimpleRequest}.
 *
 * @author JoeZee
 */
class SimpleResponseTest {
    private static final int STATUS = 1;
    private static final Map<String, List<String>> HEADERS = Collections.emptyMap();
    private static final String BODY = "";

    @Test
    void shouldHaveZeroDefaultStatus() {
        SimpleResponse response = SimpleResponse.builder().headers(HEADERS).body(BODY).build();

        assertThat(response.status()).isEqualTo(0);
    }

    @Test
    void shouldFailIfHeadersAreNull() {
        assertThatThrownBy(() -> SimpleResponse.builder().status(STATUS).headers(null).body(BODY).build())
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldNotFailIfBodyIsNull() {
        Response response = SimpleResponse.builder().status(STATUS).headers(HEADERS).body(null).build();

        assertThat(response.body()).isEmpty();
    }
}

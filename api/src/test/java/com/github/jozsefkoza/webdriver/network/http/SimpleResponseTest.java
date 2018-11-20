package com.github.jozsefkoza.webdriver.network.http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

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
        Response response = SimpleResponse.builder().headers(HEADERS).body(BODY).build();

        assertThat(response.statusCode()).isEqualTo(0);
    }

    @Test
    void shouldFailIfHeadersAreNull() {
        assertThatThrownBy(() -> SimpleResponse.builder().statusCode(STATUS).headers(null).body(BODY).build())
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldNotFailIfBodyIsNull() {
        Response response = SimpleResponse.builder().statusCode(STATUS).headers(HEADERS).body(null).build();

        assertThat(response.body()).isEmpty();
    }
}

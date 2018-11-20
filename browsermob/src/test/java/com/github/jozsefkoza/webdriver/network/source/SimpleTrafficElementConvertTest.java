package com.github.jozsefkoza.webdriver.network.source;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.util.List;

import com.github.jozsefkoza.webdriver.network.TestResourceReader;
import com.github.jozsefkoza.webdriver.network.http.TrafficElement;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.lightbody.bmp.core.har.HarEntry;
import org.junit.jupiter.api.Test;

import com.github.jozsefkoza.webdriver.network.http.Request;
import com.github.jozsefkoza.webdriver.network.http.Response;

/**
 * Unit test for {@link SimpleTrafficElementConvert}.
 *
 * @author JoeZee
 */
class SimpleTrafficElementConvertTest {

    private final SimpleTrafficElementConvert converter = new SimpleTrafficElementConvert();

    @Test
    void shouldConvertHarEntryToTrafficElement() {
        TrafficElement trafficElement = converter.apply(TestResourceReader.read("har_entry.json", HarEntry.class));

        assertThat(trafficElement).isEqualTo(TrafficElement.simple(request(), response()));
    }

    private Request request() {
        return Request.simpleRequest()
            .method("GET")
            .url(URI.create("http://www.softwareishard.com/blog/har-12-spec/?a=1&b=2"))
            .headers(ImmutableMap.<String, List<String>>builder()
                .put("Accept-Encoding", ImmutableList.of("gzip, deflate"))
                .put("Host", ImmutableList.of("www.softwareishard.com"))
                .put("Cookie", ImmutableList.of("__utma=57327400.108880243.1520934914.1520934914.1524223067.2"))
                .build())
            .build();
    }

    private Response response() {
        return Response.simpleResponse()
            .statusCode(200)
            .headers(ImmutableMap.<String, List<String>>builder()
                .put("Date", ImmutableList.of("Fri, 20 Apr 2018 11:18:06 GMT"))
                .put("Content-Encoding", ImmutableList.of("gzip"))
                .put("Set-Cookie", ImmutableList.of("asd", "123"))
                .build())
            .body("content")
            .build();
    }
}

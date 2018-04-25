package com.joezee.trafficsniffer;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.joezee.trafficsniffer.TrafficElement.Request;
import com.joezee.trafficsniffer.TrafficElement.Response;
import net.lightbody.bmp.core.har.HarEntry;

/**
 * Unit test for {@link HarEntryToTrafficElementConverter}.
 *
 * @author JoeZee
 */
class HarEntryToTrafficElementConverterTest {
    private static final HarEntry HAR_ENTRY = TestResourceUtils.getTestData("test_har_entry.json", HarEntry.class);

    private final HarEntryToTrafficElementConverter converter = new HarEntryToTrafficElementConverter();

    @Test
    void shouldConvertHarEntryToTrafficElement() {
        TrafficElement trafficElement = converter.apply(HAR_ENTRY);

        SimpleTrafficElement expected = new SimpleTrafficElement(timestamp(), request(), response());

        assertThat(trafficElement).isEqualTo(expected);
    }

    private LocalDateTime timestamp() {
        return ZonedDateTime.parse("2018-04-20T11:18:06.880Z").withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }

    private Request request() {
        return SimpleRequest.builder().withMethod(HttpMethod.GET)
                .withUrl(URI.create("http://www.softwareishard.com/blog/har-12-spec/?a=1&b=2"))
                .withHeaders(ImmutableMap.of(
                        "Accept-Encoding", ImmutableList.of("gzip, deflate"),
                        "Host", ImmutableList.of("www.softwareishard.com"),
                        "Cookie", ImmutableList.of("__utma=57327400.108880243.1520934914.1520934914.1524223067.2")))
                .build();
    }

    private Response response() {
        return SimpleResponse.builder().withStatus(HttpStatus.OK)
                .withHeaders(ImmutableMap.of(
                        "Date", ImmutableList.of("Fri, 20 Apr 2018 11:18:06 GMT"),
                        "Content-Encoding", ImmutableList.of("gzip"),
                        "Set-Cookie", ImmutableList.of("asd", "123")
                ))
                .withContent("content")
                .build();
    }
}

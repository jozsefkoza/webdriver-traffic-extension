package com.joezee.trafficsniffer.record;

import com.google.common.collect.ImmutableList;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.core.har.HarRequest;
import net.lightbody.bmp.core.har.HarResponse;
import org.mockito.Mock;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap.SimpleEntry;
import java.util.Date;
import java.util.Optional;

import static com.joezee.trafficsniffer.TestResourceUtils.getTestHarEntry;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Unit test for {@link HarEentryToTrafficElementConverter}.
 *
 * @author JoeZee
 */
public class HarEentryToTrafficElementConverterTest {
    private static final Date EPOCH_DATE = new Date();

    private HarEentryToTrafficElementConverter converter = new HarEentryToTrafficElementConverter();

    @Mock
    private HarEntry harEntry;
    @Mock
    private HarRequest request;
    @Mock
    private HarResponse response;

    @BeforeMethod
    public void setUp() {
        initMocks(this);
        when(harEntry.getRequest()).thenReturn(request);
        when(harEntry.getResponse()).thenReturn(response);
        when(harEntry.getStartedDateTime()).thenReturn(EPOCH_DATE);
    }

    @Test
    public void shouldConvertHarEntry() {
        TrafficElement trafficElement = converter.apply(getTestHarEntry());

        assertThat(trafficElement).isInstanceOf(SimpleTrafficElement.class);
        assertThat(trafficElement.timestamp()).isEqualTo(parseTimestamp("2018-03-17T16:20:39.466Z"));
        assertThat(trafficElement.request().method()).isEqualTo(HttpMethod.GET);
        assertThat(trafficElement.request().url()).isEqualTo(URI.create("https://www.google.hu/?gfe_rd=cr&dcr=0&ei=V0CtWpePFqXL8gelrLuoDQ"));
        assertThat(trafficElement.request().headers()).contains(new SimpleEntry<>("upgrade-insecure-requests", ImmutableList.of("1")));
        assertThat(trafficElement.request().body()).isEqualTo(Optional.empty());
        assertThat(trafficElement.response().status()).isEqualTo(HttpStatus.OK);
        assertThat(trafficElement.response().headers()).contains(new SimpleEntry<>("server", ImmutableList.of("gws")));
        assertThat(trafficElement.response().content()).isEqualTo("<!doctype html><html></html>");
    }

    private LocalDateTime parseTimestamp(String timestamp) {
        Instant dateInstant = Instant.from(DateTimeFormatter.ISO_INSTANT.parse(timestamp));
        return LocalDateTime.ofInstant(dateInstant, ZoneId.systemDefault());
    }
}

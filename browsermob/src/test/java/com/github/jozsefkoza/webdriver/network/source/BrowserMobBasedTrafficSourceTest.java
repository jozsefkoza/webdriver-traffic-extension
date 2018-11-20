package com.github.jozsefkoza.webdriver.network.source;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.stream.Stream;

import com.github.jozsefkoza.webdriver.network.http.TrafficElement;
import com.google.common.collect.ImmutableList;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

/**
 * Unit test for {@link BrowserMobBasedTrafficSource}.
 *
 * @author Jozsef_Koza
 */
class BrowserMobBasedTrafficSourceTest {

    @Mock
    private BrowserMobProxy proxy;
    @Mock
    private Har har;
    @Mock
    private HarLog harLog;

    private BrowserMobBasedTrafficSource trafficSource;

    @BeforeEach
    void setUp() {
        initMocks(this);
        when(proxy.getHar()).thenReturn(har);
        when(har.getLog()).thenReturn(harLog);
        when(harLog.getEntries()).thenReturn(ImmutableList.of());
        trafficSource = new BrowserMobBasedTrafficSource(proxy);
    }

    @Test
    void shouldReturnNewStreamForEachInvocation() {
        Stream<TrafficElement> first = trafficSource.traffic();
        Stream<TrafficElement> second = trafficSource.traffic();

        assertThat(second).isNotSameAs(first);
    }
}

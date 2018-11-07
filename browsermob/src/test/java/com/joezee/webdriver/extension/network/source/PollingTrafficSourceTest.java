package com.joezee.webdriver.extension.network.source;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.byLessThan;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import com.google.common.base.Stopwatch;
import io.reactivex.Observable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joezee.webdriver.extension.network.model.TrafficElement;

/**
 * Unit test for {@link PollingTrafficSource}.
 *
 * @author Jozsef_Koza
 */
class PollingTrafficSourceTest {
    private static final Logger LOG = LoggerFactory.getLogger(PollingTrafficSourceTest.class);
    private static final int TRAFFIC_ELEMENT_COUNT = 10;
    private static final long ADDITIONAL_DELAY = 200L;
    private static final long ELAPSED_TIME_TOLERANCE = 50L;

    @Mock
    private TrafficSource trafficSource;
    private PollingTrafficSource pollingTrafficSource;

    @BeforeEach
    void setUp() {
        initMocks(this);
        AtomicLong delay = new AtomicLong(0);
        List<TrafficElement> traffic = new ArrayList<>();
        Observable.range(0, TRAFFIC_ELEMENT_COUNT)
            .flatMap(accumulator -> {
                long d = delay.updateAndGet(previousDelay -> previousDelay + accumulator * ADDITIONAL_DELAY);
                LOG.info("Emit new traffic item after {} ms", d);
                return Observable.timer(d, TimeUnit.MILLISECONDS);
            })
            .map(signal -> mock(TrafficElement.class))
            .doOnEach(signal -> LOG.info("Emit new traffic element"))
            .forEach(traffic::add);

        when(trafficSource.get()).then(invocation -> traffic.stream());
        pollingTrafficSource = new PollingTrafficSource(trafficSource);
    }

    @Test
    void shouldPollForTrafficElements() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Stream<TrafficElement> trafficElements = pollingTrafficSource.get();
        stopwatch.stop();
        long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);

        assertThat(trafficElements).hasSize(5);
        assertThat(elapsed).isCloseTo(2500L, byLessThan(ELAPSED_TIME_TOLERANCE));
    }
}

package com.joezee.trafficsniffer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Optional;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import net.lightbody.bmp.core.har.HarEntry;

/**
 * Unit test for {@link FailsafeHarEntryToTrafficElementConverter}.
 *
 * @author JoeZee
 */
class FailsafeHarEntryToTrafficElementConverterTest {

    @Mock
    private Function<HarEntry, TrafficElement> converter;
    @Mock
    private HarEntry harEntry;

    private FailsafeHarEntryToTrafficElementConverter failsafeConverter;

    @BeforeEach
    void setUp() {
        initMocks(this);
        failsafeConverter = new FailsafeHarEntryToTrafficElementConverter(converter);
    }

    @Test
    void shouldReturnEmptyIfConversionFails() {
        when(converter.apply(any())).thenThrow(new IllegalArgumentException());

        Optional<TrafficElement> trafficElement = failsafeConverter.apply(harEntry);

        assertThat(trafficElement).isEmpty();
    }

    @Test
    void shouldReturnConvertedValue() {
        when(converter.apply(any())).thenReturn(mock(TrafficElement.class));

        Optional<TrafficElement> trafficElement = failsafeConverter.apply(harEntry);

        assertThat(trafficElement).isNotEmpty();
    }

}

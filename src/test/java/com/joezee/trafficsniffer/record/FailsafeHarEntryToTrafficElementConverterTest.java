package com.joezee.trafficsniffer.record;

import net.lightbody.bmp.core.har.HarEntry;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Unit test for {@link FailsafeHarEntryToTrafficElementConverter}.
 *
 * @author JoeZee
 */
public class FailsafeHarEntryToTrafficElementConverterTest {

    @Mock
    private Function<HarEntry, TrafficElement> converter;
    @Mock
    private HarEntry harEntry;

    private FailsafeHarEntryToTrafficElementConverter failsafeConverter;

    @BeforeMethod
    public void setUp() {
        initMocks(this);
        failsafeConverter = new FailsafeHarEntryToTrafficElementConverter(converter);
    }

    @Test
    public void shouldReturnEmptyIfConversionFails() {
        when(converter.apply(any())).thenThrow(new IllegalArgumentException());

        Optional<TrafficElement> trafficElement = failsafeConverter.apply(harEntry);

        assertThat(trafficElement).isEmpty();
    }

    @Test
    public void shouldReturnConvertedValue() {
        when(converter.apply(any())).thenReturn(mock(TrafficElement.class));

        Optional<TrafficElement> trafficElement = failsafeConverter.apply(harEntry);

        assertThat(trafficElement).isNotEmpty();
    }

}

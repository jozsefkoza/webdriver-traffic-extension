package com.github.jozsefkoza.webdriver.network.source;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.core.har.HarLog;
import net.lightbody.bmp.proxy.CaptureType;

import com.github.jozsefkoza.webdriver.network.http.TrafficElement;

/**
 * A {@link TrafficSource} which returns all the captured traffic using {@link BrowserMobProxy}.
 *
 * <p>Note that har capturing should be enabled with {@link BrowserMobProxy#enableHarCaptureTypes(CaptureType...)} in
 * order for the traffic not to be empty.
 */
public final class BrowserMobBasedTrafficSource implements TrafficSource {

    private final BrowserMobProxy proxy;
    private Function<HarEntry, TrafficElement> harEntryConverter = new SimpleTrafficElementConvert();

    public BrowserMobBasedTrafficSource(BrowserMobProxy proxy) {
        this.proxy = requireNonNull(proxy);
    }

    /**
     * Get the traffic captured by {@link BrowserMobProxy#getHar()}. Each invocation on this method causes a new har
     * capturing to happen.
     *
     * @return the captured traffic stream
     */
    @Override
    public Stream<TrafficElement> traffic() {
        return Optional.ofNullable(proxy.getHar())
            .map(Har::getLog)
            .map(HarLog::getEntries)
            .orElse(Collections.emptyList())
            .stream()
            .map(harEntryConverter)
            .filter(Objects::nonNull);
    }

    /**
     * Set the converter function to transform har entries captured by {@link BrowserMobProxy} to traffic elements.
     *
     * @param harEntryConverter the converter function
     */
    public void setHarEntryConverter(Function<HarEntry, TrafficElement> harEntryConverter) {
        this.harEntryConverter = requireNonNull(harEntryConverter);
    }
}

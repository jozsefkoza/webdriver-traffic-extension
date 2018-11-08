package com.joezee.webdriver.extension.network.source;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.core.har.HarLog;

import com.joezee.webdriver.extension.network.http.TrafficElement;

/**
 * A {@link TrafficSource} which returns all the captured traffic by {@link BrowserMobProxy}.
 *
 * @author JoeZee
 */
public final class BrowserMobBasedTrafficSource implements TrafficSource {
    private static final Function<HarEntry, Optional<TrafficElement>> TO_OPTIONAL_TRAFFIC_ELEMENT =
            new FailsafeHarEntryToTrafficElementConverter(new HarEntryToTrafficElementConverter());

    private final BrowserMobProxy proxy;

    BrowserMobBasedTrafficSource(BrowserMobProxy proxy) {
        this.proxy = requireNonNull(proxy);
    }

    @Override
    public Stream<TrafficElement> getTraffic() {
        return Optional.ofNullable(proxy.getHar())
                .map(Har::getLog)
                .map(HarLog::getEntries)
                .orElse(emptyList())
                .stream()
                .map(TO_OPTIONAL_TRAFFIC_ELEMENT)
                .filter(Optional::isPresent)
                .map(Optional::get);
    }
}

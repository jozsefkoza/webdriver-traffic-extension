package com.joezee.trafficsniffer.record;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarLog;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

/**
 * A {@link TrafficSnapshotter} that returns all the captured traffic as a {@link Snapshot}.
 *
 * @author JoeZee
 */
public final class SimpleTrafficSnapshotter implements TrafficSnapshotter {

    private final HarEentryToTrafficElementConverter harEentryToTrafficElement = new HarEentryToTrafficElementConverter();
    private final BrowserMobProxy proxy;

    SimpleTrafficSnapshotter(BrowserMobProxy proxy) {
        this.proxy = requireNonNull(proxy);
    }

    @Override
    public Snapshot takeSnapshot() {
        List<TrafficElement> trafficElements = Optional.ofNullable(proxy.getHar())
            .map(Har::getLog)
            .map(HarLog::getEntries)
            .orElse(emptyList())
            .stream()
            .map(harEentryToTrafficElement)
            .collect(toList());
        return Snapshot.of(trafficElements);
    }
}

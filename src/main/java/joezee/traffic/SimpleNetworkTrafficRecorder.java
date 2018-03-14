package joezee.traffic;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarLog;

/**
 * A {@link NetworkTrafficRecorder} that returns all the captured traffic as a collection of {@link TrafficElement}s.
 *
 * @author Jozsef_Koza
 */
public final class SimpleNetworkTrafficRecorder implements NetworkTrafficRecorder {

    private final HarEentryToTrafficElementConverter harEentryToTrafficElement = new HarEentryToTrafficElementConverter();
    private final BrowserMobProxy proxy;

    SimpleNetworkTrafficRecorder(BrowserMobProxy proxy) {
        this.proxy = requireNonNull(proxy);
    }

    @Override
    public Collection<TrafficElement> recordTraffic() {
        return Optional.ofNullable(proxy.getHar())
            .map(Har::getLog)
            .map(HarLog::getEntries)
            .orElse(emptyList())
            .stream()
            .map(harEentryToTrafficElement)
            .collect(toList());
    }

    @Override
    public void restartCapturing() {
        proxy.newHar(UUID.randomUUID().toString());
    }
}

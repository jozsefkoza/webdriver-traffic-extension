package com.joezee.webdriver.extension.network.source;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import com.google.common.base.Preconditions;
import io.reactivex.Flowable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joezee.webdriver.extension.network.model.TrafficElement;

/**
 * Gets a snapshot of the captured network traffic.
 *
 * <p>This class polls the network traffic in regular intervals for a certain amount of time, or if not any new entries
 * can be captured in the traffic after two consecutive poll. Both the polling interval and the max amount of time of
 * polling is configurable. By default, traffic is polled for at most 10 seconds with 500 milliseconds intervals.
 *
 * @author JoeZee
 */
public final class PollingTrafficSource implements TrafficSource {
    private static final Logger LOG = LoggerFactory.getLogger(PollingTrafficSource.class);
    private static final long DEFAULT_MAX_POLLING_TIME_IN_MILLIS = 10000;
    private static final long DEFAULT_POLLING_INTERVAL_IN_MILLIS = 500;

    private final TrafficSource trafficSource;

    private long maxPollingTime = DEFAULT_MAX_POLLING_TIME_IN_MILLIS;
    private long pollingInterval = DEFAULT_POLLING_INTERVAL_IN_MILLIS;

    public PollingTrafficSource(TrafficSource trafficSource) {
        this.trafficSource = requireNonNull(trafficSource);
    }

    @Override
    public Stream<TrafficElement> getTraffic() {
        Snapshot snapshot = pollTraffic();
        LOG.debug("Captured {} traffic elements", snapshot.trafficElements.size());
        return snapshot.trafficElements.stream();
    }

    /**
     * Set the maximum time in milliseconds to poll the traffic. The value must be non-negative.
     *
     * @param maxPollingTime the maximum time to poll the traffic
     * @throws IllegalArgumentException if the time supplied is negative
     */
    public void setMaxPollingTimeInMillis(long maxPollingTime) {
        Preconditions.checkArgument(maxPollingTime >= 0, "Max polling time must be non-negative");
        this.maxPollingTime = maxPollingTime;
    }

    /**
     * Set the delay between two consecutive polls in milliseconds. The value must be positive.
     *
     * @param pollingInterval the maximum time to poll the traffic
     * @throws IllegalArgumentException if the time supplied is not a positive number
     */
    public void setPollingIntervalInMillis(long pollingInterval) {
        checkArgument(pollingInterval > 0, "Polling interval must be a positive number");
        this.pollingInterval = pollingInterval;
    }

    private Snapshot pollTraffic() {
        LOG.debug("Poll for traffic elements for {} milliseconds with {} milliseconds interval", maxPollingTime,
            pollingInterval);
        AtomicReference<Snapshot> latestSnapshot = new AtomicReference<>(Snapshot.EMPTY);
        return Flowable.intervalRange(0, getPollCount(), 0, pollingInterval, TimeUnit.MILLISECONDS)
            .doOnNext(i -> LOG.debug("Polling for traffic elements since {} milliseconds", i * pollingInterval))
            .map(i -> Snapshot.create(trafficSource))
            .takeWhile(snapshot -> snapshot.capturedElementCount() > latestSnapshot.getAndSet(snapshot)
                .capturedElementCount())
            .blockingLast();
    }

    private long getPollCount() {
        long pollCount = 1;
        return Math.max(pollCount, maxPollingTime / pollingInterval);
    }

    /**
     * Represents a snapshot of the captured network traffic.
     */
    private static final class Snapshot {
        private static final Snapshot EMPTY = new Snapshot(Collections.emptyList());

        private final Collection<TrafficElement> trafficElements;

        private Snapshot(Collection<TrafficElement> trafficElements) {
            this.trafficElements = requireNonNull(trafficElements);
        }

        static Snapshot create(TrafficSource trafficSource) {
            List<TrafficElement> trafficElements = trafficSource.get().collect(toList());
            Snapshot snapshot = EMPTY;
            if (trafficElements != null && !trafficElements.isEmpty()) {
                snapshot = new Snapshot(trafficElements);
            }
            return snapshot;
        }

        int capturedElementCount() {
            return trafficElements.size();
        }
    }
}


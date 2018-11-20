package com.github.jozsefkoza.webdriver.network.source;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import io.reactivex.Flowable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jozsefkoza.webdriver.network.http.TrafficElement;

/**
 * Gets a snapshot of the captured network traffic.
 *
 * <p>This class polls the network traffic in regular intervals for a certain amount of time, or if not any new entries
 * can be captured in the traffic after two consecutive poll. Both the polling interval and the max amount of time of
 * polling is configurable. By default, traffic is polled for at most 10 seconds with 500 milliseconds intervals.
 */
public final class PollingTrafficSource implements TrafficSource {
    private static final Logger LOG = LoggerFactory.getLogger(PollingTrafficSource.class);
    private static final long DEFAULT_MAX_POLLING_TIME_IN_MILLIS = 10000;
    private static final long DEFAULT_POLLING_INTERVAL_IN_MILLIS = 500;

    private final TrafficSource trafficSource;

    private long maxPollingTime = DEFAULT_MAX_POLLING_TIME_IN_MILLIS;
    private long pollingInterval = DEFAULT_POLLING_INTERVAL_IN_MILLIS;

    /**
     * Create a new polling traffic source from the argument one with default poll timeout and period.
     *
     * @param trafficSource the base traffic source to use in polling
     */
    public PollingTrafficSource(TrafficSource trafficSource) {
        this.trafficSource = requireNonNull(trafficSource);
    }

    /**
     * Poll the traffic for traffic elements in a blocking way.
     */
    @Override
    public Stream<TrafficElement> traffic() {
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
        checkArgument(maxPollingTime >= 0, "Max polling time must be non-negative");
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
        private static final Snapshot EMPTY = new Snapshot(emptyList());

        private final Collection<TrafficElement> trafficElements;

        private Snapshot(Collection<TrafficElement> trafficElements) {
            this.trafficElements = requireNonNull(trafficElements);
        }

        static Snapshot create(TrafficSource trafficSource) {
            List<TrafficElement> trafficElements = trafficSource.traffic().collect(toList());
            Snapshot snapshot = EMPTY;
            if (!trafficElements.isEmpty()) {
                snapshot = new Snapshot(trafficElements);
            }
            return snapshot;
        }

        int capturedElementCount() {
            return trafficElements.size();
        }
    }
}


package com.joezee.trafficsniffer.record;

import io.reactivex.Flowable;
import net.lightbody.bmp.BrowserMobProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Gets a snapshot of the captured network traffic.
 * <p>
 * This class polls the network traffic in regular intervals for a certain amount of time, or if not any
 * new entries can be captured in the traffic after two consecutive poll. Both the polling interval and the max amount of time of polling is
 * configurable. By default, traffic is
 * polled for at most 10 seconds with 500 milliseconds intervals.
 *
 * @author JoeZee
 */
public final class PollingTrafficSnapshotter implements TrafficSnapshotter {
    public static final long DEFAULT_MAX_POLLING_TIME_IN_MILLIS = 10000;
    public static final long DEFAULT_POLLING_INTERVAL_IN_MILLIS = 500;

    private static final Logger LOG = LoggerFactory.getLogger(PollingTrafficSnapshotter.class);

    private final SimpleTrafficSnapshotter simpleTrafficSnapshotter;

    private long maxPollingTime = DEFAULT_MAX_POLLING_TIME_IN_MILLIS;
    private long pollingInterval = DEFAULT_POLLING_INTERVAL_IN_MILLIS;

    PollingTrafficSnapshotter(BrowserMobProxy proxy) {
        simpleTrafficSnapshotter = new SimpleTrafficSnapshotter(proxy);
    }

    @Override
    public Snapshot takeSnapshot() {
        Snapshot snapshot;
        if (maxPollingTime > 0 && pollingInterval > 0) {
            snapshot = pollTraffic();
        } else {
            snapshot = simpleTrafficSnapshotter.takeSnapshot();
        }
        LOG.debug("Captured {} traffic elements", snapshot.size());
        return snapshot;
    }

    private Snapshot pollTraffic() {
        LOG.debug("Poll for traffic elements for {} milliseconds with {} milliseconds interval", maxPollingTime, pollingInterval);
        AtomicReference<Snapshot> latestSnapshot = new AtomicReference<>(Snapshot.empty());
        return Flowable.intervalRange(0, maxPollingTime / pollingInterval, 0, pollingInterval, TimeUnit.MILLISECONDS)
            .doOnNext(i -> LOG.debug("Polling for traffic elements since {} milliseconds", i * pollingInterval))
            .map(i -> simpleTrafficSnapshotter.takeSnapshot())
            .takeWhile(snapshot -> snapshot.hasMoreRecordedRequestsThan(latestSnapshot.getAndSet(snapshot)))
            .blockingLast();
    }

    public void setMaxPollingTimeInMillis(long maxPollingTime) {
        this.maxPollingTime = maxPollingTime;
    }

    public void setPollingIntervalInMillis(long pollingInterval) {
        this.pollingInterval = pollingInterval;
    }

}

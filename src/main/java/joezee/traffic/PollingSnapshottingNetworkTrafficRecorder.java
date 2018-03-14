package joezee.traffic;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Flowable;
import net.lightbody.bmp.BrowserMobProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Gets a snapshot of the captured network traffic.
 * <p>
 * This class polls the network traffic in regular intervals for a certain amount of time, or if not any
 * new entries can be captured in the traffic after two consecutive poll. Both the polling interval and the max amount of time of polling is configurable. By default, traffic is
 * polled for at most 10 seconds with 500 milliseconds intervals.
 *
 * @author Jozsef_Koza
 */
public final class PollingSnapshottingNetworkTrafficRecorder implements NetworkTrafficRecorder {
    public static final long DEFAULT_MAX_POLLING_TIME_IN_MILLIS = 10000;
    public static final long DEFAULT_POLLING_INTERVAL_IN_MILLIS = 500;

    private static final Logger LOG = LoggerFactory.getLogger(PollingSnapshottingNetworkTrafficRecorder.class);
    private final NetworkTrafficRecorder simpleNetworkTrafficRecorder;

    private long maxPollingTime = DEFAULT_MAX_POLLING_TIME_IN_MILLIS;
    private long pollingInterval = DEFAULT_POLLING_INTERVAL_IN_MILLIS;

    public PollingSnapshottingNetworkTrafficRecorder(BrowserMobProxy proxy) {
        simpleNetworkTrafficRecorder = new SimpleNetworkTrafficRecorder(proxy);
    }

    @Override
    public Collection<TrafficElement> recordTraffic() {
        Snapshot trafficSnapshot;
        if (maxPollingTime > 0 && pollingInterval > 0) {
            trafficSnapshot = pollTraffic();
        } else {
            trafficSnapshot = takeSnapshot();
        }
        Collection<TrafficElement> result = trafficSnapshot.capturedTraffic();
        LOG.debug("Captured {} traffic elements", result.size());
        return result;
    }

    @Override
    public void restartCapturing() {
        simpleNetworkTrafficRecorder.restartCapturing();
    }

    private Snapshot pollTraffic() {
        LOG.debug("Poll for traffic elements for {} milliseconds with {} milliseconds interval", maxPollingTime, pollingInterval);
        AtomicReference<Snapshot> latestSnapshot = new AtomicReference<>(Snapshot.EMPTY);
        return Flowable.intervalRange(0, maxPollingTime / pollingInterval, 0, pollingInterval, TimeUnit.MILLISECONDS)
            .doOnNext(i -> LOG.debug("Polling for traffic elements since {} milliseconds", i * pollingInterval))
            .map(i -> takeSnapshot())
            .takeWhile(snapshot -> snapshot.hasMoreRecordedRequestsThan(latestSnapshot.getAndSet(snapshot)))
            .blockingLast();
    }

    private Snapshot takeSnapshot() {
        return new Snapshot(simpleNetworkTrafficRecorder.recordTraffic());
    }

    public void setMaxPollingTimeInMillis(long maxPollingTime) {
        this.maxPollingTime = maxPollingTime;
    }

    public void setPollingIntervalInMillis(long pollingInterval) {
        this.pollingInterval = pollingInterval;
    }

    private static final class Snapshot {

        private static final Snapshot EMPTY = new Snapshot(Collections.emptyList());
        private final Collection<TrafficElement> capturedTraffic;

        Snapshot(Collection<TrafficElement> capturedTraffic) {
            this.capturedTraffic = requireNonNull(capturedTraffic);
        }

        boolean hasMoreRecordedRequestsThan(Snapshot other) {
            return capturedTraffic.size() > other.capturedTraffic.size();
        }

        Collection<TrafficElement> capturedTraffic() {
            return capturedTraffic;
        }
    }
}

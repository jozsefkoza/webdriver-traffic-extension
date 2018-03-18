package com.joezee.trafficsniffer.record;

import net.lightbody.bmp.BrowserMobProxy;

/**
 * Factory for creating {@link TrafficSnapshotter} instances.
 *
 * @author JoeZee
 */
public final class PollingTrafficSnapshotterFactory {
    private static final TrafficSnapshotter DUMMY_SNAPSHOT_CREATOR = new EmptyTrafficSnapshotter();

    private long trafficPollingInterval = PollingTrafficSnapshotter.DEFAULT_POLLING_INTERVAL_IN_MILLIS;
    private long trafficMaxPollingTime = PollingTrafficSnapshotter.DEFAULT_MAX_POLLING_TIME_IN_MILLIS;

    /**
     * Create a {@link PollingTrafficSnapshotter} for a {@link BrowserMobProxy} proxy or a {@link EmptyTrafficSnapshotter}.
     *
     * @return the traffic service for the web page
     */
    public TrafficSnapshotter createFor(BrowserMobProxy proxy) {
        TrafficSnapshotter snapshotCreator = DUMMY_SNAPSHOT_CREATOR;
        if (proxy != null) {
            PollingTrafficSnapshotter pollingSnapshotter = new PollingTrafficSnapshotter(proxy);
            pollingSnapshotter.setMaxPollingTimeInMillis(trafficMaxPollingTime);
            pollingSnapshotter.setPollingIntervalInMillis(trafficPollingInterval);
            snapshotCreator = pollingSnapshotter;
        }
        return snapshotCreator;
    }

    public void setTrafficPollingIntervalInMillis(long pollingInterval) {
        this.trafficPollingInterval = pollingInterval;
    }

    public void setTrafficMaxPollingTimeInMillis(long maxPollingTime) {
        this.trafficMaxPollingTime = maxPollingTime;
    }
}

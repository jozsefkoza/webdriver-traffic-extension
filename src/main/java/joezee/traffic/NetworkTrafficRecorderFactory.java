package joezee.traffic;

import net.lightbody.bmp.BrowserMobProxy;

/**
 * Factory for creating {@link NetworkTrafficRecorder} instances.
 *
 * @author Jozsef_Koza
 */
public final class NetworkTrafficRecorderFactory {
    private static final NetworkTrafficRecorder DUMMY_RECORDER = new NoopNetworkTrafficRecorder();

    private long trafficPollingInterval = PollingSnapshottingNetworkTrafficRecorder.DEFAULT_POLLING_INTERVAL_IN_MILLIS;
    private long trafficMaxPollingTime = PollingSnapshottingNetworkTrafficRecorder.DEFAULT_MAX_POLLING_TIME_IN_MILLIS;

    /**
     * Create a {@link NetworkTrafficRecorder} for a web driver instance.
     * <p>
     * If a web driver proxy is available - therefore network can be sniffed -, a
     * {@link PollingSnapshottingNetworkTrafficRecorder} will be created wrapped in a {@link CachingNetworkTrafficRecorder}. Otherwise, a dummy {@link NoopNetworkTrafficRecorder}
     * will be returned.
     *
     * @return the traffic service for the web page
     */
    public NetworkTrafficRecorder createFor(BrowserMobProxy proxy) {
        NetworkTrafficRecorder service = DUMMY_RECORDER;
        if (proxy != null) {
            PollingSnapshottingNetworkTrafficRecorder pollingService = new PollingSnapshottingNetworkTrafficRecorder(proxy);
            pollingService.setMaxPollingTimeInMillis(trafficMaxPollingTime);
            pollingService.setPollingIntervalInMillis(trafficPollingInterval);
            service = new CachingNetworkTrafficRecorder(pollingService);
        }
        return service;
    }

    public void setTrafficPollingIntervalInMillis(long pollingInterval) {
        this.trafficPollingInterval = pollingInterval;
    }

    public void setTrafficMaxPollingTimeInMillis(long maxPollingTime) {
        this.trafficMaxPollingTime = maxPollingTime;
    }
}

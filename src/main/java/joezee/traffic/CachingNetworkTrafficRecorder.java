package joezee.traffic;

import static java.util.Objects.requireNonNull;

import java.util.Collection;

/**
 * {@link NetworkTrafficRecorder} that caches the recorded traffic until the capturing is not restarted.
 *
 * @author Jozsef_Koza
 */
public final class CachingNetworkTrafficRecorder implements NetworkTrafficRecorder {

    private final NetworkTrafficRecorder networkTrafficRecorder;
    private Collection<TrafficElement> capturedTraffic;

    public CachingNetworkTrafficRecorder(NetworkTrafficRecorder networkTrafficRecorder) {
        this.networkTrafficRecorder = requireNonNull(networkTrafficRecorder);
    }

    @Override
    public Collection<TrafficElement> recordTraffic() {
        if (capturedTraffic == null) {
            capturedTraffic = networkTrafficRecorder.recordTraffic();
        }
        return capturedTraffic;
    }

    @Override
    public void restartCapturing() {
        capturedTraffic = null;
        networkTrafficRecorder.restartCapturing();
    }
}

package joezee.traffic;

import static java.util.Objects.requireNonNull;

import java.util.stream.Stream;

/**
 * Simple {@link NetworkTrafficSource} that uses a {@link NetworkTrafficRecorder} as the source of the traffic stream.
 *
 * @author Jozsef_Koza
 */
public class NetworkTrafficRecorderBasedNetworkTrafficSource implements NetworkTrafficSource {

    private final NetworkTrafficRecorder networkTrafficRecorder;

    public NetworkTrafficRecorderBasedNetworkTrafficSource(NetworkTrafficRecorder networkTrafficRecorder) {
        this.networkTrafficRecorder = requireNonNull(networkTrafficRecorder);
    }

    @Override
    public Stream<TrafficElement> getTraffic() {
        return networkTrafficRecorder.recordTraffic().stream();
    }
}

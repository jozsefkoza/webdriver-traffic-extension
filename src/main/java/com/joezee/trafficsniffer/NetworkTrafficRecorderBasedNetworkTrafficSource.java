package com.joezee.trafficsniffer;

import com.joezee.trafficsniffer.record.TrafficElement;
import com.joezee.trafficsniffer.record.TrafficSnapshotter;

import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * Simple {@link NetworkTrafficSource} that uses a {@link TrafficSnapshotter} as the source of the traffic stream.
 *
 * @author JoeZee
 */
public class NetworkTrafficRecorderBasedNetworkTrafficSource implements NetworkTrafficSource {

    private final TrafficSnapshotter trafficSnapshotter;

    public NetworkTrafficRecorderBasedNetworkTrafficSource(TrafficSnapshotter trafficSnapshotter) {
        this.trafficSnapshotter = requireNonNull(trafficSnapshotter);
    }

    @Override
    public Stream<TrafficElement> getTraffic() {
        // TODO expose snapshot
        return Stream.empty();
    }
}

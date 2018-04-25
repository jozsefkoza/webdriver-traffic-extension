package com.joezee.trafficsniffer;

import java.util.stream.Stream;

/**
 * A {@link NetworkTrafficSource} returning empty traffic.
 *
 * @author JoeZee
 */
final class EmptyNetworkTrafficSource implements NetworkTrafficSource {
    @Override
    public Stream<TrafficElement> getTraffic() {
        return Stream.empty();
    }
}

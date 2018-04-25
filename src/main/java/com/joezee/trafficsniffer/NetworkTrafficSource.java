package com.joezee.trafficsniffer;

import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * An object that can get network traffic entries as a stream of {@link TrafficElement}s.
 *
 * @author JoeZee
 */
public interface NetworkTrafficSource extends Supplier<Stream<TrafficElement>> {

    /**
     * Gets the traffic stream.
     *
     * @return the stream of {@link TrafficElement}s
     */
    Stream<TrafficElement> getTraffic();

    @Override
    default Stream<TrafficElement> get() {
        return getTraffic();
    }
}

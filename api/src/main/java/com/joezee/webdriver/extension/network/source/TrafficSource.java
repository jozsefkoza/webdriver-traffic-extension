package com.joezee.webdriver.extension.network.source;

import java.util.function.Supplier;
import java.util.stream.Stream;

import com.joezee.webdriver.extension.network.model.TrafficElement;

/**
 * An object that can get network traffic entries as a stream of {@link TrafficElement}s.
 *
 * @author JoeZee
 */
public interface TrafficSource extends Supplier<Stream<TrafficElement>> {

    static TrafficSource empty() {
        return Stream::empty;
    }

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

package com.joezee.webdriver.extension.network.source;

import java.util.function.Supplier;
import java.util.stream.Stream;

import com.joezee.webdriver.extension.network.http.TrafficElement;

/**
 * An object that can get network traffic entries as a stream of {@link TrafficElement}s.
 *
 * @author JoeZee
 */
public interface TrafficSource extends Supplier<Stream<TrafficElement>> {

    /**
     * Get the traffic stream.
     *
     * @return the stream of {@link TrafficElement}s
     */
    Stream<TrafficElement> traffic();

    @Override
    default Stream<TrafficElement> get() {
        return traffic();
    }
}

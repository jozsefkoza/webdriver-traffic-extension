package com.github.jozsefkoza.webdriver.network.source;

import java.util.stream.Stream;

import com.github.jozsefkoza.webdriver.network.http.TrafficElement;

/**
 * An object that can get network traffic entries as a stream of {@link TrafficElement}s.
 *
 * @author JoeZee
 */
public interface TrafficSource {

    /**
     * Get the traffic stream.
     *
     * @return the stream of {@link TrafficElement}s
     */
    Stream<TrafficElement> traffic();
}

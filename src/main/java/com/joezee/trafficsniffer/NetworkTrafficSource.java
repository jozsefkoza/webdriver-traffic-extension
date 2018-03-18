package com.joezee.trafficsniffer;

import com.joezee.trafficsniffer.record.TrafficElement;
import com.joezee.trafficsniffer.record.TrafficSnapshotter;

import java.util.stream.Stream;

/**
 * An object that can get network traffic entries as a stream of {@link TrafficElement}s preferably through an instance of {@link TrafficSnapshotter}.
 *
 * @author JoeZee
 */
public interface NetworkTrafficSource {

    /**
     * Gets the traffic stream.
     *
     * @return the stream of {@link TrafficElement}s
     */
    Stream<TrafficElement> getTraffic();
}

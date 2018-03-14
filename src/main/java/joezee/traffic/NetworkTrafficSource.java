package joezee.traffic;

import java.util.stream.Stream;

/**
 * An object that can get network traffic entries as a stream of {@link TrafficElement}s preferably through an instance of {@link NetworkTrafficRecorder}.
 *
 * @author Jozsef_Koza
 */
public interface NetworkTrafficSource {

    /**
     * Gets the traffic stream.
     *
     * @return the stream of {@link TrafficElement}s
     */
    Stream<TrafficElement> getTraffic();
}

package joezee.traffic;

import java.util.Collection;

/**
 * Records network traffic as a collection of captured traffic entries.
 *
 * @author Jozsef_Koza
 */
public interface NetworkTrafficRecorder {

    /**
     * Records the captured traffic entries as a stream of {@link TrafficElement}s.
     *
     * @return the traffic elements
     */
    Collection<TrafficElement> recordTraffic();

    /**
     * Restart the capturing of traffic entries dropping all previously captured traffic entries.
     */
    void restartCapturing();
}

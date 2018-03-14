package joezee.traffic;

import java.util.Collection;
import java.util.Collections;

/**
 * Dummy version of {@link NetworkTrafficRecorder} which does "nothing"; returns an empty traffic.
 *
 * @author Jozsef_Koza
 */
final class NoopNetworkTrafficRecorder implements NetworkTrafficRecorder {
    @Override
    public Collection<TrafficElement> recordTraffic() {
        return Collections.emptyList();
    }

    @Override
    public void restartCapturing() {
    }
}

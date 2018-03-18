package com.joezee.trafficsniffer.record;

/**
 * Takes a {@link Snapshot} of the network traffic.
 *
 * @author JoeZee
 */
public interface TrafficSnapshotter {

    /**
     * Creates a snapshot of the traffic.
     *
     * @return the snapshot of the traffic
     */
    Snapshot takeSnapshot();
}

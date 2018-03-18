package com.joezee.trafficsniffer.record;

/**
 * Dummy version of {@link TrafficSnapshotter} which does "nothing"; returns an empty snapshot.
 *
 * @author JoeZee
 */
final class EmptyTrafficSnapshotter implements TrafficSnapshotter {
    @Override
    public Snapshot takeSnapshot() {
        return Snapshot.empty();
    }
}

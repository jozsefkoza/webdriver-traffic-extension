package com.joezee.trafficsniffer.record;

import java.util.Collection;
import java.util.Collections;

import static java.util.Objects.requireNonNull;

/**
 * Represent a snapshot of the network traffic.
 *
 * @author JoeZee
 */
final class Snapshot {
    private static final Snapshot EMPTY = new Snapshot(Collections.emptyList());

    private final Collection<TrafficElement> trafficElements;

    private Snapshot(Collection<TrafficElement> trafficElements) {
        this.trafficElements = requireNonNull(trafficElements);
    }

    public static Snapshot of(Collection<TrafficElement> trafficElements) {
        return new Snapshot(trafficElements);
    }

    public static Snapshot empty() {
        return EMPTY;
    }

    public Collection<TrafficElement> trafficElements() {
        return trafficElements;
    }

    public int size() {
        return trafficElements.size();
    }

    boolean hasMoreRecordedRequestsThan(Snapshot other) {
        return trafficElements.size() > other.trafficElements.size();
    }
}

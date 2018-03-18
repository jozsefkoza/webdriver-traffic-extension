package com.joezee.trafficsniffer.record;

import net.lightbody.bmp.core.har.HarEntry;

import java.util.Optional;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

final class FailsafeHarEntryToTrafficElementConverter implements Function<HarEntry, Optional<TrafficElement>> {

    private final Function<HarEntry, TrafficElement> converter;

    FailsafeHarEntryToTrafficElementConverter(Function<HarEntry, TrafficElement> converter) {
        this.converter = requireNonNull(converter);
    }

    @Override
    public Optional<TrafficElement> apply(HarEntry harEntry) {
        Optional<TrafficElement> result;
        try {
            result = Optional.of(converter.apply(harEntry));
        } catch (Exception e) {
            result = Optional.empty();
        }
        return result;
    }
}

package com.joezee.webdriver.extension.network.model;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Builder
@Accessors(fluent = true)
final class SimpleRequest implements TrafficElement.Request {

    @NonNull
    private final String method;
    @NonNull
    private final URI url;
    @NonNull
    @Builder.Default
    private final Map<String, ? extends Collection<String>> headers = Collections.emptyMap();
    private final String payload;

    @Override
    public Optional<String> payload() {
        return Optional.ofNullable(payload);
    }
}

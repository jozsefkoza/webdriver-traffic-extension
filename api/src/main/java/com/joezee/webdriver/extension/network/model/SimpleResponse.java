package com.joezee.webdriver.extension.network.model;

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
final class SimpleResponse implements TrafficElement.Response {

    @Builder.Default
    private final int status = 0;
    @NonNull
    @Builder.Default
    private final Map<String, ? extends Collection<String>> headers = Collections.emptyMap();
    private final String body;

    @Override
    public Optional<String> body() {
        return Optional.ofNullable(body);
    }
}

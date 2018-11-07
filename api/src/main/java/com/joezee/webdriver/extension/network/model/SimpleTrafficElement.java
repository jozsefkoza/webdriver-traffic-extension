package com.joezee.webdriver.extension.network.model;

import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Accessors(fluent = true)
final class SimpleTrafficElement implements TrafficElement {

    @NonNull
    private final Request request;
    @NonNull
    private final Response response;
}

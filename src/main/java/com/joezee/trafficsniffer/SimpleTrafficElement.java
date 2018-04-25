package com.joezee.trafficsniffer;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Objects;

import com.google.common.base.MoreObjects;

final class SimpleTrafficElement implements TrafficElement {

    private final Request request;
    private final Response response;
    private final LocalDateTime timestamp;

    SimpleTrafficElement(LocalDateTime timestamp, Request request, Response response) {
        this.timestamp = requireNonNull(timestamp);
        this.request = requireNonNull(request);
        this.response = requireNonNull(response);
    }

    @Override
    public LocalDateTime timestamp() {
        return timestamp;
    }

    @Override
    public Request request() {
        return request;
    }

    @Override
    public Response response() {
        return response;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SimpleTrafficElement that = (SimpleTrafficElement) o;
        return Objects.equals(request, that.request)
                && Objects.equals(response, that.response)
                && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(request, response, timestamp);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("timestamp", timestamp)
                .add("request", request)
                .add("response", response)
                .toString();
    }
}

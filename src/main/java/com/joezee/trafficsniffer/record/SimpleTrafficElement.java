package com.joezee.trafficsniffer.record;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

final class SimpleTrafficElement implements TrafficElement {
    private final Request request;
    private final Response response;
    private final LocalDateTime timestamp;

    private SimpleTrafficElement(Builder builder) {
        this.timestamp = requireNonNull(builder.timestamp);
        this.request = requireNonNull(builder.request);
        this.response = requireNonNull(builder.response);
    }

    public static Builder builder() {
        return new Builder();
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

    public static final class Builder {
        private LocalDateTime timestamp;
        private Request request;
        private Response response;

        public Builder withTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder withRequest(Request request) {
            this.request = request;
            return this;
        }

        public Builder withResponse(Response response) {
            this.response = response;
            return this;
        }

        public SimpleTrafficElement build() {
            return new SimpleTrafficElement(this);
        }

    }
}

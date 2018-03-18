package com.joezee.trafficsniffer.record;

import com.google.common.collect.ImmutableMap;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

final class SimpleResponse implements TrafficElement.Response {
    private final HttpStatus status;
    private final Map<String, List<String>> headers;
    private final String content;

    private SimpleResponse(Builder builder) {
        status = requireNonNull(builder.status);
        headers = ImmutableMap.copyOf(builder.headers);
        content = requireNonNull(builder.content);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public HttpStatus status() {
        return status;
    }

    @Override
    public Map<String, List<String>> headers() {
        return headers;
    }

    @Override
    public String content() {
        return content;
    }

    public static class Builder {
        private HttpStatus status;
        private Map<String, List<String>> headers = ImmutableMap.of();
        private String content;

        public Builder withStatus(HttpStatus status) {
            this.status = status;
            return this;
        }

        public Builder withHeaders(Map<String, List<String>> headers) {
            this.headers = ImmutableMap.copyOf(requireNonNull(headers));
            return this;
        }

        public Builder withContent(String content) {
            this.content = content;
            return this;
        }

        public SimpleResponse build() {
            return new SimpleResponse(this);
        }
    }
}

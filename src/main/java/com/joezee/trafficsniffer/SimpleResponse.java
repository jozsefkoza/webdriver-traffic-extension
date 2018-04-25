package com.joezee.trafficsniffer;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpStatus;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

final class SimpleResponse implements TrafficElement.Response {

    private final HttpStatus status;
    private final Map<String, List<String>> headers;
    private final String content;

    private SimpleResponse(Builder builder) {
        status = requireNonNull(builder.status);
        headers = ImmutableMap.copyOf(builder.headers);
        content = requireNonNull(builder.content);
    }

    static Builder builder() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SimpleResponse that = (SimpleResponse) o;
        return status == that.status
                && Objects.equals(headers, that.headers)
                && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, headers, content);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("status", status)
                .add("headers", headers)
                .add("content", content)
                .toString();
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

        public TrafficElement.Response build() {
            return new SimpleResponse(this);
        }
    }
}

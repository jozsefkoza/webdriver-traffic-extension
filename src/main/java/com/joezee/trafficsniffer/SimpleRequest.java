package com.joezee.trafficsniffer;

import static java.util.Objects.requireNonNull;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.HttpMethod;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

final class SimpleRequest implements TrafficElement.Request {

    private final HttpMethod method;
    private final URI url;
    private final Map<String, List<String>> headers;
    private final String body;

    private SimpleRequest(Builder builder) {
        method = requireNonNull(builder.method);
        headers = ImmutableMap.copyOf(builder.headers);
        url = requireNonNull(builder.url);
        body = builder.body;
    }

    static Builder builder() {
        return new Builder();
    }

    @Override
    public HttpMethod method() {
        return method;
    }

    @Override
    public URI url() {
        return url;
    }

    @Override
    public Map<String, List<String>> headers() {
        return headers;
    }

    @Override
    public Optional<String> body() {
        return Optional.ofNullable(body);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SimpleRequest that = (SimpleRequest) o;
        return method == that.method
                && Objects.equals(url, that.url)
                && Objects.equals(headers, that.headers)
                && Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, url, headers, body);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("method", method)
                .add("url", url)
                .add("headers", headers)
                .add("body", body)
                .toString();
    }

    static final class Builder {
        private HttpMethod method;
        private URI url;
        private Map<String, List<String>> headers = ImmutableMap.of();
        private String body;

        public Builder withMethod(HttpMethod httpMethod) {
            this.method = requireNonNull(httpMethod);
            return this;
        }

        public Builder withUrl(URI url) {
            this.url = requireNonNull(url);
            return this;
        }

        public Builder withHeaders(Map<String, List<String>> httpHeaders) {
            this.headers = ImmutableMap.copyOf(requireNonNull(httpHeaders));
            return this;
        }

        public Builder withBody(String body) {
            this.body = body;
            return this;
        }

        public TrafficElement.Request build() {
            return new SimpleRequest(this);
        }
    }
}

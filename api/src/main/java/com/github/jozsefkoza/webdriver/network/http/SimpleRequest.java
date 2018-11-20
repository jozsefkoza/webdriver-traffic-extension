package com.github.jozsefkoza.webdriver.network.http;

import static java.util.Objects.requireNonNull;

import java.net.URI;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.google.common.base.MoreObjects;

final class SimpleRequest extends BaseHttpElement implements Request {

    private final String method;
    private final URI url;

    private SimpleRequest(SimpleRequestBuilder builder) {
        super(builder);
        this.method = requireNonNull(builder.method);
        this.url = requireNonNull(builder.url);
    }

    static Request.Builder builder() {
        return new SimpleRequestBuilder();
    }

    @Override
    public Map<String, Collection<String>> headers() {
        return headers;
    }

    @Override
    public String method() {
        return method;
    }

    @Override
    public URI url() {
        return url;
    }

    @Override
    public Optional<String> payload() {
        return Optional.ofNullable(data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), method, url);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }
        SimpleRequest that = (SimpleRequest) other;
        return Objects.equals(method, that.method) && Objects.equals(url, that.url);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("method", method)
            .add("url", url)
            .add("headers", headers)
            .add("payload", data)
            .toString();
    }

    private static final class SimpleRequestBuilder extends BaseHttpElement.Builder implements Request.Builder {
        private String method;
        private URI url;

        @Override
        public SimpleRequestBuilder method(String method) {
            this.method = method;
            return this;
        }

        @Override
        public SimpleRequestBuilder url(URI uri) {
            this.url = uri;
            return this;
        }

        @Override
        public SimpleRequestBuilder payload(String payload) {
            super.data(payload);
            return this;
        }

        @Override
        public SimpleRequestBuilder headers(Map<String, ? extends Collection<String>> headers) {
            super.headers(headers);
            return this;
        }

        @Override
        public SimpleRequest build() {
            return new SimpleRequest(this);
        }
    }
}

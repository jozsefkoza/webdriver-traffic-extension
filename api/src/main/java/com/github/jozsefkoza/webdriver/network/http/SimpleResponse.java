package com.github.jozsefkoza.webdriver.network.http;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.google.common.base.MoreObjects;

final class SimpleResponse extends BaseHttpElement implements Response {

    private final int status;

    private SimpleResponse(SimpleResponseBuilder builder) {
        super(builder);
        this.status = builder.status;
    }

    static Response.Builder builder() {
        return new SimpleResponseBuilder();
    }

    @Override
    public Map<String, Collection<String>> headers() {
        return headers;
    }

    @Override
    public int statusCode() {
        return status;
    }

    @Override
    public Optional<String> body() {
        return Optional.ofNullable(data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), status);
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
        SimpleResponse that = (SimpleResponse) other;
        return status == that.status;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("statusCode", status)
            .add("headers", headers)
            .add("body", data)
            .toString();
    }

    private static final class SimpleResponseBuilder extends BaseHttpElement.Builder implements Response.Builder {
        private int status;

        @Override
        public SimpleResponseBuilder statusCode(int status) {
            this.status = status;
            return this;
        }

        @Override
        public SimpleResponseBuilder body(String body) {
            super.data(body);
            return this;
        }

        @Override
        public SimpleResponseBuilder headers(Map<String, ? extends Collection<String>> headers) {
            super.headers(headers);
            return this;
        }

        @Override
        public SimpleResponse build() {
            return new SimpleResponse(this);
        }
    }
}

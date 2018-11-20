package com.github.jozsefkoza.webdriver.network.http;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collector;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * Base class for specific HTTP elements with headers and some raw data. This class is immutable.
 *
 * @author JoeZee
 */
class BaseHttpElement {
    protected final Map<String, Collection<String>> headers;
    protected final String data;

    /**
     * Create an element from the argument builder.
     *
     * @param builder the concrete builder
     */
    protected BaseHttpElement(Builder builder) {
        this.headers = requireNonNull(builder.headers).entrySet().stream()
            .collect(Collector.of(
                ImmutableMap::<String, Collection<String>>builder,
                (mapBuilder, entry) -> mapBuilder.put(entry.getKey(), ImmutableSet.copyOf(entry.getValue())),
                (base, rest) -> base.putAll(rest.build()),
                ImmutableMap.Builder::build
            ));
        this.data = builder.data;
    }

    @Override
    public int hashCode() {
        return Objects.hash(headers, data);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        BaseHttpElement that = (BaseHttpElement) other;
        return Objects.equals(headers, that.headers) && Objects.equals(data, that.data);
    }

    /**
     * Base builder for specific HTTP elements.
     */
    protected static class Builder {
        private Map<String, ? extends Collection<String>> headers = Collections.emptyMap();
        private String data;

        /**
         * Set the HTTP headers.
         *
         * @param headers the HTTP headers
         * @return this builder
         */
        protected Builder headers(Map<String, ? extends Collection<String>> headers) {
            this.headers = headers;
            return this;
        }

        /**
         * Set the raw data of the of the HTTP element.
         *
         * @param data the raw data
         * @return this builder
         */
        protected Builder data(String data) {
            this.data = data;
            return this;
        }

        /**
         * Build the concrete HTTP element from this builder configuration.
         *
         * @return a new HTTP element
         */
        protected BaseHttpElement build() {
            return new BaseHttpElement(this);
        }
    }
}


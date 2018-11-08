package com.joezee.webdriver.extension.network.http;

import java.net.URI;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;

/**
 * Represents an HTTP request of the network traffic.
 *
 * @author JoeZee
 */
public interface Request {

    /**
     * Create a builder for a simple HTTP request.
     *
     * @return the builder of the request
     */
    static Builder simpleRequest() {
        return SimpleRequest.builder();
    }

    /**
     * Get the HTTP headers of this request.
     *
     * @return the HTTP headers
     */
    Map<String, Collection<String>> headers();

    /**
     * Get the HTTP method of this request.
     *
     * @return the request method
     */
    String method();

    /**
     * Get the URL of this request.
     *
     * @return the request URL
     */
    URI url();

    /**
     * Get the raw payload - if any - of this request.
     *
     * @return the payload
     */
    Optional<String> payload();

    /**
     * Builder of HTTP request.
     */
    interface Builder {

        /**
         * Set the HTTP headers of the request.
         *
         * @param headers the HTTP headers
         * @return this builder
         */
        Builder headers(Map<String, ? extends Collection<String>> headers);

        /**
         * Set the HTTP request method of the request.
         *
         * @param method the request method
         * @return this builder
         */
        Builder method(String method);

        /**
         * Set the URL of the request.
         *
         * @param uri the request URL
         * @return this builder
         */
        Builder url(URI uri);

        /**
         * Set the raw payload of the HTTP request.
         *
         * @param payload the payload
         * @return this builder
         */
        Builder payload(@Nullable String payload);

        /**
         * Create a new HTTP request object from this builder.
         *
         * @return the request
         */
        Request build();
    }
}

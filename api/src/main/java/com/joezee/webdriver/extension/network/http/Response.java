package com.joezee.webdriver.extension.network.http;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * Represents an HTTP response of the network traffic.
 *
 * @author JoeZee
 */
public interface Response {

    /**
     * Create a builder for a simple HTTP response.
     *
     * @return the builder of the response
     */
    static Builder simpleResponse() {
        return SimpleResponse.builder();
    }

    /**
     * Get the HTTP headers of this response.
     *
     * @return the HTTP headers
     */
    Map<String, Collection<String>> headers();

    /**
     * Get the statusCode code of the HTTP response.
     *
     * @return the HTTP statusCode code
     */
    int statusCode();

    /**
     * Get the raw body - if any - of this response.
     *
     * @return the response body
     */
    Optional<String> body();

    /**
     * Builder of HTTP response.
     */
    interface Builder {

        /**
         * Set the HTTP headers of the response.
         *
         * @param headers the HTTP headers
         * @return this builder
         */
        Builder headers(Map<String, ? extends Collection<String>> headers);

        /**
         * Set the HTTP status code of the response.
         *
         * @param code the HTTP status code
         * @return this builder
         */
        Builder statusCode(int code);

        /**
         * Set the body - if any - of the response.
         *
         * @param body the body of the response
         * @return this builder
         */
        Builder body(String body);

        /**
         * Create a new HTTP response object from this builder.
         *
         * @return the response
         */
        Response build();
    }
}

package com.joezee.webdriver.extension.network.model;

import java.net.URI;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * Represents an element of the network traffic.
 *
 * @author JoeZee
 */
public interface TrafficElement {

    static SimpleRequest.SimpleRequestBuilder simpleRequest() {
        return SimpleRequest.builder();
    }

    static SimpleResponse.SimpleResponseBuilder simpleResponse() {
        return SimpleResponse.builder();
    }

    static TrafficElement simple(Request request, Response response) {
        return new SimpleTrafficElement(request, response);
    }

    /**
     * Gets the HTTP request captured in this traffic element.
     *
     * @return the request
     */
    Request request();

    /**
     * Gets the HTTP response captured in this traffic element.
     *
     * @return the response
     */
    Response response();

    /**
     * Represents an HTTP request of the network traffic.
     */
    interface Request {
        /**
         * Gets the method of this request.
         *
         * @return the HTTP method
         */
        String method();

        /**
         * Gets the URL of this request.
         *
         * @return the URL
         */
        URI url();

        /**
         * Gets the HTTP headers of this request as key-value pairs.
         *
         * @return the headers
         */
        Map<String, ? extends Collection<String>> headers();

        /**
         * Gets the payload of this request.
         *
         * @return the raw payload, or empty if no data was sent with this request
         */
        Optional<String> payload();
    }

    /**
     * Represents an HTTP response of the network traffic.
     */
    interface Response {
        /**
         * Gets the status of this response.
         *
         * @return the HTTP status
         */
        int status();

        /**
         * Gets the HTTP headers of this response as key-value pairs.
         *
         * @return the headers
         */
        Map<String, ? extends Collection<String>> headers();

        /**
         * Gets the payload of this response.
         *
         * @return the raw payload
         */
        Optional<String> body();
    }
}

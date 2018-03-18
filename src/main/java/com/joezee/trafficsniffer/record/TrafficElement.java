package com.joezee.trafficsniffer.record;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Represents an element of the network traffic.
 *
 * @author JoeZee
 */
public interface TrafficElement {

    /**
     * Gets the time this traffic element was initiated.
     *
     * @return the timestamp
     */
    LocalDateTime timestamp();

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
        HttpMethod method();

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
        Map<String, List<String>> headers();

        /**
         * Gets the body of this request.
         *
         * @return the raw body, or empty if no data was sent with this request
         */
        Optional<String> body();
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
        HttpStatus status();

        /**
         * Gets the HTTP headers of this response as key-value pairs.
         *
         * @return the headers
         */
        Map<String, List<String>> headers();

        /**
         * Gets the content of this response.
         *
         * @return the raw content
         */
        String content();
    }
}

package com.github.jozsefkoza.webdriver.network.http;

/**
 * Represents an element of the network traffic.
 *
 * @author JoeZee
 */
public interface TrafficElement {

    /**
     * Create a simple traffic element with the argument request and response.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return the traffic element
     */
    static TrafficElement simple(Request request, Response response) {
        return new SimpleTrafficElement(request, response);
    }

    /**
     * Get the HTTP request of this traffic element.
     *
     * @return the request
     */
    Request request();

    /**
     * Get the HTTP response of this traffic element.
     *
     * @return the response
     */
    Response response();
}

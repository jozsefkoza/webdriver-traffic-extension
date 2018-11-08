package com.joezee.webdriver.extension.network.http;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import com.google.common.base.MoreObjects;

final class SimpleTrafficElement implements TrafficElement {

    private final Request request;
    private final Response response;

    SimpleTrafficElement(Request request, Response response) {
        this.request = requireNonNull(request);
        this.response = requireNonNull(response);
    }

    @Override
    public Request request() {
        return request;
    }

    @Override
    public Response response() {
        return response;
    }

    @Override
    public int hashCode() {
        return Objects.hash(request, response);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SimpleTrafficElement that = (SimpleTrafficElement) o;
        return Objects.equals(request, that.request) && Objects.equals(response, that.response);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("request", request)
            .add("response", response)
            .toString();
    }
}

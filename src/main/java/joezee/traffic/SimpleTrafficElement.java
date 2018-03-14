package joezee.traffic;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;

final class SimpleTrafficElement implements TrafficElement {
    private final Request request;
    private final Response response;
    private final LocalDateTime timestamp;

    SimpleTrafficElement(LocalDateTime timestamp, Request request, Response response) {
        this.timestamp = requireNonNull(timestamp);
        this.request = requireNonNull(request);
        this.response = requireNonNull(response);
    }

    @Override
    public LocalDateTime timestamp() {
        return timestamp;
    }

    @Override
    public Request request() {
        return request;
    }

    @Override
    public Response response() {
        return response;
    }
}

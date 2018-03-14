package joezee.traffic;

import static java.util.Objects.requireNonNull;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import org.springframework.http.HttpStatus;

final class SimpleResponseLog implements TrafficElement.Response {
    private final HttpStatus status;
    private final Map<String, String> headers;
    private final String content;

    private SimpleResponseLog(Builder builder) {
        status = requireNonNull(builder.status);
        headers = ImmutableMap.copyOf(builder.headers);
        content = requireNonNull(builder.content);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public HttpStatus status() {
        return status;
    }

    @Override
    public Map<String, String> headers() {
        return headers;
    }

    @Override
    public String content() {
        return content;
    }

    public static class Builder {
        private HttpStatus status;
        private Map<String, String> headers = ImmutableMap.of();
        private String content;

        public Builder withStatus(HttpStatus status) {
            this.status = status;
            return this;
        }

        public Builder withHeaders(Map<String, String> headers) {
            this.headers = ImmutableMap.copyOf(requireNonNull(headers));
            return this;
        }

        public Builder withContent(String content) {
            this.content = content;
            return this;
        }

        public SimpleResponseLog build() {
            return new SimpleResponseLog(this);
        }
    }
}

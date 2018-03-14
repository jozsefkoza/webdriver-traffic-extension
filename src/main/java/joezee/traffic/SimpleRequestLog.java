package joezee.traffic;

import static java.util.Objects.requireNonNull;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

import com.google.common.collect.ImmutableMap;
import org.springframework.http.HttpMethod;

final class SimpleRequestLog implements TrafficElement.Request {

    private final HttpMethod method;
    private final URI url;
    private final Map<String, String> headers;
    private final String body;

    private SimpleRequestLog(Builder builder) {
        method = requireNonNull(builder.method);
        headers = ImmutableMap.copyOf(builder.headers);
        url = requireNonNull(builder.url);
        body = builder.body;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public HttpMethod method() {
        return method;
    }

    @Override
    public URI url() {
        return url;
    }

    @Override
    public Map<String, String> headers() {
        return headers;
    }

    @Override
    public Optional<String> body() {
        return Optional.ofNullable(body);
    }

    public static final class Builder {
        private HttpMethod method;
        private URI url;
        private Map<String, String> headers = ImmutableMap.of();
        private String body;

        public Builder withMethod(HttpMethod httpMethod) {
            this.method = requireNonNull(httpMethod);
            return this;
        }

        public Builder withUrl(URI url) {
            this.url = requireNonNull(url);
            return this;
        }

        public Builder withHeaders(Map<String, String> httpHeaders) {
            this.headers = ImmutableMap.copyOf(requireNonNull(httpHeaders));
            return this;
        }

        public Builder withBody(String body) {
            this.body = body;
            return this;
        }

        public SimpleRequestLog build() {
            return new SimpleRequestLog(this);
        }
    }
}

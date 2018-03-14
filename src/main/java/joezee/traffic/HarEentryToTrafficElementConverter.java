package joezee.traffic;

import static java.util.stream.Collectors.toMap;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import joezee.traffic.TrafficElement.Request;
import joezee.traffic.TrafficElement.Response;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.core.har.HarNameValuePair;
import net.lightbody.bmp.core.har.HarPostData;
import net.lightbody.bmp.core.har.HarRequest;
import net.lightbody.bmp.core.har.HarResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;


/**
 * Converts {@link HarEntry} to {@link TrafficElement}.
 *
 * @author Jozsef_Koza
 */
final class HarEentryToTrafficElementConverter implements Function<HarEntry, TrafficElement> {
    @Override
    public TrafficElement apply(HarEntry harEntry) {
        return new SimpleTrafficElement(getTimestamp(harEntry), getRequest(harEntry.getRequest()), getResponse(harEntry.getResponse()));
    }

    private LocalDateTime getTimestamp(HarEntry harEntry) {
        return harEntry.getStartedDateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    private Request getRequest(HarRequest request) {
        return SimpleRequestLog.builder()
            .withUrl(URI.create(request.getUrl()))
            .withMethod(HttpMethod.resolve(request.getMethod()))
            .withHeaders(getHeaders(request.getHeaders()))
            .withBody(getBody(request.getPostData()))
            .build();
    }

    private Response getResponse(HarResponse response) {
        return SimpleResponseLog.builder()
            .withStatus(HttpStatus.valueOf(response.getStatus()))
            .withHeaders(getHeaders(response.getHeaders()))
            .withContent(response.getContent().getText())
            .build();
    }

    private Map<String, String> getHeaders(List<HarNameValuePair> headers) {
        return headers.stream().collect(toMap(HarNameValuePair::getName, HarNameValuePair::getValue));
    }

    private String getBody(HarPostData postData) {
        return Optional.ofNullable(postData).map(HarPostData::getText).orElse(null);
    }
}

package com.joezee.trafficsniffer.record;

import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.core.har.HarNameValuePair;
import net.lightbody.bmp.core.har.HarPostData;
import net.lightbody.bmp.core.har.HarRequest;
import net.lightbody.bmp.core.har.HarResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;


/**
 * Converts {@link HarEntry} to {@link TrafficElement}.
 *
 * @author JoeZee
 */
final class HarEentryToTrafficElementConverter implements Function<HarEntry, TrafficElement> {

    @Override
    public TrafficElement apply(HarEntry harEntry) {
        return SimpleTrafficElement.builder()
            .withTimestamp(getTimestamp(harEntry))
            .withRequest(convert(harEntry.getRequest()))
            .withResponse(convert(harEntry.getResponse())).build();
    }

    private LocalDateTime getTimestamp(HarEntry harEntry) {
        return harEntry.getStartedDateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    private TrafficElement.Request convert(HarRequest request) {
        return SimpleRequest.builder()
            .withUrl(URI.create(request.getUrl()))
            .withMethod(HttpMethod.resolve(request.getMethod()))
            .withHeaders(convert(request.getHeaders()))
            .withBody(getBody(request.getPostData()))
            .build();
    }

    private TrafficElement.Response convert(HarResponse response) {
        return SimpleResponse.builder()
            .withStatus(HttpStatus.valueOf(response.getStatus()))
            .withHeaders(convert(response.getHeaders()))
            .withContent(response.getContent().getText())
            .build();
    }

    private Map<String, List<String>> convert(List<HarNameValuePair> headers) {
        return headers.stream().collect(groupingBy(HarNameValuePair::getName, mapping(HarNameValuePair::getValue, toList())));
    }

    private String getBody(HarPostData postData) {
        return Optional.ofNullable(postData).map(HarPostData::getText).orElse(null);
    }
}

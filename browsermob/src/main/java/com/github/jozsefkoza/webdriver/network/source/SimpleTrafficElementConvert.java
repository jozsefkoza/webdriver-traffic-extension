package com.github.jozsefkoza.webdriver.network.source;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toSet;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import com.github.jozsefkoza.webdriver.network.http.TrafficElement;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.core.har.HarNameValuePair;
import net.lightbody.bmp.core.har.HarPostData;
import net.lightbody.bmp.core.har.HarRequest;
import net.lightbody.bmp.core.har.HarResponse;

import com.github.jozsefkoza.webdriver.network.http.Request;
import com.github.jozsefkoza.webdriver.network.http.Response;

/**
 * Converts a {@link HarEntry} to a {@link TrafficElement}.
 */
final class SimpleTrafficElementConvert implements Function<HarEntry, TrafficElement> {

    @Override
    public TrafficElement apply(HarEntry harEntry) {
        return TrafficElement.simple(getRequest(harEntry.getRequest()), getResponse(harEntry.getResponse()));
    }

    private Request getRequest(HarRequest request) {
        return Request.simpleRequest()
            .url(URI.create(request.getUrl()))
            .method(request.getMethod())
            .headers(getHeaders(request.getHeaders()))
            .payload(getBody(request.getPostData()))
            .build();
    }

    private Response getResponse(HarResponse response) {
        return Response.simpleResponse()
            .statusCode(response.getStatus())
            .headers(getHeaders(response.getHeaders()))
            .body(response.getContent().getText())
            .build();
    }

    private Map<String, Set<String>> getHeaders(List<HarNameValuePair> headers) {
        return headers.stream().collect(groupingBy(
            HarNameValuePair::getName,
            mapping(HarNameValuePair::getValue, toSet())));
    }

    private String getBody(HarPostData postData) {
        return Optional.ofNullable(postData).map(HarPostData::getText).orElse(null);
    }
}

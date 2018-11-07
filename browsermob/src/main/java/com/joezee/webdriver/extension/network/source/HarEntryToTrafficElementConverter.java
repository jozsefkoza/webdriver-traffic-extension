package com.joezee.webdriver.extension.network.source;

import static java.util.stream.Collectors.groupingBy;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;

import com.google.common.collect.ImmutableList;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.core.har.HarNameValuePair;
import net.lightbody.bmp.core.har.HarPostData;
import net.lightbody.bmp.core.har.HarRequest;
import net.lightbody.bmp.core.har.HarResponse;

import com.joezee.webdriver.extension.network.model.TrafficElement;
import com.joezee.webdriver.extension.network.model.TrafficElement.Request;
import com.joezee.webdriver.extension.network.model.TrafficElement.Response;

/**
 * Converts a {@link HarEntry} to a {@link TrafficElement}.
 *
 * @author JoeZee
 */
final class HarEntryToTrafficElementConverter implements Function<HarEntry, TrafficElement> {
    @Override
    public TrafficElement apply(HarEntry harEntry) {
        return TrafficElement.simple(getRequest(harEntry.getRequest()), getResponse(harEntry.getResponse()));
    }

    private Request getRequest(HarRequest request) {
        return TrafficElement.simpleRequest()
            .url(URI.create(request.getUrl()))
            .method(request.getMethod())
            .headers(getHeaders(request.getHeaders()))
            .payload(getBody(request.getPostData()))
            .build();
    }

    private Response getResponse(HarResponse response) {
        return TrafficElement.simpleResponse()
            .status(response.getStatus())
            .headers(getHeaders(response.getHeaders()))
            .body(response.getContent().getText())
            .build();
    }

    private Map<String, ? extends Collection<String>> getHeaders(List<HarNameValuePair> headers) {
        return headers.stream().collect(groupingBy(
            HarNameValuePair::getName,
            Collector.of(
                ImmutableList::<String>builder,
                (builder, pair) -> builder.add(pair.getValue()),
                (base, rest) -> base.addAll(rest.build()),
                ImmutableList.Builder::build
            )));
    }

    private String getBody(HarPostData postData) {
        return Optional.ofNullable(postData).map(HarPostData::getText).orElse(null);
    }
}

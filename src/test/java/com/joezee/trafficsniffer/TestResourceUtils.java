package com.joezee.trafficsniffer;

import com.google.gson.Gson;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Utility class for managing test resources.
 *
 * @author JoeZee
 */
public final class TestResourceUtils {
    private static final Gson GSON = new Gson();
    private static final Resource TEST_HAR = new ClassPathResource("test_har.json");

    private TestResourceUtils() {
        //Prevent instantiation
    }

    public static Har getTestHar() {
        try (InputStreamReader reader = new InputStreamReader(TEST_HAR.getInputStream(), StandardCharsets.UTF_8)) {
            return GSON.fromJson(reader, Har.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot load test resource", e);
        }
    }

    public static HarEntry getTestHarEntry() {
        return getTestHar().getLog().getEntries().get(0);
    }
}

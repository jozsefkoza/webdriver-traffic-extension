package com.joezee.trafficsniffer;

import java.io.File;
import java.io.FileReader;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.google.gson.Gson;

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

    public static <T> T getTestData(String path, Class<T> resultType) {
        try {
            File resource = new File(Thread.currentThread().getContextClassLoader().getResource(path).toURI());
            return GSON.fromJson(new FileReader(resource), resultType);
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot load test data from file at path: " + path, e);
        }
    }
}

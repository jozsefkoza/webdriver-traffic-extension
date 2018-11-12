package com.joezee.webdriver.extension.network;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import com.google.gson.Gson;

/**
 * Utility class for reading test resources.
 *
 * @author JoeZee
 */
public final class TestResourceReader {
    private static final Gson GSON = new Gson();

    private TestResourceReader() {
        //Prevent instantiation
    }

    /**
     * Read the test resource of a format of JSON specified by {@code path} as {@code type}.
     *
     * @param resourcePath the path to the JSON resource
     * @param type         the type of the JSON to map to
     * @param <T>          the concrete type of the mapped resource
     * @return the mapped JSON
     */
    public static <T> T read(String resourcePath, Type type) {
        InputStream resource = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);
        return GSON.fromJson(new InputStreamReader(resource), type);
    }
}

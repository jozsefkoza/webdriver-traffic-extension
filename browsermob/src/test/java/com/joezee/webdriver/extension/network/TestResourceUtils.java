package com.joezee.webdriver.extension.network;

import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.lightbody.bmp.core.har.HarEntry;

/**
 * Utility class for managing test resources.
 *
 * @author JoeZee
 */
public final class TestResourceUtils {
    private static final Gson GSON = new GsonBuilder().create();

    private TestResourceUtils() {
        //Prevent instantiation
    }

    public static HarEntry harEntry() {
        InputStream resource = Thread.currentThread().getContextClassLoader().getResourceAsStream("har_entry.json");
        return GSON.fromJson(new InputStreamReader(resource), HarEntry.class);
    }
}

package com.pf.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

@Slf4j
public final class EnvironmentUtils {
    private EnvironmentUtils() {
        // utils class
    }

    public static String readEnvironmentVariable(Environment environment, String key) {
        try {
            return environment.getProperty(key);
        } catch (IllegalArgumentException e) {
            log.warn("Did not find any environment variable with key {} due to {}, returning null", key, e.getLocalizedMessage());
            return null;
        }
    }
}

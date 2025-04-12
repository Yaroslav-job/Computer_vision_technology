package org.example;

import org.opencv.core.Core;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YourApi {

    private static final Logger log = LoggerFactory.getLogger(YourApi.class);

    public enum OSType {
        WINDOWS, MACOS, LINUX, OTHER
    }

    public OSType getOperatingSystemType() {
        String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        if ((OS.contains("mac")) || (OS.contains("darwin"))) {
            return OSType.MACOS;
        } else if (OS.contains("win")) {
            return OSType.WINDOWS;
        } else if (OS.contains("nux")) {
            return OSType.LINUX;
        } else {
            return OSType.OTHER;
        }
    }

    public YourApi() throws Exception {
        log.info("Checking OS.....");

        String nativeLibraryPath;

        switch (getOperatingSystemType()) {
            case LINUX:
                nativeLibraryPath = Config.getProp("pathToNativeLibLinux");
                break;
            case WINDOWS:
                nativeLibraryPath = Config.getProp("pathToNativeLibWin");
                break;
            case MACOS:
                nativeLibraryPath = Config.getProp("pathToNativeLibMac");
                break;
            case OTHER:
            default:
                throw new Exception("Your OS does not support OpenCV!");
        }

        if (nativeLibraryPath == null) {
            throw new Exception("Native library path not found in config!");
        }

        // Загружаем библиотеку
        System.load(nativeLibraryPath);

        // Проверяем версию OpenCV
        String version = Core.getVersionString();
        log.info("OS: {}", getOperatingSystemType());
        log.info("OpenCV version: {}", version);

    }
}

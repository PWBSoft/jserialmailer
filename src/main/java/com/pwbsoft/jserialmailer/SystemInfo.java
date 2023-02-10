package com.pwbsoft.jserialmailer;

import jakarta.mail.Session;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class SystemInfo {

    public static final String NEW_LINE = "\n";
    public static Properties properties = new Properties();
    public static List<OS> WINOS = List.of(OS.OLDWIN, OS.WIN7, OS.WIN8, OS.WIN10);

    private static void initProperties() {
        try {
            properties.load(SystemInfo.class.getClassLoader().getResourceAsStream("app.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String appVersion() {
        if (properties != null) {
            initProperties();
        }
        assert properties != null;
        return properties.getProperty("app.version");
    }

    public static OS getOS() {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().contains("windows")) {

            if (os.toLowerCase().endsWith(" 7") || os.toLowerCase().endsWith("vista")) {
                return OS.WIN7;
            } else if (os.toLowerCase().endsWith(" 8") || os.toLowerCase().endsWith(" 8.1")) {
                return OS.WIN8;
            } else if (os.toLowerCase().endsWith(" 10") || os.toLowerCase().endsWith(" 11")) {
                return OS.WIN10;
            } else return OS.OLDWIN;

        } else if (os.toLowerCase().contains("mac")) {
            return OS.MACOS;
        } else {
            return OS.LINUX;
        }
    }

    public static String javaVersion() {
        return System.getProperty("java.version");
    }

    public static String javafxVersion() {
        return System.getProperty("javafx.version");
    }

    public static Properties getProperties() {
        if (properties != null) {
            initProperties();
        }
        assert properties != null;
        return properties;
    }

    public enum OS {
        OLDWIN,
        WIN7,
        WIN8,
        WIN10,
        MACOS,
        LINUX
    }

}
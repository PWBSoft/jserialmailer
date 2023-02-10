package com.pwbsoft.jserialmailer.utils;

import com.pwbsoft.jserialmailer.SystemInfo;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Comparator;

public class UrlUtils {
    private static final String[] linux_browsers = {"google-chrome", "firefox", "mozilla", "epiphany", "konqueror",
            "netscape", "opera", "links", "lynx"};

    public static void openUrl(String url) {
        SystemInfo.OS os = SystemInfo.getOS();
        Runtime rt = Runtime.getRuntime();
        try {
            System.out.println("OS " + os.toString());

            if (SystemInfo.WINOS.contains(os)) {
                System.out.println("WINDOWS");
                rt.exec(new String[]{"explorer", url});

            } else if (SystemInfo.OS.MACOS == os) {
                System.out.println("MAC");
                rt.exec(new String[]{"open", url});

            } else {
                System.out.println("LINUX");

                StringBuilder cmd = new StringBuilder();
                for (int i = 0; i < linux_browsers.length; i++) {
                    if (i == 0) {
                        cmd.append(String.format("%s \"%s\"", linux_browsers[i], url));
                    } else {
                        cmd.append(String.format(" || %s \"%s\"", linux_browsers[i], url));
                    }
                }
                // If the first didn't work, try the next browser and so on
                rt.exec(new String[]{"sh", "-c", cmd.toString()});

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class FileNameComparator implements Comparator<Path> {
        public int compare(Path a, Path b) {
            return a.toAbsolutePath().compareTo(b.toAbsolutePath());
        }
    }
}

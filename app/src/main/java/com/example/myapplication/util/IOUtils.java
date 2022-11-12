package com.example.myapplication.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public final class IOUtils {
    public static void closeQuietly(AutoCloseable... acs) {
        for (AutoCloseable ac : acs) {
            if (ac != null) {
                try {
                    ac.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    public static String read(InputStream in) {
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            isr = new InputStreamReader(in, StandardCharsets.UTF_8);
            br = new BufferedReader(isr);

            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(br, isr, in);
        }
    }

    private IOUtils() {
    }
}

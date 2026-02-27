package org.waterwood.utils;

import org.waterwood.plugin.WaterPlugin;
import org.waterwood.service.LoggerService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DebugUtil {

    private static boolean isDebugMode = false;
    private static LoggerService loggerService;
    private static Path recordFilePath;
    private static final List<String> logs = new ArrayList<>();
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
    public static void init(boolean debug, LoggerService logger, WaterPlugin plugin) {
        isDebugMode = debug;
        loggerService = logger;
        recordFilePath = Path.of(plugin.getDefaultFilePath("/log.txt"));

        try {
            Files.createDirectories(recordFilePath.getParent());
        } catch (IOException e) {
            System.err.println("Failed to create log directory: " + e.getMessage());
        }
    }

    public static void init(boolean debug, LoggerService logger, Path logPath) {
        isDebugMode = debug;
        loggerService = logger;
        recordFilePath = logPath;

        try {
            Files.createDirectories(recordFilePath.getParent());
        } catch (IOException e) {
            System.err.println("Failed to create log directory: " + e.getMessage());
        }
    }

    public static void record(String log) {
        logs.add(log);
        writeToFile("INFO", log);
    }

    public static void recordAll(List<String> logList) {
        logs.addAll(logList);
        for (String log : logList) {
            writeToFile("INFO", log);
        }
    }

    public static void print() {
        if (!isDebugMode) return;
        logs.forEach(log -> loggerService.info("[DEBUG] " + log));
    }

    public static void print(int length) {
        if (!isDebugMode || logs.isEmpty()) return;

        int maxLen = logs.size();
        int actualLen = Math.min(length, maxLen);

        for (int i = maxLen - actualLen; i < maxLen; i++) {
            loggerService.info("[DEBUG] " + logs.get(i));
        }

        int remain = maxLen - actualLen;
        if (remain > 0) {
            loggerService.info("[DEBUG] ... and %d older lines".formatted(remain));
        }
    }

    public static void print(int start, int end) {
        if (!isDebugMode || logs.isEmpty()) return;

        int maxLen = logs.size();
        int s = Math.max(0, Math.min(start, maxLen));
        int e = Math.min(end, maxLen);

        if (s >= e) {
            loggerService.warning("[DEBUG] Invalid range: " + start + " to " + end);
            return;
        }

        if (s > 0) {
            loggerService.info("[DEBUG] ... %d lines before".formatted(s));
        }

        for (int i = s; i < e; i++) {
            loggerService.info("[DEBUG] " + logs.get(i));
        }

        int remain = maxLen - e;
        if (remain > 0) {
            loggerService.info("[DEBUG] ... and %d more lines".formatted(remain));
        }
    }

    public static void flush() {
        if (logs.isEmpty() || recordFilePath == null) return;

        StringBuilder sb = new StringBuilder();
        for (String log : logs) {
            sb.append(formatLogLine("INFO", log));
        }

        writeStringToFile(sb.toString());
    }

    public static void saveAndClear() {
        flush();
        clear();
    }

    public static void clear() {
        logs.clear();
    }


    public static boolean isDebugMode() {
        return isDebugMode;
    }

    public static void setDebugMode(boolean debug) {
        isDebugMode = debug;
    }

    public static int getLogCount() {
        return logs.size();
    }

    public static List<String> getLogs() {
        return new ArrayList<>(logs);
    }

    public static Path getLogPath() {
        return recordFilePath;
    }

    public static void writeToFile(String level, String message, Path filePath) {
        try {
            Files.createDirectories(filePath.getParent());
            Files.writeString(filePath, formatLogLine(level, message),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error writing log: " + e.getMessage());
        }
    }

    public static void writeToFile(String level, String message) {
        if (recordFilePath == null) {
            System.err.println("DebugService not initialized! Call init() first.");
            return;
        }
        writeToFile(level, message, recordFilePath);
    }

    public static void info(String msg) {
        record(msg);
    }

    public static void error(String msg) {
        if (recordFilePath == null) {
            System.err.println("DebugService not initialized!");
            return;
        }
        writeToFile("ERROR", msg);
        if (isDebugMode && loggerService != null) {
            loggerService.info("[DEBUG] " + msg);
        }
    }

    public static void debug(String msg) {
        if (recordFilePath == null) {
            System.err.println("DebugService not initialized!");
            return;
        }
        writeToFile("DEBUG", msg);
        if (isDebugMode && loggerService != null) {
            loggerService.info("[DEBUG] " + msg);
        }
    }

    private static String formatLogLine(String level, String message) {
        return String.format("[%s] %s: %s%n",
                LocalDateTime.now().format(FORMATTER), level, message);
    }

    private static void writeStringToFile(String content) {
        try {
            Files.writeString(recordFilePath, content,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            if (loggerService != null) {
                loggerService.warning("Failed to write debug logs: " + e.getMessage());
            } else {
                System.err.println("Failed to write debug logs: " + e.getMessage());
            }
        }
    }
}
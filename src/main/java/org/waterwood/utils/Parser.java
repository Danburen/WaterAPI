package org.waterwood.utils;

public class Parser {
    public static int[] parseVersionToArray(String version) {
        String[] parts = version.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid version format: " + version);
        }

        return new int[] {
                Integer.parseInt(parts[0]), // major
                Integer.parseInt(parts[1]), // minor
                Integer.parseInt(parts[2])  // patch
        };
    }
}

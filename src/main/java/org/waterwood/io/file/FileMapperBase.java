package org.waterwood.io.file;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileMapperBase {
    protected static String filePath;
    public FileMapperBase(String filePath) {
        FileMapperBase.filePath = filePath;
    }
    // Helper method to read JSON from a file
    protected JsonObject readJsonFile(String filePath) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            return new Gson().fromJson(content, JsonObject.class);
        } catch (IOException e) {
            System.out.println("Error reading file " + filePath + ": " + e.getMessage());
            return null;
        }
    }

    // Helper method to write JSON to a file
    protected void writeJsonFile(String filePath, JsonObject jsonObject) {
        try {
            Files.write(Paths.get(filePath), new Gson().toJson(jsonObject).getBytes());
        } catch (IOException e) {
            System.out.println("Error writing file " + filePath + ": " + e.getMessage());
        }
    }
}

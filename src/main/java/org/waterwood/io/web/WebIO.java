package org.waterwood.io.web;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public  abstract class WebIO {
    public static String sendGetRequest(String urlStr) {
        StringBuilder result = new StringBuilder();
        try{
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int requestCode = connection.getResponseCode();
            if(requestCode == HttpURLConnection.HTTP_OK){
                InputStream IS = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(IS));
                String line;
                while((line = reader.readLine())!= null){
                    result.append(line);
                }
                IS.close();
                connection.disconnect();
            }
        }catch(Exception e) {
            return null;
        }
        return result.toString();
    }

    public static void download(String fileUrl,String savedPath,long expectedSize) throws IOException{
        URL url = new URL(fileUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try (BufferedInputStream FIS = new BufferedInputStream(connection.getInputStream());
             FileOutputStream FOS = new FileOutputStream(savedPath);
             BufferedOutputStream out = new BufferedOutputStream(FOS)) {

            byte[] buffer = new byte[1024];
            int byteRead;
            long totalSize = 0;
            while ((byteRead = FIS.read(buffer, 0, 1024)) != -1) {
                out.write(buffer, 0, byteRead);
                totalSize += byteRead;
            }
            if (totalSize != expectedSize) {
                Files.deleteIfExists(Paths.get(savedPath));
                throw new IOException("Download file size is not match expired: " + expectedSize + " byte,but got: " + totalSize + " bytes");
            }
        } finally {
            connection.disconnect();
        }
    }
    private static String getFileName(String fileUrl) {
        return fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
    }
}

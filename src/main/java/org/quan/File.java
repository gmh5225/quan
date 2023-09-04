package org.quan;
import org.quan.log.Log;
import org.quan.log.LogType;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
public class File {
    public static String getFileString(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                result.append(line).append('\u001F');
            reader.close();
            return result.toString();
        } catch (IOException e) {
            new Log(LogType.error,"[File]: Unable to read file \""+filePath+"\"");
            return "";
        }
    }
    public static void stringToFile(String filePath, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
            System.out.println("Content has been written to the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
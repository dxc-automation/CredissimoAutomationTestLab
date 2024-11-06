package com.demo.utilities;

import com.demo.config.BasicTestConfig;
import com.google.gson.*;
import okhttp3.MediaType;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.demo.properties.FilePaths.report_json_folder;


public class FileUtility {

    public static final JSONParser jsonParser = new JSONParser();
    public static final Gson gson = new Gson();
    public static final MediaType MediaTypeJSON = MediaType.parse("application/json; charset=utf-8");

    private static String date;


    public static String getDate() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH-mm");
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        String date = dateFormatter.format(localDate);
        String time = timeFormatter.format(localTime);
        return date + "_" + time;
    }


    public static Timestamp getTime(Long timestamp) throws Exception {
        Timestamp time = new Timestamp(timestamp);
        return time;
    }


    public static String getFileName(Method method) throws Exception {
        String fileName = method.getName() + ".json";
        return fileName;
    }


    public static File createLogFile(String fileName, String responseBody) {
        try {
            File file = new File(report_json_folder + fileName);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(responseBody);
            fileWriter.flush();
            fileWriter.close();

            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static File createDebugFile(String fileName, String responseBody) {
        try {
            String response = responseBody
                    .replace("vwd.hchart.seriesRequestManager.sync_response(", "")
                    .replace(")", "");

            File file = new File(report_json_folder + fileName);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(responseBody);
            fileWriter.flush();
            fileWriter.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void zip(String sourceDirPath, String zipFilePath) throws IOException {
        Path p = Files.createFile(Paths.get(zipFilePath));
        try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p))) {
            Path pp = Paths.get(sourceDirPath);
            Files.walk(pp)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
                        try {
                            zs.putNextEntry(zipEntry);
                            Files.copy(path, zs);
                            zs.closeEntry();
                        } catch (IOException e) {
                            System.err.println(e);
                        }
                    });
        }
    }


    private static long getFolderSize(File directory) {
        long length = 0;

        if (directory.isFile())
            length += directory.length();
        else{
            for (File file : directory.listFiles()) {
                if (file.isFile())
                    length += file.length();
                else
                    length += getFolderSize(file);
            }
        }
        // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
        long fileSizeInKB = length / 1024;

        // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        long fileSizeInMB = fileSizeInKB / 1024;
        return fileSizeInMB;
    }


    public static void checkFolderSize(File dir) {
        long size = getFolderSize(dir);
        System.out.println("\nReports archive folder size is " + size);
    }
}
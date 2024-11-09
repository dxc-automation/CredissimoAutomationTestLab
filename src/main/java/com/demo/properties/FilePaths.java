package com.demo.properties;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilePaths {

    /**
     * Used for getting project root folder
     *
     * @return  Current project root folder
     */
    public static Path getRootDir() {
        Path root = Paths.get(new File(System.getProperty("user.dir")).getPath());
        return root;
    }


    //  * * * *    FILES
    public final static File testDataFile         = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\test_data.json").getAbsoluteFile();
    public final static File reportFile           = new File(System.getProperty("user.dir") + "\\report\\TestReport.html");
    public final static File reportConfigFile     = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\extent-config.xml");
    public final static File configPropertiesFile = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\config.properties");



    //  * * * *    FOLDERS
    public final static File report_archive_folder       = new File(System.getProperty("user.dir") + "\\report_archive\\");
    public final static File report_json_folder          = new File(System.getProperty("user.dir") + "\\report\\JSON\\");
    public final static File report_folder               = new File(System.getProperty("user.dir") + "\\report\\");
    public final static File xml_files_folder            = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\xml_files\\");

    public final static File screenshots_failed_folder   = new File(System.getProperty("user.dir") + "\\report\\Screenshots\\Failed\\");
    public final static File screenshots_actual_folder   = new File(System.getProperty("user.dir") + "\\report\\Screenshots\\Actual\\");
    public final static File screenshots_buffer_folder   = new File(System.getProperty("user.dir") + "\\report\\Screenshots\\Buffer\\");
    public final static File screenshots_expected_folder = new File(System.getProperty("user.dir") + "\\report\\Screenshots\\Expected\\");
    public final static File comparison_result_folder    = new File(System.getProperty("user.dir") + "\\report\\Screenshots\\Result\\");
    public final static File video_files                 = new File(System.getProperty("user.dir") + "\\report\\video\\");

}



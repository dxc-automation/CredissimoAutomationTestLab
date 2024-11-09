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
    public final static String report_archive_folder       = getRootDir().getParent() + "/report_archive/";
    public final static String report_json_folder          = getRootDir().getParent() + "/report/JSON/";
    public final static String report_folder               = getRootDir().getParent() + "/report/";
    public final static String xml_files_folder            = getRootDir().getParent() + "/src/main/resources/xml_files/";

    public final static String screenshots_failed_folder   = getRootDir().getParent() + "/report/Screenshots/Failed/";
    public final static String screenshots_actual_folder   = getRootDir().getParent() + "/report/Screenshots/Actual/";
    public final static String screenshots_buffer_folder   = getRootDir().getParent() + "/report/Screenshots/Buffer/";
    public final static String screenshots_expected_folder = getRootDir().getParent() + "/report/Screenshots/Expected/";
    public final static String comparison_result_folder    = getRootDir().getParent() + "/report/Screenshots/Result/";
    public final static String video_files                 = getRootDir().getParent() + "/report/video/";

}



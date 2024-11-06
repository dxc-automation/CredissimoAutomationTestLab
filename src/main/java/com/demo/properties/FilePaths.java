package com.demo.properties;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilePaths {

    //  Get project main dir
    public static Path getRootDir() {
        Path root = Paths.get(new File(System.getProperty("user.dir")).getPath());
        return root;
    }






    //  * * * *    F I L E   P A T H S
    public final static String report_archive_folder       = getRootDir().getParent() + "/report_archive/";
    public final static String report_json_folder          = getRootDir().getParent() + "/report/JSON/";
    public final static String report_folder               = getRootDir().getParent() + "/report/";
    public final static String report_html_file            = getRootDir().getParent() + "/report/TestReport.html";
    public final static String report_config_xml_file      = getRootDir().getParent() + "/src/main/resources/extent-config.xml";
    public final static String xml_files_folder            = getRootDir().getParent() + "/src/main/resources/xml_files/";
    public final static String test_data_file              = getRootDir().getParent() + "/src/main/resources/test_data.json";

    public final static String screenshots_failed_folder   = getRootDir().getParent() + "/report/Screenshots/Failed/";
    public final static String screenshots_actual_folder   = getRootDir().getParent() + "/report/Screenshots/Actual/";
    public final static String screenshots_buffer_folder   = getRootDir().getParent() + "/report/Screenshots/Buffer/";
    public final static String screenshots_expected_folder = getRootDir().getParent() + "/report/Screenshots/Expected/";
    public final static String comparison_result_folder    = getRootDir().getParent() + "/report/Screenshots/Result/";
    public final static String video_files                 = getRootDir().getParent() + "/report/video/";

    public final static String config_properties_file      = getRootDir() + "/src/main/resources/config.properties";
    public final static String chrome_driver_file          = getRootDir() + "/src/main/resources/drivers/chromedriver.exe";
    public final static String firefox_driver_file         = getRootDir() + "/src/main/resources/drivers/geckodriver.exe";

    public final static String cookies_file                = getRootDir() + "/report/cookies/";


}



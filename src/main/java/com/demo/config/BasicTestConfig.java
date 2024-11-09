package com.demo.config;

import com.demo.properties.Constants;
import com.demo.properties.FilePaths;
import com.demo.properties.TestData;
import com.demo.utilities.FileUtility;
import com.demo.utilities.ScreenRecorderUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.*;

import static com.demo.config.Drivers.browserConfig;


/**
 *                          This class contains all methods for taking screenshots,
 *                          browser initialization and generateReport generation.
 *          List:
 *   [1]    takeScreenshot  Capture screenshot and save the file with PNG extension.
 *                          Example:            takeScreenshot(driver, "FileName");
 *   [2]    browserConfig           Launch web browser. Value must be setted in testng.xml
 *   [3]    generateReport          Describes the result of a scripts and print result values.
 *   [4]    finishReport    Writes scripts information from the started reporters to
 *                          their output view.
 *   [5]    tearDown        Stop web driver and close the browser.
 */


public class BasicTestConfig {

    public static Drivers     drivers = new Drivers();
    public static SoftAssert  check   = new SoftAssert();
    public static Constants   constants  = new Constants();
    public static TestData    testData   = new TestData();
    public static WebDriver     driver;
    public static WebDriverWait wait;

    static final Logger LOG = LogManager.getLogger(BasicTestConfig.class);



    @BeforeSuite
    public void getTestData() {
        testData.getTestData();
        WebDriverManager.chromedriver().setup();
    }


    @BeforeTest
    @Parameters({"browser"})
    public void initWebBrowser(String browser) throws Exception {
        driver = browserConfig(browser);
        wait = new WebDriverWait(driver, 10);
    }


    @AfterTest
    public void closeWebBrowser() throws Exception {
        try {
            driver.close();
        } catch (Exception e) {
            System.out.println("WebDriver was already closed");
        }
    }


    /**
     * Delete all previous executed XML files
     */
    @AfterSuite
    public void clearXmlFiles() throws IOException {
        ReporterConfig.extent.flush();

        try {
            driver.close();
            driver.quit();
        } catch (Exception e) {
            System.out.println("Web browser was not closed");
        }

        String folder = FilePaths.report_folder.toString();
        String file   = FilePaths.report_archive_folder + "\\" + FileUtility.getDate() + ".zip";
        FileUtility.zip(folder, file);
        LOG.info("Report archive successfully created\nFile {}", file);

        FileUtils.forceMkdir(FilePaths.report_folder);
        LOG.info("Old report folder successfully deleted");
    }

}


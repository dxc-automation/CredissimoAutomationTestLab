package com.demo.config;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.demo.properties.Constants;
import com.demo.properties.Endpoints;
import com.demo.properties.FilePaths;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestResult;

import static com.demo.properties.FilePaths.*;
import com.demo.config.BasicTestConfig.*;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 *                              This class contains methods for HTML generateReport generation.
 *          List:
 *   [1]    ExtentReports       Print information about machine.
 *   [2]    ExtentHtmlReporter  HTML file design configuration.
 *   [3]    ExtentTest          Create a new scripts object.
 */

public class ReporterConfig {

    static final Logger LOG = LogManager.getLogger(BasicTestConfig.class);

    public static ExtentTest    test;
    public static ExtentReports extent;
    private static ExtentHtmlReporter htmlReporter;

    private static String osName    = System.getProperty("os.name");
    private static String osVersion = System.getProperty("os.version");
    private static String osArch    = System.getProperty("os.arch");


    public static ExtentReports GetExtent() throws UnknownHostException {
        if (extent != null)
            return extent;
        extent = new ExtentReports();
        extent.attachReporter(getHtmlReporter());
        extent.attachReporter(htmlReporter);

        InetAddress localHost = InetAddress.getLocalHost();
        String hostname = localHost.getHostName();

        extent.setSystemInfo("Local Host", localHost.getHostAddress());
        extent.setSystemInfo("Host Name",  hostname);
        extent.setSystemInfo("OS",         osName);
        extent.setSystemInfo("OS Version", osVersion);
        extent.setSystemInfo("OS Arch",    osArch);
        return extent;
    }


    private static ExtentHtmlReporter getHtmlReporter() {
        htmlReporter = new ExtentHtmlReporter(reportFile);
        htmlReporter.config().setReportName("<![CDATA[ <img src='" + new File(System.getProperty("user.dir") + "/src/main/resources/logo.jpeg") + "' />  ]]>");
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setEncoding("UTF-8");
        htmlReporter.config().setCSS(".r-img { width: 50%; }" +
                                     "nav { background: #ffffff; }" +
                                     ".black { background-color: #fff !important; }");
        return htmlReporter;
    }


    /**
     * This method must be called for every new test.
     *
     * @param       testName            String with the name of our test case
     * @param       testDescription     String with description or test steps
     * @param       testCategory        String with test category (API or WEB)
     */
    public static void startTestReport(String testName, String testDescription, String testCategory) throws Exception {
        extent = GetExtent();
        test   = extent.createTest(
                "<b>" + testName + "</b>",
                "<pre>"
                        + "<center><b>* * * * * * * *    I N F O R M A T I O N    * * * * * * * *</b></center>"
                        + "<p align=justify>"
                        + testDescription
                        + "</p>"
                        + "</pre>");

        test.assignCategory(testCategory);
        extent.setAnalysisStrategy(AnalysisStrategy.TEST);
    }
}

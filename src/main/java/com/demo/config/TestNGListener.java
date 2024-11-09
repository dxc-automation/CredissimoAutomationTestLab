package com.demo.config;

import com.aventstack.extentreports.MediaEntityBuilder;
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
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import java.io.File;
import java.io.IOException;

import static com.demo.config.ReporterConfig.*;
import static com.demo.config.BasicTestConfig.*;
import static com.demo.properties.FilePaths.screenshots_actual_folder;


public class TestNGListener implements ITestListener {

    static final Logger LOG = LogManager.getLogger(BasicTestConfig.class);

    private String description;
    private String methodName;

    @Override
    public void onStart(ITestContext arg0) {
        System.out.println("\nSUITE STARTED: [ " + arg0.getName() + " ]");
    }


    @Override
    public void onTestStart(ITestResult arg0) {
        System.out.println("\nTEST STARTED: [ " + arg0.getName() + " ]");
    }


    @Override
    public void onTestSuccess(ITestResult result) {
        description = result.getMethod().getDescription();
        methodName  = String.format("%s", result.getMethod().getRealClass().getSimpleName());

        LOG.log(Level.ALL, "| PASSED | " + description + "_" + methodName);
        System.out.println("\nTEST PASSED: [ " + result.getName() + " ]");

        if (description.equalsIgnoreCase("API")) {

            //  Print into HTML generateReport file
            test.pass("<pre>"
                    + "<br/>"
                    + "<center><b>* * * * * * * *    R E Q U E S T    * * * * * * * *</b></center>"
                    + "<br/>"
                    + "<br/>"
                    + "URL              : " + constants.getUri().toString()
                    + "<br/>"
                    + "<br/>"
                    + HttpHelper.getFormattedJson(constants.getRequestBody())
                    + "<br/>"
                    + "<br/>"
                    + "<br/>"
                    + "<center><b>* * * * * * * *    R E S P O N S E    * * * * * * * *</b></center>"
                    + "<br/>"
                    + "<br/>"
                    + "Response Code    : " + constants.getResponseCode()
                    + "<br/>"
                    + "Response Message : " + constants.getResponseMsg()
                    + "<br/>"
                    + "<br/>"
                    + constants.getResponseHeaders()
                    + "<br/>"
                    + "<br/>"
                    + "<br/>"
                    + HttpHelper.getFormattedJson(constants.getResponseBody())
                    + "<br/>"
                    + "<br/>"
                    + "</pre>");

        } else if (description.equalsIgnoreCase("WEB")) {
            // TODO
        }
    }


    @Override
    public void onTestFailure(ITestResult result) {
        Throwable throwable = result.getThrowable();
        String exception = result.getThrowable().getMessage();

        description = result.getMethod().getDescription();
        methodName = String.format("%s", result.getMethod().getRealClass().getSimpleName());

        LOG.log(Level.ALL,"| FAILED | " + description + "_" + methodName);
        System.out.println("\nTEST FAILED: [ " + result.getName() + " ]");

        if (description.equalsIgnoreCase("API")) {

            // Print into HTML generateReport file
            test.fail("<pre>"
                    + "<br/>"
                    + "<center><b>* * * * * * * *    R E Q U E S T    * * * * * * * *</b></center>"
                    + "<br/>"
                    + "<br/>"
                    + "URL              : " + constants.getUri().toString()
                    + "<br/>"
                    + "<br/>"
                    + HttpHelper.getFormattedJson(constants.getRequestBody())
                    + "<br/>"
                    + "<br/>"
                    + "<br/>"
                    + "<center><b>* * * * * * * *    R E S P O N S E    * * * * * * * *</b></center>"
                    + "<br />"
                    + "<br />"
                    + "Response Code  : " + constants.getResponseCode()
                    + "<br />"
                    + "Error Message  : " + constants.getResponseMsg()
                    + "<br />"
                    + "<br />"
                    + constants.getResponseHeaders()
                    + "<br />"
                    + "<br />"
                    + "<br />"
                    + "<center><b>********  E X C E P T I O N  ********</b></center>"
                    + "<br />"
                    + throwable
                    + "<br />"
                    + "<br />"
                    + constants.getResponseBody()
                    + "<br />"
                    + "</pre>");

        } else if (description.equalsIgnoreCase("WEB")) {
            File fileFail;
            fileFail = ((TakesScreenshot) BasicTestConfig.driver).getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(fileFail, new File(FilePaths.screenshots_failed_folder + methodName + ".png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                test.fail("<br><b>FAILED ON SCREEN</b><br>", MediaEntityBuilder.createScreenCaptureFromPath(FilePaths.screenshots_failed_folder + methodName + ".png").build());
                test.fail("<br>" + exception + "<br>");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("\n" + throwable);
        }
    }


    @Override
    public void onTestSkipped(ITestResult arg0) {
        System.out.println("\nTEST SKIPPED: [ " +arg0.getName().toUpperCase() + " ]");
    }


    @Override
    public void onFinish(ITestContext arg0) {
        System.out.println("\nTEST FINISH: [ " +arg0.getName().toUpperCase() + " ]");
    }


    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
        // TODO Auto-generated method stub

    }



}
package com.demo.config;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.demo.properties.FilePaths;
import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.ImageComparisonUtil;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.demo.config.BasicTestConfig.*;
import static com.demo.config.ReporterConfig.test;
import static com.demo.properties.FilePaths.*;


public class Drivers {

    private WebDriver driver;


    /**
     * Used for browser configuration
     *
     * @param       browser     String with name of the browser
     */

    public WebDriver browserConfig(String browser) throws Exception {

        if (browser.equalsIgnoreCase("chrome")) {
            // Install Chrome

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--incognito");

            driver = WebDriverManager.chromedriver().capabilities(options).avoidFallback().browserVersion("130").create();

        } else if (browser.equalsIgnoreCase("firefox")) {
            // Install Firefox
            WebDriverManager.firefoxdriver().setup();

            FirefoxProfile profile = new FirefoxProfile();
            profile.setAcceptUntrustedCertificates(true);
            profile.setAssumeUntrustedCertificateIssuer(true);

            FirefoxOptions options = new FirefoxOptions();
            options.setProfile(profile);
            options.setLogLevel(FirefoxDriverLogLevel.TRACE);

            driver = new FirefoxDriver(options);
        }
        Thread.sleep(5000);
        return driver;
    }


    /**
     * Used for taking screenshots
     *
     * @param       driver, name
     * @throws      Exception
     */
    public File takeScreenshot(WebDriver driver, String imageName, String status) throws IOException {
        TakesScreenshot takesScreenshot = ((TakesScreenshot) driver);
        File imageFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File destination = new File(getScreenShot(BasicTestConfig.driver, imageName, status));
        FileUtils.copyFile(imageFile, destination);
        return destination;
    }


    /**
     * Used for separating screenshots
     *
     * @param driver
     * @param imageName
     * @param status
     * @return
     */
    private String getScreenShot(WebDriver driver, String imageName, String status) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source        = ts.getScreenshotAs(OutputType.FILE);

        String destination = "";

        switch (status) {
            case "PASS":
                destination = FilePaths.screenshots_actual_folder + imageName + ".png";
                break;

            case "FAIL":
                destination = FilePaths.screenshots_failed_folder + imageName + ".png";
                break;
        }

        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return destination;
    }


    /**
     * Used for taking screenshots on single elements.
     *
     * @param       element     Locator for the element
     * @param       image       String with name for the file
     */
    public void elementScreenshot(WebElement element, String image) throws Exception {
        File file = element.getScreenshotAs(OutputType.FILE);
        File dest = new File(screenshots_actual_folder + image + ".png");
        FileUtils.copyFile(file, dest);

        try {
            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(BasicTestConfig.driver, element);
            ImageIO.write(screenshot.getImage(), "png", new File(screenshots_actual_folder + image + ".png"));
        } catch (Exception e) {
            System.out.println("Problem with AShot library");
        }
    }


    /**
     * This method is prepared to be called from our test.
     *
     * @param       actualImageName     String that present actual screenshot image name
     * @param       expectedImageName   String that present expected screenshot image name
     */
    public void imageComparison(String actualImageName, String expectedImageName) throws IOException {
        //load images to be compared:
        BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources(screenshots_expected_folder + "\\" + expectedImageName + ".png");
        constants.setExpectedImageName(screenshots_expected_folder + "\\" + expectedImageName + ".png");

        BufferedImage actualImage = ImageComparisonUtil.readImageFromResources(screenshots_actual_folder + "\\" + actualImageName + ".png");
        constants.setActualImageName(screenshots_actual_folder + "\\" + actualImageName + ".png");

        //Create ImageComparison object with result destination and compare the images.
        ImageComparisonResult imageComparisonResult = new ImageComparison(expectedImage, actualImage).compareImages();

        String file = actualImageName.replace("_Actual", "");
        File resultDestination = new File(comparison_result_folder + "\\" + file + ".png" );
        constants.setComparisonResultImage(resultDestination.toString());

        //Image can be saved after comparison, using ImageComparisonUtil.
        ImageComparisonUtil.saveImage(resultDestination, imageComparisonResult.getResult());

        imageComparisonResult(actualImage, expectedImage);

        //Check the result
        //assertEquals(ImageComparisonState.MATCH, imageComparisonResult.getImageComparisonState());
    }


    /**
     * This method is responsible for comparing and reporting results.
     * Based on difference percents between screenshots the test will
     * be reported as passed, failed or warning.
     *
     * @param       actualImage
     * @param       expectedImage
     */
    private void imageComparisonResult(BufferedImage actualImage, BufferedImage expectedImage) throws IOException {
        ImageComparisonResult imageComparisonResult = new ImageComparison(expectedImage, actualImage).compareImages();
        double percentDifference = imageComparisonResult.getDifferencePercent();
        constants.setComparisonDifference(percentDifference);

        if (constants.getComparisonDifference() > 2.0) {
            test.fail(     "<pre><br>Actual image from the test can be found <b><a href='" + constants.getActualImageName()      + "'>here</a></b>"
                            + "<br><br>"
                            + "Expected image from the test data can be found <b><a href='"       + constants.getExpectedImageName()    + "'>here</a></b>"
                            + "<br><br>"
                            + "Calculated image difference in percentage is <b>"                  + constants.getComparisonDifference() + "%</b>"
                            + "<br><br>"
                            + "<center><b>* * * * * * * *    Image Comparison Result    * * * * * * * *</b></center>"
                            + "<br>",
                    MediaEntityBuilder.createScreenCaptureFromPath(constants.getComparisonResultImage().toString(), "<br></pre>").build());
        }
        else if (constants.getComparisonDifference() <= 1.0) {
            test.pass(     "<pre><br>Actual image from the test can be found <b><a href='" + constants.getActualImageName()      + "'>here</a></b>"
                            + "<br><br>"
                            + "Expected image from the test data can be found <b><a href='"       + constants.getExpectedImageName()    + "'>here</a></b>"
                            + "<br><br>"
                            + "Calculated image difference in percentage is <b>"                  + constants.getComparisonDifference() + "%</b>"
                            + "<br><br>"
                            + "<center><b>* * * * * * * *    Image Comparison Result    * * * * * * * *</b></center>"
                            + "<br>",
                    MediaEntityBuilder.createScreenCaptureFromPath(constants.getComparisonResultImage().toString(), "<br></pre>").build());
        }
        else if (constants.getComparisonDifference() < 2.0 || constants.getComparisonDifference() > 1.0) {
            test.warning(     "<pre><br>Actual image from the test can be found <b><a href='" + constants.getActualImageName()      + "'>here</a></b>"
                            + "<br><br>"
                            + "Expected image from the test data can be found <b><a href='"          + constants.getExpectedImageName()    + "'>here</a></b>"
                            + "<br><br>"
                            + "Calculated image difference in percentage is <b>"                     + constants.getComparisonDifference() + "%</b>"
                            + "<br><br>"
                            + "<center><b>* * * * * * * *    Image Comparison Result    * * * * * * * *</b></center>"
                            + "<br>",
                    MediaEntityBuilder.createScreenCaptureFromPath(constants.getComparisonResultImage().toString(), "<br></pre>").build());
        }

    }


    /**
     * Used for image comparing. Logic is based on RGB colors of every pixel.
     *
     * @param       actualImage     String that present actual image name
     * @param       expectedImage   String that present expected image name
     */
    public void imageCompare(String actualImage, String expectedImage) throws IOException {
        String textFile = "";

        long start = System.currentTimeMillis();
        int q = 0;
        File file1 = new File(screenshots_buffer_folder + textFile + ".txt");

        FileWriter fw = new FileWriter(file1.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        File fileA = new File(screenshots_actual_folder + actualImage + ".png");
        BufferedImage image = ImageIO.read(fileA);
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        int[][] clr = new int[width][height];

        File fileB = new File(screenshots_expected_folder + expectedImage + ".png");
        BufferedImage images = ImageIO.read(fileB);
        int widthe = images.getWidth(null);
        int heighte = images.getHeight(null);
        int[][] clre = new int[widthe][heighte];
        int smw = 0;
        int smh = 0;
        int p = 0;

        if (width > widthe) {
            smw = widthe;
        } else {
            smw = width;
        }
        if (height > heighte) {
            smh = heighte;
        } else {
            smh = height;
        }

        for (int a = 0; a < smw; a++) {
            for (int b = 0; b < smh; b++) {
                clre[a][b] = images.getRGB(a, b);
                clr[a][b] = image.getRGB(a, b);
                if (clr[a][b] == clre[a][b]) {
                    p = p + 1;
                    bw.write("\t");
                    bw.write(Integer.toString(a));
                    bw.write("\t");
                    bw.write(Integer.toString(b));
                    bw.write("\n");
                } else
                    q = q + 1;
            }
        }

        float w, h = 0;
        if (width > widthe) {
            w = width;
        } else {
            w = widthe;
        }
        if (height > heighte) {
            h = height;
        } else {
            h = heighte;
        }
        float s = (smw * smh);
        float x = (100 * p) / s;

        long stop = System.currentTimeMillis();

        if (x >= 50) {
            ReporterConfig.test.pass("<pre><b>Image comparison successfully completed</b>"
                            + "<br><br>"
                            + "Image comparison success rate    = " + x + "%"
                            + "<br>"
                            + "Number of  pixels gets varied    = " + q
                            + "<br>"
                            + "Time(ms) for visualization check = " + (stop - start)
                            + "<br>"
                            + "Number of pixels gets matched    = " + p
                            + "<br><br>"
                            + "ACTUAL SCREENSHOT"
                            + "<br><br>",
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshots_actual_folder + actualImage + ".png").build());
        }
        if (x > 49 && x < 100) {
            ReporterConfig.test.warning("<pre><b>Results from comparison needs to be checked</b>"
                            + "<br><br>"
                            + "Image comparison success rate    = " + x + "%"
                            + "<br>"
                            + "Number of  pixels gets varied    = " + q
                            + "<br>"
                            + "Time(ms) for visualization check = " + (stop - start)
                            + "<br>"
                            + "Number of pixels gets matched    = " + p
                            + "<br><br>"
                            + "ACTUAL SCREENSHOT"
                            + "<br><br>",
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshots_actual_folder + actualImage + ".png").build());
        }
        if (x < 9) {
            ReporterConfig.test.fail("<pre><b>Compare actual screenshot with screenshot from the data base has failed</b>"
                            + "<br><br>"
                            + "Image comparison success rate  = " + x + "%"
                            + "<br>"
                            + "Number of  pixels gets varied  = " + q
                            + "<br>"
                            + "Time(ms) for visualization     = " + (stop - start)
                            + "<br>"
                            + "Number of pixels gets matched  = " + p
                            + "<br><br>"
                            + "ACTUAL SCREENSHOT"
                            + "<br><br>",
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshots_actual_folder + actualImage + ".png").build());
        }
    }
}

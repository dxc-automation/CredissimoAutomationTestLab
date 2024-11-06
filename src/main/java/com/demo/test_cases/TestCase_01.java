package com.demo.test_cases;

import com.demo.config.BasicTestConfig;
import com.demo.test_scripts.ui.Home;
import com.demo.test_scripts.ui.Login;
import com.demo.utilities.ScreenRecorderUtil;
import org.testng.annotations.Test;

import static com.demo.config.ReporterConfig.startTestReport;

public class TestCase_01 extends BasicTestConfig {



    @Test(description = "WEB", enabled = true, priority = 0)
    public void checkHomePage() throws Exception {
        String testName        = "<b>Home Page Visualization</b>";
        String testCategory    = "Frontend";
        String testDescription = "The purpose of this test is to verify that home page is displayed properly."              +
                "<br><br><br>* * *  STEPS DESCRIPTION  * * *</b><br><br>"                                                       +
                "[1] Open Home page.<br>"                      +
                "[2] Accept cookie dialog.<br>" +
                "[3] Take page screenshot.<br>" +
                "[4] Compare actual page screenshot with expected one.";

        startTestReport(testName, testDescription, testCategory);
        
        Home home = new Home();
        home.openHomePage();

        drivers.takeScreenshot(driver, "HomePage_Actual", "PASS");
        drivers.imageComparison("HomePage_Actual", "HomePage_Expected");

    }


    @Test(description = "WEB", enabled = true, priority = 1)
    public void checkUserLogin() throws Exception {
        String testName        = "<b>User login with valid credentials</b>";
        String testCategory    = "Frontend";
        String testDescription = "The purpose of this test is to verify that login page is displayed properly and the functionality is working as expected.<br>" +
                "[1] Open Home page.<br>"                      +
                "[2] Accept cookie dialog.<br>" +
                "[3] Click on Login button in header menu.<br>" +
                "[4] Wait for login page and take screenshot.<br>" +
                "[5] Enter user credentials and click Login button.";

        startTestReport(testName, testDescription, testCategory);

        Home home = new Home();
        home.openHomePage();

        Login login = new Login();
        login.openLoginPage();
        drivers.takeScreenshot(driver, "LoginPage_Actual", "PASS");
        drivers.imageComparison("LoginPage_Actual", "LoginPage_Expected");

        login.userLogin(testData.getEmail(), testData.getPassword());
    }




}

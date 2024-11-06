package com.demo.test_scripts.ui;

import com.demo.config.BasicTestConfig;
import com.demo.page_objects.HomePage;
import com.demo.properties.Endpoints;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Home extends BasicTestConfig {

    private HomePage  homePage  = PageFactory.initElements(driver, HomePage.class);



    public void openHomePage() throws Exception {
        driver.get("https://" + Endpoints.host);

        String expectedTitle = "Кредити онлайн до 6 000 лв. Пари навреме! | Credissimo";
        String actualTitle   = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle, "Page title is incorrect");

        acceptCookieDialog();
    }


    private void acceptCookieDialog() throws Exception {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(homePage.cybotDialogAcceptBtn));
            drivers.elementScreenshot(homePage.cybotDialog, "CoockieDialog_Actual");

            homePage.cybotDialogAcceptBtn.click();
            wait.until(ExpectedConditions.invisibilityOf(homePage.cybotDialog));
        } catch (Exception e) {
            System.out.println("\nCookie dialog is not displayed");
        }
    }



}

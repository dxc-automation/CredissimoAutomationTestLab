package com.demo.test_scripts.ui;

import com.demo.config.BasicTestConfig;
import com.demo.page_objects.LoginPage;
import com.demo.properties.Endpoints;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.io.IOException;

public class Login extends BasicTestConfig {

    private LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);


    public void userLogin(String email, String password) throws IOException {
        enterEmail(email);
        enterPassword(password);

        wait.until(ExpectedConditions.elementToBeClickable(loginPage.submitBtn));
        loginPage.submitBtn.click();

        String expectedUrl = "https://" + Endpoints.host + Endpoints.client;
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "Incorrect URL address");
    }


    public void openLoginPage() throws IOException {
        wait.until(ExpectedConditions.elementToBeClickable(loginPage.headerMenuLoginBtn));
        loginPage.headerMenuLoginBtn.click();

        wait.until(ExpectedConditions.elementToBeClickable(loginPage.emailInput));
    }


    private void enterEmail(String email) {
        wait.until(ExpectedConditions.elementToBeClickable(loginPage.emailInput));
        loginPage.emailInput.click();
        loginPage.emailInput.sendKeys(email);
        System.out.println("\nEmail was successfully inserted");
    }


    private void enterPassword(String password) {
        wait.until(ExpectedConditions.elementToBeClickable(loginPage.passwordInput));
        loginPage.passwordInput.click();
        loginPage.passwordInput.sendKeys(password);
        System.out.println("\nPassword was successfully inserted");
    }
}

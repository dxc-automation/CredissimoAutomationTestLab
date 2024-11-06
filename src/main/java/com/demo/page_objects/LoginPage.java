package com.demo.page_objects;

import com.demo.config.BasicTestConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LoginPage extends BasicTestConfig {

    public static final Logger LOG = LogManager.getLogger(LoginPage.class);

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    final WebDriver driver;


    @FindBy(how = How.XPATH, using = "//div[@class='main-menu__item']/a[@href='https://credissimo.bg/client/login']")
    public WebElement headerMenuLoginBtn;

    @FindBy(how = How.ID, using = "email")
    public WebElement emailInput;

    @FindBy(how = How.ID, using = "password")
    public WebElement passwordInput;

    @FindBy(how = How.XPATH, using = "//button[@type='submit']")
    public WebElement submitBtn;
}

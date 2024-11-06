package com.demo.page_objects;

import com.demo.config.BasicTestConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HomePage extends BasicTestConfig {

    public static final Logger LOG = LogManager.getLogger(HomePage.class);

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    final WebDriver driver;


    //  Cybot Dialog

    @FindBy(how = How.ID, using = "CybotCookiebotDialog")
    public WebElement cybotDialog;

    @FindBy(how = How.ID, using = "CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll")
    public WebElement cybotDialogAcceptBtn;


    //  Header Menu

    @FindBy(how = How.XPATH, using = "//div[@class='main-menu__item']/a[@href='https://credissimo.bg/client/login']")
    public WebElement loginBtn;


    // Calculator

    @FindBy(how = How.XPATH, using = "//div[@class='grid__item grid__item--60 grid__item--tv-100 mt-s-30 mt-tv-10 pb-v-0']")
    public WebElement calculator;

    @FindBy(how = How.XPATH, using = "//div[@class='calculator-box']/div[2]/div[3]/div/div[@class='grid__item grid__item--100']/button[@type='button']")
    public WebElement calculatorTakeLoanBtn;


}

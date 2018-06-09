package com.learning.spring.boot.model;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class LoginPageModel {


    private final WebDriver driver;
    private static Logger logger = LogManager.getLogger(LoginPageModel.class);

    public LoginPageModel(WebDriver driver) {
        this.driver = driver;
    }

    By name = By.name("username");
    By pwd = By.name("password");
    By login = By.name("login");

    public WebDriver loginwithValidUser(String user, String password) {

        driver.findElement(name).sendKeys(user);
        driver.findElement(pwd).sendKeys(password);
        driver.findElement(login).click();
        return driver;
    }

    public boolean validate_welcomepage_title(String user) {

        if (driver.getTitle().equalsIgnoreCase("Welcome " + user)) {
            logger.info("User Login was Succesful");
            return true;
        } else {
            logger.info("User Login Failed" + driver.getTitle() + " != Welcome" + user);
            return false;
        }

    }
}

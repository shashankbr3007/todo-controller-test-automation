package com.learning.spring.boot.model;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WelcomePageModel {
    private final WebDriver driver;
    private static Logger logger = LogManager.getLogger(LoginPageModel.class);

    public WelcomePageModel(WebDriver driver) {
        this.driver = driver;
    }

    By name = By.name("click");

    public boolean validate_welcomepage_title(String user) {

        if (driver.getTitle().equalsIgnoreCase("Welcome " + user)) {
            logger.info("User Login was Succesful");
            return true;
        } else {
            logger.info("User Login Failed" + driver.getTitle() + " != Welcome" + user);
            return false;
        }

    }

    public WebDriver click_here_showtodo_page(){
        driver.findElement(name).click();

        return driver;
    }

    public boolean validate_showtodopage_title(String user) {

        if (driver.getTitle().equalsIgnoreCase("Todo List for "+ user)) {
            logger.info("User successfully moved to Todo-Page");
            return true;
        } else {
            logger.info("User failed to move into Todo-Page" + driver.getTitle() + " != Todo-PageTodo List for " + user);
            return false;
        }

    }

}

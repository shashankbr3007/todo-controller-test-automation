package com.learning.spring.boot.model;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ShowTodoPageModel {

    private final WebDriver driver;
    private static Logger logger = LogManager.getLogger(LoginPageModel.class);

    public ShowTodoPageModel(WebDriver driver) {
        this.driver = driver;
    }

    By id = By.name("id");
    By desc = By.name("desc");
    By targetDate = By.name("targetDate");
    By done = By.name("done");

    public String[] get_todo_id(){
        return get_element_names(id);
    }

    public String[] get_todo_targetDate(){
        return get_element_names(targetDate);
    }

    public String[] get_todo_done(){
        return get_element_names(done);
    }

    public String[] get_todo_desc(){
        return get_element_names(desc);
    }

    public String[] get_element_names(By element){
        List<WebElement> elements = null;
        try {
            elements = driver.findElements(element);
        }catch (Exception e){
            logger.error(e.getStackTrace());
        }
        String[] ids=  new String[elements.size()];
        int i =0;
        for (WebElement a :elements) {
            ids[i] = a.getText();
            i++;
        }
        return ids;
    }
}

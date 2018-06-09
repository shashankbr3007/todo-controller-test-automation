package com.learning.spring.boot.test;

import com.learning.spring.boot.model.LoginPageModel;
import com.learning.spring.boot.model.ShowTodoPageModel;
import com.learning.spring.boot.model.WelcomePageModel;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.*;

public class ApplicationTestMain {

    private static Logger logger = LogManager.getLogger(ApplicationTestMain.class);

    public FirefoxDriver firefoxDriver;

    String URL = "http://localhost:8080/login";

    public WebDriver loaddriver() {
        WebDriver webdriver;
        System.setProperty("webdriver.gecko.driver", "./webdriver/geckodriverv0.19.1/geckodriver.exe");
        webdriver = new FirefoxDriver();
        webdriver.get(URL);

        return webdriver;
    }

    @Test(dataProvider = "creds", groups = "login")
    public void run_login_test(String name, String password) {
        WebDriver webdriver = loaddriver();
        LoginPageModel loginPage = new LoginPageModel(webdriver);

        String loginpagetitle = webdriver.findElement(By.name("title")).getText();

        if (loginpagetitle.equalsIgnoreCase("Todo Application List")) {
            loginPage.loginwithValidUser(name, password);
            if (loginPage.validate_welcomepage_title(name)) {
                logger.info("Test Case Passed");
                Assert.assertTrue(loginPage.validate_welcomepage_title(name));
            } else {
                logger.info("Test Case Failed");
                Assert.assertTrue(loginPage.validate_welcomepage_title(name));
            }
        }
        closedriver(webdriver);
    }

    @Test(groups = "Navigateintotodo", dataProvider = "creds")
    public void navigate_into_todo_page(String name, String password) {

        WebDriver webdriver = loaddriver();
        LoginPageModel loginPage = new LoginPageModel(webdriver);
        webdriver = loginPage.loginwithValidUser(name, password);

        WelcomePageModel welcomePage = new WelcomePageModel(webdriver);
        if (welcomePage.validate_welcomepage_title(name)) {
            welcomePage.click_here_showtodo_page();
            if (welcomePage.validate_showtodopage_title(name)) {
                logger.info("Test Case Passed");
                Assert.assertTrue(welcomePage.validate_showtodopage_title(name));
            } else {
                logger.info("Test Case Failed");
                Assert.assertTrue(welcomePage.validate_showtodopage_title(name));
            }
        } else {
            logger.info("Test Case Failed");
            Assert.assertTrue(welcomePage.validate_welcomepage_title(name));
        }

        closedriver(webdriver);
    }

    @Test(groups = "Validatetodo", dataProvider = "creds")
    public void validate_todo_page(String name, String password) {

        WebDriver webdriver = loaddriver();
        LoginPageModel loginpage = new LoginPageModel(webdriver);
        webdriver = loginpage.loginwithValidUser(name, password);

        WelcomePageModel welcomePage = new WelcomePageModel(webdriver);
        webdriver = welcomePage.click_here_showtodo_page();

        if (welcomePage.validate_showtodopage_title(name)) {
            ShowTodoPageModel todoPage = new ShowTodoPageModel(webdriver);
            String[] ids = todoPage.get_todo_id();
            String[] descs = todoPage.get_todo_desc();
            String[] done = todoPage.get_todo_done();
            String[] targetdate = todoPage.get_todo_targetDate();

            for (int i = 0; i < ids.length; i++) {
                logger.info(name + " : " + ids[i] + " : " + descs[i] + " : " + targetdate[i] + " : " + done[i]);
            }

            if (name.equalsIgnoreCase("in28minutes")) {
                if (ids.length == 2) {
                    if (welcomePage.validate_showtodopage_title(name)) {
                        logger.info("Test Case Passed");
                        Assert.assertEquals(ids.length, 2);
                    } else {
                        logger.info("Test Case Failed");
                        Assert.assertEquals(ids.length, 2);
                    }
                }
            } else if (name.equalsIgnoreCase("shashank")) {
                if (ids.length == 1) {
                    if (welcomePage.validate_showtodopage_title(name)) {
                        logger.info("Test Case Passed");
                        Assert.assertEquals(ids.length, 1);
                    } else {
                        logger.info("Test Case Failed");
                        Assert.assertEquals(ids.length, 1);
                    }
                }
            } else if (name.equalsIgnoreCase("harshini")) {
                if (ids.length == 0) {
                    if (welcomePage.validate_showtodopage_title(name)) {
                        logger.info("Test Case Passed");
                        Assert.assertEquals(ids.length, 0);
                    } else {
                        logger.info("Test Case Failed");
                        Assert.assertEquals(ids.length, 0);
                    }
                }
            }
        }
        closedriver(webdriver);
    }

    public void closedriver(WebDriver webdriver) {
        webdriver.close();
    }

    @DataProvider(name = "creds"/*, parallel = true*/)
    public Object[][] readtestdata() throws IOException {

        File file = new File("./testdata/testdata.csv");
        LineNumberReader lineReader = new LineNumberReader(new FileReader(file));
        lineReader.skip(Long.MAX_VALUE);

        int totalNumberOfLines = lineReader.getLineNumber() + 1;
        Object[][] creds = new Object[totalNumberOfLines][2];
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        int i = 0;
        String sCurrentLine;
        while ((sCurrentLine = bufferedReader.readLine()) != null) {
            creds[i][0] = sCurrentLine.split(",")[0];
            creds[i][1] = sCurrentLine.split(",")[1];
            i++;
        }
        return creds;
    }

}

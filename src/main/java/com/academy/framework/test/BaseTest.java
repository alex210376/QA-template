package com.academy.framework.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    private static final Logger LOG =  LogManager.getLogger(BaseTest.class);

    protected Properties prop;
    protected WebDriver driver;
    protected String baseUrl;

    private void loadProperties() throws Exception {
        String propertyPath = System.getProperty("cfg");
        prop = new Properties();
        prop.load(new FileReader(propertyPath));
    }

    @Parameters({"browser"})
    @BeforeClass(alwaysRun = true)
    public void setUp(@Optional("chrome") String browser) throws Exception {
        loadProperties();

        baseUrl = prop.getProperty("base.url");

        switch (browser) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", prop.getProperty("driver.chrome"));
                driver = new ChromeDriver();
                break;

            case "firefox":
                System.setProperty("webdriver.gecko.driver", prop.getProperty("driver.firefox"));
                driver = new FirefoxDriver();
                break;
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void logTestStart(Method method, Object[] params) {
        LOG.info("Start test {} with parameters {}",
                method.getName(), Arrays.toString(params));
    }

    @AfterMethod(alwaysRun = true)
    public void logTestStop(Method method) {
        LOG.info("Finish test {}", method.getName());
    }


    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
    }
}

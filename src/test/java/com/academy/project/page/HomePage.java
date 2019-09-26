package com.academy.project.page;

import com.academy.framework.page.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
    @FindBy(css = "input[name='q']")
    private WebElement searchField;

    @FindBy(css ="input[value~='Google']")
    private WebElement searchButton;

    public static  HomePage startFromHome(WebDriver driver, String baseUrl) {
        driver.get(baseUrl);
        return new HomePage(driver);
    }

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage inputSearch(String text) {
        inputTextField(searchField, text);
        return this;
    }

    public HomePage clickSearch() {
        searchButton.click();
        return this;
    }
}

package com.automationexercise.tests;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.drivers.WebDriverProvider;
import com.automationexercise.utils.dataReader.JsonReader;
import org.openqa.selenium.WebDriver;

public class BaseTest implements WebDriverProvider {
    protected GUIDriver driver;
    protected JsonReader testData;

    public void beforeClass(){
        testData = new JsonReader("");
    }

    @Override
    public WebDriver getWebDriver() {
        return driver.get();
    }
}

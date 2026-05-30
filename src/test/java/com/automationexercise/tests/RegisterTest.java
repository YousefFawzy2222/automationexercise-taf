package com.automationexercise.tests;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.components.NavigationBarComponent;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegisterTest extends BaseTest {


    //Tests
    @Test
    public void signUpTest() {

    }


    //Configurations
    @BeforeMethod
    public void beforeMethod() {
        driver = new GUIDriver(); //initialized our driver component
        new NavigationBarComponent(driver).navigate(); //navigate to the base url
    }
    @AfterMethod
    public void tearDown() {
        driver.quitDriver();
    }
}

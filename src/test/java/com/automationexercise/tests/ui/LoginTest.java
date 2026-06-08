package com.automationexercise.tests.ui;

import com.automationexercise.apis.UserManagementApi;
import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.SignupLoginPage;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.tests.BaseTest;
import com.automationexercise.utils.TimeManager;
import com.automationexercise.utils.dataReader.JsonReader;
import com.automationexercise.utils.dataReader.PropertyReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Automation Exercise")
@Feature("UI User Management")
@Story("User Login")
@Severity(SeverityLevel.CRITICAL)
@Owner("Fawzy")
public class LoginTest extends BaseTest {
    String timestamp = TimeManager.getSimpleTimeStamp();

    @Description("Verify that user can login successfully with valid credentials")
    @Test
    public void validLoginTC() {
        new UserManagementApi()
                .createRegisterUserAccount(
                        testData.getJsonData("name"),
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName")
                ).verifyUserCreatedSuccessfully();

        new SignupLoginPage(driver)
                .navigate()
                .enterLoginEmail(testData.getJsonData("email") + timestamp + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password"))
                .clickLoginButton()
                .navigationBar
                .verifyUserLabel(testData.getJsonData("name"));
        new UserManagementApi()
                .deleteUserAccount(
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();
    }

    @Description("Verify that user cannot login with invalid email")
    @Test
    public void inValidLoginUsingInvalidEmailTC() {
        new UserManagementApi()
                .createRegisterUserAccount(
                        testData.getJsonData("name"),
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName")
                ).verifyUserCreatedSuccessfully();

        new SignupLoginPage(driver)
                .navigate()
                .enterLoginEmail(testData.getJsonData("email")  + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password"))
                .clickLoginButton()
                .verifyLoginErrorMsg(testData.getJsonData("messages.loginErrorMsg"));
        new UserManagementApi()
                .deleteUserAccount(
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();
    }

    @Description("Verify that user cannot login with invalid password")
    @Test
    public void inValidLoginUsingInvalidPasswordTC() {
        new UserManagementApi()
                .createRegisterUserAccount(
                        testData.getJsonData("name"),
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName")
                ).verifyUserCreatedSuccessfully();

        new SignupLoginPage(driver)
                .navigate()
                .enterLoginEmail(testData.getJsonData("email") + timestamp+ "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password") + timestamp)
                .clickLoginButton()
                .verifyLoginErrorMsg(testData.getJsonData("messages.loginErrorMsg"));
        new UserManagementApi()
                .deleteUserAccount(
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();
    }


    //Configurations
    @BeforeClass
    private void preCondition(){
        testData = new JsonReader("login-data");
    }
    @BeforeMethod
    public void beforeMethod() {
        driver = new GUIDriver(); //initialized our driver component
        new NavigationBarComponent(driver).navigate(); //navigate to the base url
        if (PropertyReader.getProperty("executionType").equalsIgnoreCase("Local")) {
            driver.browser().closeExtensionTab();
        }
    }
    @AfterMethod
    public void tearDown() {
        driver.quitDriver();
    }
}

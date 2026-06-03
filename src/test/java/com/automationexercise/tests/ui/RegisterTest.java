package com.automationexercise.tests.ui;

import com.automationexercise.apis.UserManagementApi;
import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.SignupLoginPage;
import com.automationexercise.pages.SignupPage;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.tests.BaseTest;
import com.automationexercise.utils.TimeManager;
import com.automationexercise.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Automation Exercise")
@Feature("UI User Management")
@Story("User Register")
@Severity(SeverityLevel.CRITICAL)
@Owner("Fawzy")
public class RegisterTest extends BaseTest {
    String timestamp = TimeManager.getSimpleTimeStamp();


    //Tests
    @Description("Verify that user can register successfully with valid data")
    @Test
    public void validSignUpTC() {

        new SignupLoginPage(driver)
                .navigate()
                .enterSignUpName(testData.getJsonData("name"))
                .enterSignUpEmail(testData.getJsonData("email") + timestamp+"@gmail.com")
                .clickSignUpButton();

        new SignupPage(driver)
                .fillRegistrationForm(
                        testData.getJsonData("titleMale"),
                        testData.getJsonData("password"),
                        testData.getJsonData("day"),
                        testData.getJsonData("month"),
                        testData.getJsonData("year"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"),
                        testData.getJsonData("company"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("address2"),
                        testData.getJsonData("country"),
                        testData.getJsonData("state"),
                        testData.getJsonData("city"),
                        testData.getJsonData("zipCode"),
                        testData.getJsonData("mobileNumber")
                )
                .clickCreateAccountButton()
                .verifyAccountCreated();
        new UserManagementApi()
                .deleteUserAccount(
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();

    }

    @Description("Verify that user cannot register with email that already exists in the system")
    @Test
    public void verifyErrorMessageWhenAccountCreatedBefore() {
        //precondition > create a user Account
        new UserManagementApi().createRegisterUserAccount(
                testData.getJsonData("name"),
                testData.getJsonData("email") + timestamp +"@gmail.com",
                testData.getJsonData("password"),
                testData.getJsonData("titleMale"),
                testData.getJsonData("day"),
                testData.getJsonData("month"),
                testData.getJsonData("year"),
                testData.getJsonData("firstName"),
                testData.getJsonData("lastName"),
                testData.getJsonData("company"),
                testData.getJsonData("address1"),
                testData.getJsonData("address2"),
                testData.getJsonData("country"),
                testData.getJsonData("state"),
                testData.getJsonData("city"),
                testData.getJsonData("zipCode"),
                testData.getJsonData("mobileNumber")
        ). verifyUserCreatedSuccessfully();
        new SignupLoginPage(driver).navigate()
                .enterSignUpName(testData.getJsonData("name"))
                .enterSignUpEmail(testData.getJsonData("email") + timestamp+"@gmail.com")
                .clickSignUpButton()
                .verifySignUpErrorMsg(testData.getJsonData("messages.signUpErrorMsg"));
    }


    //Configurations
    @BeforeClass
    public void preCondition(){
        testData = new JsonReader("register-data");
    }
    @BeforeMethod
    public void beforeMethod() {
        driver = new GUIDriver(); //initialized our driver component
        new NavigationBarComponent(driver).navigate(); //navigate to the base url
        driver.browser().closeExtensionTab();
    }
    @AfterMethod
    public void tearDown() {
        driver.quitDriver();
    }
}
